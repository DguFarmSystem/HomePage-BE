package org.farmsystem.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.apply.repository.PassedApplyRepository;
import org.farmsystem.homepage.domain.user.dto.request.UserVerifyRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserVerifyResponseDTO;
import org.farmsystem.homepage.global.error.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.farmsystem.homepage.global.error.ErrorCode.AUTHENTICATION_FAILED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final PassedApplyRepository passedApplyRepository;

    public UserVerifyResponseDTO verifyUser(UserVerifyRequestDTO userVerifyRequest) {
        System.out.println("studentNumber = " + userVerifyRequest.studentNumber());
        PassedApply verifiedUser = passedApplyRepository.findByStudentNumber(userVerifyRequest.studentNumber())
                .orElseThrow(() -> new UnauthorizedException(AUTHENTICATION_FAILED));
        System.out.println("verifiedUser = " + verifiedUser.getName());
        return new UserVerifyResponseDTO(true, verifiedUser.getName());
    }
}
