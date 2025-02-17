package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.apply.repository.PassedApplyRepository;
import org.farmsystem.homepage.domain.user.dto.request.UserInfoUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoUpdateResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserTokenResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.common.S3Service;
import org.farmsystem.homepage.global.config.auth.jwt.JwtProvider;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.farmsystem.homepage.global.error.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.farmsystem.homepage.global.error.ErrorCode.AUTHENTICATION_FAILED;
import static org.farmsystem.homepage.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final JwtProvider jwtProvider;
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final PassedApplyRepository passedApplyRepository;

    public String issueNewAccessToken(Long userId) {
        return jwtProvider.getIssueToken(userId, true);
    }
    public String issueNewRefreshToken(Long userId) {
        return jwtProvider.getIssueToken(userId, false);
    }

    public UserTokenResponseDTO issueTempToken(Long userId) {
        String accessToken = issueNewAccessToken(userId);
        String refreshToken = issueNewRefreshToken(userId);
        return UserTokenResponseDTO.of(accessToken, refreshToken);
    }

    public UserVerifyResponseDTO verifyUser(UserVerifyRequestDTO userVerifyRequest) {
        PassedApply verifiedUser = passedApplyRepository.findByStudentNumber(userVerifyRequest.studentNumber())
                .orElseThrow(() -> new UnauthorizedException(AUTHENTICATION_FAILED));
        return UserVerifyResponseDTO.from(true, verifiedUser);
    }

    public UserInfoResponseDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return UserInfoResponseDTO.from(user);
    }

    // 사용자 정보 수정
    @Transactional
    public UserInfoUpdateResponseDTO updateUserInfo(Long userId, UserInfoUpdateRequestDTO userUpdateRequestDTO) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        if (userUpdateRequestDTO.phoneNumber() != null) {
            user.setPhoneNumber(userUpdateRequestDTO.phoneNumber());
        }

        if (userUpdateRequestDTO.profileImage() != null) {
            String profileImageUrl = s3Service.uploadFile(userUpdateRequestDTO.profileImage(), "profile");
            user.setProfileImageUrl(profileImageUrl);
        }

        userRepository.save(user);
        return UserInfoUpdateResponseDTO.from(user);
    }

}
