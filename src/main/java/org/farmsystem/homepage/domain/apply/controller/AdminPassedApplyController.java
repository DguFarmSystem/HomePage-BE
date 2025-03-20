package org.farmsystem.homepage.domain.apply.controller;


import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.service.PassedApplyService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/passed-apply")
public class AdminPassedApplyController {

    private final PassedApplyService passedApplyService;

    //csv파일로 합격자(회원) 리스트 등록 API
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<?>> savePassers(@RequestBody MultipartFile csvFile) {
        passedApplyService.savePassers(csvFile);
        return SuccessResponse.ok(null);
    }
}
