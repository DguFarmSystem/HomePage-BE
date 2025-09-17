package org.farmsystem.homepage.domain.blog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.blog.controller.docs.BlogApi;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.domain.blog.service.BlogService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController implements BlogApi {

    private final BlogService blogService;

    /**
     * 블로그 신청
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> applyForBlog(
            @RequestBody @Valid BlogRequestDTO request,
            @AuthenticationPrincipal Long userId
    ) {
        blogService.applyForBlog(request, userId);
        return SuccessResponse.noContent();
    }

    /**
     * 승인된 블로그 목록 조회
     */
    @GetMapping("/approved")
    public ResponseEntity<SuccessResponse<?>> getApprovedBlogs() {
        return SuccessResponse.ok(blogService.getApprovedBlogs());
    }

    /**
     * 내가 신청한 블로그 목록 조회
     */
    @GetMapping("/my-applications")
    public ResponseEntity<SuccessResponse<?>> getMyBlogs(
            @AuthenticationPrincipal Long userId
    ) {
        return SuccessResponse.ok(blogService.getMyBlogs(userId));
    }

    /**
     * 최신 승인된 블로그 페이징 조회
     */
    @GetMapping("/page")
    public ResponseEntity<SuccessResponse<?>> getApprovedBlogsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return SuccessResponse.ok(blogService.getApprovedBlogsPaged(page, size));
    }
}
