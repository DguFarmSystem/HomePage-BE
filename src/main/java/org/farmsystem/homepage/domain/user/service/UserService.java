package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.apply.repository.PassedApplyRepository;
import org.farmsystem.homepage.domain.common.util.JamoUtil;
import org.farmsystem.homepage.domain.user.dto.request.UserLoginRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserSaveResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserSearchResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.domain.user.dto.request.*;
import org.farmsystem.homepage.domain.user.dto.response.*;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.farmsystem.homepage.global.error.exception.UnauthorizedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.farmsystem.homepage.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
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
    public UserInfoResponseDTO updateUserInfo(Long userId, UserUpdateRequestDTO userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        user.updateUser(userUpdateRequest.toEntity());
        userRepository.save(user);

        return UserInfoResponseDTO.from(user);
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
        return UserSearchResponseDTO.from(user);
    }

    public List<UserSearchResponseDTO> searchUserSuggest(String query) {
        List<User> userList = userRepository.findByNameJamoStartsWith(JamoUtil.convertToJamo(query));
        return userList.stream()
                .map(UserSearchResponseDTO::from)
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

    // [관리자] 사용자 정보 등록
    @Transactional
    public UserRegisterResponseDTO registerUser(AdminUserRegisterRequestDTO adminUserRegisterRequest) {
        passedApplyRepository.findByStudentNumber(adminUserRegisterRequest.studentNumber())
                .ifPresent(user -> {throw new BusinessException(PASSED_USER_ALREADY_EXISTS);});
        PassedApply registeredUser = passedApplyRepository.save(adminUserRegisterRequest.toEntity());
        return UserRegisterResponseDTO.from(registeredUser);
    }

    // [관리자] 사용자 정보 삭제
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.delete();
    }

    // [관리자] 사용자 정보 수정
    @Transactional
    public UserInfoResponseDTO updateUserByAdmin(Long userId, AdminUserUpdateRequestDTO adminUserUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        user.updateUserByAdmin(adminUserUpdateRequest.toEntity());
        userRepository.save(user);
        return UserInfoResponseDTO.from(user);
    }

    // [관리자] 사용자 정보 조회 (페이징)
    public PagingUserListResponseDTO getAllUsers(Pageable pageable, AdminUserSearchRequestDTO query) {
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserInfoResponseDTO> filteredUser = userPage.getContent().stream()
                .filter(user -> filterUser(user, query))
                .map(UserInfoResponseDTO::from)
                .collect(Collectors.toList());

        return PagingUserListResponseDTO.of(userPage, pageable, filteredUser);
    }

    private boolean filterUser(User user, AdminUserSearchRequestDTO query) {
        return (query.track() == null || user.getTrack() == query.track()) &&
                (query.generation() == null || user.getGeneration().equals(query.generation())) &&
                (query.role() == null || user.getRole() == query.role()) &&
                (query.major() == null || user.getMajor().equalsIgnoreCase(query.major()));
    }

    // [관리자] 삭제된 사용자 조회
    public PagingUserListResponseDTO getDeletedUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findDeletedUsers(pageable);

        List<UserInfoResponseDTO> deletedUsers = userPage.getContent().stream()
                .map(UserInfoResponseDTO::from)
                .collect(Collectors.toList());

        return PagingUserListResponseDTO.of(userPage, pageable, deletedUsers);
    }
}
