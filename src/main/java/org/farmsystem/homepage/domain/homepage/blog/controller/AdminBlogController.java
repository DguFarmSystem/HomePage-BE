package org.farmsystem.homepage.domain.homepage.blog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.homepage.blog.dto.response.BlogApprovalResponseDTO;
import org.farmsystem.homepage.domain.homepage.blog.dto.response.PendingBlogResponseDTO;
import org.farmsystem.homepage.domain.homepage.blog.service.AdminBlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
public class AdminBlogController {

    private final AdminBlogService adminBlogService;

    /**
     * 관리자 - 블로그 승인
     */
    @PatchMapping("/{blogId}/approve")
    public ResponseEntity<BlogApprovalResponseDTO> approveBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal Long userId
    ) {
        return ResponseEntity.ok(adminBlogService.approveBlog(blogId, userId));
    }

    /**
     * 관리자 - 블로그 거절
     */
    @PatchMapping("/{blogId}/reject")
    public ResponseEntity<Void> rejectBlog(
            @PathVariable Long blogId,
            @AuthenticationPrincipal Long userId
    ) {
        adminBlogService.rejectBlog(blogId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 관리자 - 승인 대기 중인 블로그 목록 조회
     */
    @GetMapping("/pending")
    public ResponseEntity<List<PendingBlogResponseDTO>> getPendingBlogs() {
        return ResponseEntity.ok(adminBlogService.getPendingBlogs());
    }
}
