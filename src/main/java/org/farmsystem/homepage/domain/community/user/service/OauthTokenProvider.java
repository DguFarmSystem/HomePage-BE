package org.farmsystem.homepage.domain.community.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.community.user.entity.SocialType;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.farmsystem.homepage.global.error.ErrorCode.OAUTH_TOKEN_REQUEST_FAILED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
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
            String oauthTokenApiUrl = socialType.equals(SocialType.GOOGLE)
                    ? "https://oauth2.googleapis.com/token"
                    : "https://kauth.kakao.com/oauth/token";

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

            ResponseEntity<Map> response = restTemplate.exchange(oauthTokenApiUrl, HttpMethod.POST, requestEntity, Map.class);

            return (String) response.getBody().get("access_token");
        } catch (Exception e) {
            log.error("OAuth 토큰 요청 중 오류 발생: ", e);
            throw new BusinessException(OAUTH_TOKEN_REQUEST_FAILED);
        }
    }
}
