package org.farmsystem.homepage.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.farmsystem.homepage.global.error.ErrorCode.OAUTH_USER_RESOURCE_FAILED;

// OAuth 토큰으로 사용자 정보 요청 클래스
@Service
public class OauthUserResourceProvider {
    private final RestTemplate restTemplate = new RestTemplate();

    public JsonNode getUserResource(String accessToken, SocialType socialType) throws JsonProcessingException {
        String apiUrl = socialType.equals(SocialType.GOOGLE)
                ? "https://www.googleapis.com/oauth2/v3/userinfo"
                : "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class
        );

        if (response.getStatusCode() != HttpStatus.OK || Objects.isNull(response.getBody())) {
            throw new BusinessException(OAUTH_USER_RESOURCE_FAILED);
        }
        return new ObjectMapper().readTree(response.getBody());
    }
}
