package org.farmsystem.homepage.domain.community.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.community.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.community.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.domain.community.user.entity.SocialType;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OauthService {

    private final OauthTokenProvider oauthTokenProvider;
    private final OauthUserResourceProvider oauthUserResourceProvider;
    private final TokenService tokenService;
    private final UserService userService;

    @Transactional
    public UserTokenResponseDTO socialLogin(UserLoginRequestDTO userLoginRequest){
        SocialType socialType = userLoginRequest.socialType();

        // OAuth 토큰 가져오기
        String oauthToken = oauthTokenProvider.getOauthToken(userLoginRequest.code(), socialType);

        // 사용자 정보 가져오기
        JsonNode userResource = oauthUserResourceProvider.getUserResource(oauthToken, socialType);

        // 사용자 정보 처리
        Map<String, String> userInfo = oauthUserResourceProvider.extractUserInfo(userResource, socialType);
        String socialId = userInfo.get("socialId");
        String imageUrl = userInfo.get("imageUrl");

        // 사용자 저장
        User user = userService.saveUser(socialId, imageUrl, userLoginRequest);

        // JWT 토큰 발급
        return tokenService.issueToken(user.getUserId(), user.getRole());

    }




}
