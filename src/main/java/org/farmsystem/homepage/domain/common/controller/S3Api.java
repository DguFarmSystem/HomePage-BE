package org.farmsystem.homepage.domain.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.common.dto.request.PresignedUrlRequestDTO;
import org.farmsystem.homepage.domain.common.dto.response.PresignedUrlResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "S3 API", description = "S3 파일 처리 관련 API")
public interface S3Api {

    @Operation(
            summary = "S3 Presigned URL 생성",
            description = "파일 업로드를 위한 presigned URL을 생성하는 API입니다.(유효 시간: 15분)  \n" +
                    "Presigned URL 사용법 (아래 참고)  \n" +
                    "1. ResponseBody에 directory(파일 경로)와 fileName(파일 이름)을 포함하여 <S3 Presigned URL 생성 API>를 호출합니다. \n" +
                    "2. 응답으로 받은 presigned URL을 복사한 후, PUT 메서드를 사용하여 S3에 업로드할 파일을 binary 형식으로 담아서 호출합니다.  \n" +
                    "3. 업로드된 파일은 S3 버킷에 저장되며, 해당 객체 URL을 통해 파일에 접근할 수 있습니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presigned URL 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PresignedUrlResponseDTO.class)
                    )),
    })
    ResponseEntity<SuccessResponse<?>> generatePresignedUrl(@RequestBody PresignedUrlRequestDTO presignedUrlRequest);
    @Operation(
            summary = "프로필 사진 업로드용 Presigned URL 생성",
            description = "사용자 아이디 기반으로 프로필 사진을 업로드할 수 있는 presigned URL을 생성하는 API입니다. (사용자 정보 수정 API에서 사용)  \n"+
                          "토큰에서 추출한 UserId을 바탕으로 'profile/' 디렉토리에 프로필 이미지를 업로드할 수 있는 presigned URL을 생성합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 사진 Presigned URL 생성 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PresignedUrlResponseDTO.class)
                    )),
    })
    ResponseEntity<SuccessResponse<?>> generateProfilePresignedUrl(Long userId);
}
