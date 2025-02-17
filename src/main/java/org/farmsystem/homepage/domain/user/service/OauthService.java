package org.farmsystem.homepage.domain.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.farmsystem.homepage.global.error.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final OauthTokenProvider oauthTokenProvider;
    private final OauthUserResourceProvider oauthUserResourceProvider;
    private final UserRepository userRepository;
    private final TokenService tokenService;


    @Transactional
    public UserTokenResponseDTO socialLogin(UserLoginRequestDTO userLoginRequest) {
        try {
            SocialType socialType = userLoginRequest.socialType();

            // OAuth 토큰 가져오기
            String oauthToken = oauthTokenProvider.getOauthToken(userLoginRequest.code(), socialType);

            // 사용자 정보 가져오기
            JsonNode userResource = oauthUserResourceProvider.getUserResource(oauthToken, socialType);

            // 사용자 정보 처리
            String imageUrl = socialType.equals(SocialType.GOOGLE)
                    ? userResource.path("picture").asText()
                    : userResource.path("kakao_account").path("profile").path("profile_image_url").asText();

            // 사용자 저장
            Long userId = saveOrUpdateUser(imageUrl, userLoginRequest);

            // JWT 토큰 발급
            return tokenService.issueToken(userId);

        } catch (Exception e) {
            throw new BusinessException(INTERNAL_SERVER_ERROR);
        }
    }

    public Long saveOrUpdateUser(String imageUrl, UserLoginRequestDTO userLoginRequest) {
        User existingUser = userRepository.findByStudentNumber(userLoginRequest.studentNumber());

        if (existingUser != null) {
            return existingUser.getUserId();
        } else {
            User user = User.builder()
                    .profileImageUrl(imageUrl)
                    .name(userLoginRequest.name())
                    .studentNumber(userLoginRequest.studentNumber())
                    .socialType(userLoginRequest.socialType())
                    .build();
            User savedUser = userRepository.save(user);
            return savedUser.getUserId();
        }
    }
}
