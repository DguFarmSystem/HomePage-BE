package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.apply.repository.PassedApplyRepository;
import org.farmsystem.homepage.domain.common.util.JamoUtil;
import org.farmsystem.homepage.domain.user.dto.request.UserInfoUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.*;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.common.S3Service;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.farmsystem.homepage.global.error.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.farmsystem.homepage.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final PassedApplyRepository passedApplyRepository;

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
    public UserInfoResponseDTO updateUserInfo(Long userId, UserInfoUpdateRequestDTO userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        userUpdateRequest.phoneNumber().ifPresent(user::setPhoneNumber);
        userUpdateRequest.major().ifPresent(user::setMajor);
        userUpdateRequest.profileImage().ifPresent(profileImage -> user.setProfileImageUrl(uploadProfileImage(profileImage)));

        userRepository.save(user);
        return UserInfoResponseDTO.from(user);
    }

    private String uploadProfileImage(MultipartFile profileImage) {
        try {
            return s3Service.uploadFile(profileImage, "profile");
        } catch (IOException e) {
            throw new BusinessException(PROFILE_IMAGE_UPLOAD_FAILED);
        }
    }

    // 사용자 정보 저장
    @Transactional
    public User saveUser(String socialId, String imageUrl, UserLoginRequestDTO userLoginRequest) {

        // 이미 가입한 유저인지 확인
        User existedUser = getExistedUser(socialId, userLoginRequest.socialType());

        // 기존 유저가 아닌 경우 회원 인증 받을 때 사용한 학번으로 합격자 정보 조회하여 유저 정보 저장
        if (existedUser == null && userLoginRequest.studentNumber().isPresent()) {
            String studentNumber = userLoginRequest.studentNumber().get();

            // 소셜로그인 다른 계정으로 이미 가입한 경우 예외 처리
            checkIfUserAlreadyRegistered(studentNumber);

            return createUserFromPassedUser(studentNumber, userLoginRequest, socialId, imageUrl);
        }
        return existedUser;
    }

    public UserSearchResponseDTO searchUser(String query) {
        User user = userRepository.findByName(query)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return UserSearchResponseDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .track(user.getTrack())
                .generation(user.getGeneration())
                .build();
    }

    public List<UserSearchResponseDTO> searchUserSuggest(String query) {
        List<User> userList = userRepository.findByNameJamoStartsWith(JamoUtil.convertToJamo(query));
        return userList.stream()
                .map(user -> UserSearchResponseDTO.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .profileImageUrl(user.getProfileImageUrl())
                        .track(user.getTrack())
                        .generation(user.getGeneration())
                        .build()
                )
                .collect(Collectors.toList());
    }

    private User getExistedUser(String socialId, SocialType socialType) {
        return userRepository.findBySocialTypeAndSocialId(socialType, socialId).orElse(null);
    }

    private void checkIfUserAlreadyRegistered(String studentNumber) {
        if (userRepository.findByStudentNumber(studentNumber).isPresent()) {
            throw new BusinessException(USER_ALREADY_EXISTS);
        }
    }

    private User createUserFromPassedUser(String studentNumber, UserLoginRequestDTO userLoginRequest, String socialId, String imageUrl) {
        PassedApply passedUser = passedApplyRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new EntityNotFoundException(PASSED_USER_NOT_FOUND));

        return userRepository.save(UserSaveResponseDTO.fromPassedUser(passedUser, userLoginRequest.socialType(), socialId, imageUrl));
    }

    // 사용자 정보 삭제
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.setDeleted(true);
        userRepository.save(user);
    }

}
