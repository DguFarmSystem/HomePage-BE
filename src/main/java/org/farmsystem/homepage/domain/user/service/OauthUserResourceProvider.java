package org.farmsystem.homepage.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.farmsystem.homepage.global.error.ErrorCode.OAUTH_USER_RESOURCE_FAILED;

// OAuth 토큰으로 사용자 정보 요청 클래스
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OauthUserResourceProvider {

    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode getUserResource(String accessToken, SocialType socialType) {
        try {
            String userResourceApiUrl = socialType.equals(SocialType.GOOGLE)
                    ? "https://www.googleapis.com/oauth2/v3/userinfo"
                    : "https://kapi.kakao.com/v2/user/me";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(userResourceApiUrl, HttpMethod.GET, entity, String.class);

            return new ObjectMapper().readTree(response.getBody());
        } catch (Exception e) {
            log.error("OAuth 사용자 정보 요청 중 오류 발생: ", e);
            throw new BusinessException(OAUTH_USER_RESOURCE_FAILED);
        }
    }

    public Map<String, String> extractUserInfo(JsonNode userResource, SocialType socialType) {
        String socialId = socialType.equals(SocialType.GOOGLE)
                ? userResource.path("sub").asText()
                : userResource.path("id").asText();

        String imageUrl = socialType.equals(SocialType.GOOGLE)
                ? userResource.path("picture").asText()
                : userResource.path("kakao_account").path("profile").path("profile_image_url").asText();
        return Map.of("socialId", socialId, "imageUrl", imageUrl);
    }
}
