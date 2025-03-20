package org.farmsystem.homepage.domain.apply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.apply.dto.request.PassedApplyRegisterRequestDTO;
import org.farmsystem.homepage.domain.apply.dto.response.PassedApplyRegisterResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "[관리자] 합격자 API", description = "관리자용 합격자 관리")
public interface AdminPassedApplyApi {

    @Operation(
            summary = "합격자(회원) 개별 등록 API",
            description = "관리자가 사용자 스스로 회원가입할 수 있도록 합격자(회원 인증 정보)를 등록하는 API입니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 등록 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PassedApplyRegisterResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "400", description = "이미 등록된 회원")
    })
    ResponseEntity<SuccessResponse<?>> registerPasser(
            @RequestBody PassedApplyRegisterRequestDTO adminUserRegisterRequest
    );


    @Operation(
            summary = "CSV 파일로 합격자(회원) 리스트 등록",
            description = "관리자가 CSV 파일을 업로드하여 합격자 리스트(회원 인증 정보)를 등록하는 API입니다.  \n" +
                    "CSV 파일 필드 이름 변경에 따라 코드 수정이 필요할 수 있습니다.(변경시 알림 필요)",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CSV 파일을 통한 합격자 리스트 등록 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "요청된 CSV 파일이 없음"),
            @ApiResponse(responseCode = "409", description = "CSV파일에 중복된 학번 (이미 등록된 회원) 포함"),
            @ApiResponse(responseCode = "500", description = "CSV 파일 파싱 실패")
    })
    ResponseEntity<SuccessResponse<?>> registerPassers(
            @RequestBody MultipartFile csvFile
    );
}
