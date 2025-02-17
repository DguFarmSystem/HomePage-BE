package org.farmsystem.homepage.domain.user.service;

import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.farmsystem.homepage.global.error.ErrorCode.OAUTH_TOKEN_REQUEST_FAILED;

// OAuth 토큰 요청 클래스
@Service
public class OauthTokenProvider {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    public String getOauthToken(String code, SocialType socialType) {
        try {
            String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8); //구글 로그인 인가코드 인코딩 관련 오류 방지 (%2F -> /)

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("code", decodedCode);
            requestBody.add("client_id", socialType.equals(SocialType.GOOGLE) ? googleClientId : kakaoClientId);
            requestBody.add("client_secret", socialType.equals(SocialType.GOOGLE) ? googleClientSecret : kakaoClientSecret);
            requestBody.add("redirect_uri", socialType.equals(SocialType.GOOGLE) ? googleRedirectUri : kakaoRedirectUri);
            requestBody.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    socialType.equals(SocialType.GOOGLE) ? "https://oauth2.googleapis.com/token" : "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST, requestEntity, Map.class);

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return (String) responseBody.get("access_token");
            } else {
                throw new BusinessException(OAUTH_TOKEN_REQUEST_FAILED);
            }
        } catch (Exception e) {
            throw new BusinessException(OAUTH_TOKEN_REQUEST_FAILED);
        }
    }
}
