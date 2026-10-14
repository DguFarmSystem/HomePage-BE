package org.farmsystem.homepage.domain.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.blog.entity.Blog;
import org.farmsystem.homepage.domain.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogPreviewService {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36";
    private static final int MAX_IMAGE_BYTES = 5 * 1024 * 1024;
    private static final Pattern META_TAG = Pattern.compile("<meta\\s[^>]*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern CONTENT_ATTR = Pattern.compile("content\\s*=\\s*(?:\"([^\"]*)\"|'([^']*)')", Pattern.CASE_INSENSITIVE);
    private static final Pattern TITLE_TAG = Pattern.compile("<title[^>]*>([^<]*)</title>", Pattern.CASE_INSENSITIVE);

    private final BlogRepository blogRepository;
    private final S3Client s3Client;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    // 미리보기(title)가 없는 블로그의 OG 정보를 채움 (기존 데이터 백필 + 신규 등록/승인 블로그)
    // ponytail: 계속 실패하는 블로그는 10분마다 재시도됨, 문제가 되면 시도 시각 컬럼 추가
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 30 * 1000)
    public void fillMissingPreviews() {
        for (Blog blog : blogRepository.findByTitleIsNull()) {
            try {
                fillPreview(blog);
            } catch (Exception e) {
                log.warn("블로그 미리보기 수집 실패 blogId={} link={} : {}", blog.getBlogId(), blog.getLink(), e.toString());
            }
        }
    }

    private void fillPreview(Blog blog) throws Exception {
        URI pageUri = httpUri(blog.getLink());
        HttpResponse<String> page = httpClient.send(request(pageUri), HttpResponse.BodyHandlers.ofString());
        if (page.statusCode() / 100 != 2) {
            throw new IllegalStateException("HTTP " + page.statusCode());
        }
        String html = page.body();

        String title = ogContent(html, "og:title");
        if (title == null) {
            Matcher m = TITLE_TAG.matcher(html);
            title = m.find() ? unescape(m.group(1).trim()) : null;
        }
        String description = ogContent(html, "og:description");

        String thumbnailUrl = null;
        String image = ogContent(html, "og:image");
        if (image != null) {
            try {
                thumbnailUrl = copyThumbnail(blog.getBlogId(), page.uri().resolve(image.trim()));
            } catch (Exception e) {
                log.warn("블로그 썸네일 복사 실패 blogId={} image={} : {}", blog.getBlogId(), image, e.toString());
            }
        }

        blog.updatePreview(title, description, thumbnailUrl);
        blogRepository.save(blog); // 블로그별로 별도 트랜잭션
    }

    // og:image를 다운로드해 버킷에 저장 (tistory 이미지 URL은 서명이 만료되므로 핫링크 불가)
    private String copyThumbnail(Long blogId, URI imageUri) throws Exception {
        HttpResponse<InputStream> res = httpClient.send(request(httpUri(imageUri.toString())), HttpResponse.BodyHandlers.ofInputStream());
        byte[] bytes;
        try (InputStream in = res.body()) {
            if (res.statusCode() / 100 != 2) {
                throw new IllegalStateException("HTTP " + res.statusCode());
            }
            bytes = in.readNBytes(MAX_IMAGE_BYTES + 1);
        }
        if (bytes.length > MAX_IMAGE_BYTES) {
            throw new IllegalStateException("이미지가 5MB 초과");
        }

        String key = "blog/thumbnail_" + blogId;
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(res.headers().firstValue("Content-Type").orElse("image/png"))
                        .build(),
                RequestBody.fromBytes(bytes));
        return apiBaseUrl + "/api/s3/files/" + key;
    }

    private HttpRequest request(URI uri) {
        return HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header("User-Agent", USER_AGENT)
                .GET()
                .build();
    }

    // http/https만 허용
    private static URI httpUri(String url) {
        URI uri = URI.create(url.trim());
        String scheme = uri.getScheme();
        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            throw new IllegalArgumentException("http/https가 아닌 URL: " + url);
        }
        return uri;
    }

    // <meta property|name="og:xxx" content="..."> (속성 순서 무관)
    static String ogContent(String html, String property) {
        Pattern prop = Pattern.compile("(?:property|name)\\s*=\\s*[\"']" + Pattern.quote(property) + "[\"']", Pattern.CASE_INSENSITIVE);
        Matcher tags = META_TAG.matcher(html);
        while (tags.find()) {
            String tag = tags.group();
            if (!prop.matcher(tag).find()) continue;
            Matcher c = CONTENT_ATTR.matcher(tag);
            if (c.find()) {
                String value = unescape(c.group(1) != null ? c.group(1) : c.group(2)).trim();
                return value.isEmpty() ? null : value;
            }
        }
        return null;
    }

    private static String unescape(String s) {
        return s.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"")
                .replace("&#39;", "'").replace("&#x27;", "'").replace("&amp;", "&");
    }
}
