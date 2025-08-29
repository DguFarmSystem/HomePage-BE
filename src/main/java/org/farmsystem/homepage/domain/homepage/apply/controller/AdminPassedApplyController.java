package org.farmsystem.homepage.domain.homepage.apply.controller;


import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.homepage.apply.dto.response.PassedApplyRegisterResponseDTO;
import org.farmsystem.homepage.domain.homepage.apply.service.PassedApplyService;
import org.farmsystem.homepage.domain.homepage.apply.dto.request.PassedApplyRegisterRequestDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/passed-apply")
public class AdminPassedApplyController implements AdminPassedApplyApi {

    private final PassedApplyService passedApplyService;

    //[관리자] csv파일로 합격자(회원) 리스트 등록 API
    @PostMapping(value = "/register-csv", consumes = "multipart/form-data")
    public ResponseEntity<SuccessResponse<?>> registerPassers(@RequestBody MultipartFile csvFile) {
        passedApplyService.registerPassers(csvFile);
        return SuccessResponse.ok(null);
    }


    // [관리자] 합격자(회원) 개별 등록(회원인증 및 가입 가능하도록)
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<?>> registerPasser(@RequestBody PassedApplyRegisterRequestDTO passedApplyRegisterRequest) {
        PassedApplyRegisterResponseDTO registedPasser= passedApplyService.registerPasser(passedApplyRegisterRequest);
        return SuccessResponse.ok(registedPasser);
    }
}
