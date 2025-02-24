package org.farmsystem.homepage.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.farmsystem.homepage.global.error.ErrorCode.OAUTH_USER_RESOURCE_FAILED;

// OAuth 토큰으로 사용자 정보 요청 클래스
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
            e.printStackTrace();
            throw new BusinessException(OAUTH_USER_RESOURCE_FAILED);
        }
    }
}
