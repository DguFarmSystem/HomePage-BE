package org.farmsystem.homepage.domain.blog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.blog.controller.docs.AdminBlogApi;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.domain.blog.service.AdminBlogService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
public class AdminBlogController implements AdminBlogApi {

    private final AdminBlogService adminBlogService;

    /**
     * 관리자 - 블로그 승인
     */
    @PatchMapping("/{blogId}/approve")
    public ResponseEntity<SuccessResponse<?>> approveBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal Long userId
    ) {
        return SuccessResponse.ok(adminBlogService.approveBlog(blogId, userId));
    }

    /**
     * 관리자 - 블로그 거절
     */
    @PatchMapping("/{blogId}/reject")
    public ResponseEntity<SuccessResponse<?>> rejectBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal Long userId
    ) {
        adminBlogService.rejectBlog(blogId, userId);
        return SuccessResponse.noContent();
    }

    /**
     * 관리자 - 승인 대기 중인 블로그 목록 조회
     */
    @GetMapping("/pending")
    public ResponseEntity<SuccessResponse<?>> getPendingBlogs() {
        return SuccessResponse.ok(adminBlogService.getPendingBlogs());
    }

    /**
     * 관리자 - 블로그 직접 생성
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createBlog(
            @RequestBody @Valid BlogRequestDTO request,
            @AuthenticationPrincipal Long userId
    ) {
        return SuccessResponse.ok(adminBlogService.createBlog(request, userId));
    }
}