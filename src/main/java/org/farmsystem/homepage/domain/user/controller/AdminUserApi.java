package org.farmsystem.homepage.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.farmsystem.homepage.domain.user.dto.request.AdminUserSearchRequestDTO;
import org.farmsystem.homepage.domain.user.dto.request.AdminUserUpdateRequestDTO;
import org.farmsystem.homepage.domain.user.dto.response.PagingUserListResponseDTO;
import org.farmsystem.homepage.domain.user.dto.response.UserInfoResponseDTO;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

@Tag(name = "[관리자] 사용자 API", description = "관리자용 사용자 관리 API")
public interface AdminUserApi {

    @Operation(
            summary = "사용자 정보 수정 API",
            description = "관리자가 사용자의 정보를 수정하는 API입니다.  \n" +
                    "사용자가 수정할 수 없는 정보인 role, name, studentNumber, notionAccount, githubAccount, track, generation 중 하나 이상 수정 요청 가능합니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    ResponseEntity<SuccessResponse<?>> updateUser(
            Long userId,
            @RequestBody AdminUserUpdateRequestDTO adminUserUpdateRequest
    );

    @Operation(
            summary = "사용자 삭제",
            description = "관리자가 사용자를 삭제하는 API입니다.",
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 정보 없음")
    })
    ResponseEntity<SuccessResponse<?>> deleteUser(Long userId);

    @Operation(
            summary = "사용자 정보 조회 (전체)",
            description = "관리자가 전체 사용자 정보를 조회하는 API입니다.  \n" +
                    "페이징과 필터링 옵션 중 필요한 값을 requestParam으로 요청하면 됩니다.(없이 요청시 페이징 기본값 & 전체 데이터 제공)  \n" +
                    "- 페이징 요청 옵션: page(페이지 번호), size(페이지 사이즈) + sort(정렬 기준)  \n" +
                    "- 필터링 요청 옵션: name(이름), track(트랙), generation(세대), role(역할)  \n" +
                    "- 페이징 응답 값(pageResponseDTO 스키마 참고하기): pageSize(페이지당 데이터 개수), totalElements(총 데이터 개수), currentPageElements(현재 페이지 데이터 개수), totalPages(총 페이지 개수), currentPage(현재 페이지), sortBy(정렬 기준), hasNextPage(다음 페이지 존재 여부), hasPreviousPage(이전 페이지 존재 여부)  \n" ,
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagingUserListResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    ResponseEntity<SuccessResponse<?>> getAllUsers(
            Pageable pageable,
            @ModelAttribute AdminUserSearchRequestDTO query
    );

    @Operation(
            summary = "삭제된 사용자 조회",
            description = "관리자가 삭제된 사용자 목록을 조회하는 API입니다.  \n" +
                    "페이징 옵션 중 필요한 값을 requestParam으로 요청하면 됩니다.(없이 요청시 페이징 기본값 & 전체 데이터 제공)  \n" +
                    "- 페이징 요청 옵션: page(페이지 번호), size(페이지 사이즈) + sort(정렬 기준)  \n" ,
            security = @SecurityRequirement(name = "token")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제된 사용자 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagingUserListResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터")
    })
    ResponseEntity<SuccessResponse<?>> getDeletedUsers(Pageable pageable);

}
