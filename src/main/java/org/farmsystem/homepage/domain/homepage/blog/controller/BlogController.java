package org.farmsystem.homepage.domain.homepage.blog.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.homepage.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.domain.homepage.blog.dto.response.BlogResponseDTO;
import org.farmsystem.homepage.domain.homepage.blog.dto.response.MyApplicationResponseDTO;
import org.farmsystem.homepage.domain.homepage.blog.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    /**
     * 블로그 신청
     */
    @PostMapping
    public ResponseEntity<Void> applyForBlog(
            @RequestBody BlogRequestDTO request,
            @AuthenticationPrincipal Long userId
    ) {
        blogService.applyForBlog(request, userId);
        return ResponseEntity.ok().build();
    }


    /**
     * 승인된 블로그 목록 조회
     */
    @GetMapping("/approved")
    public ResponseEntity<List<BlogResponseDTO>> getApprovedBlogs() {
        return ResponseEntity.ok(blogService.getApprovedBlogs());
    }

    /**
     * 사용자가 신청한 블로그 목록 조회
     */
    @GetMapping("/my")
    public ResponseEntity<List<MyApplicationResponseDTO>> getMyBlogs(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(blogService.getMyBlogs(userId));
    }

    /**
     * 홍보 페이지에서 게시(승인)된 블로그 페이지로 조회
     */
    @GetMapping("/page")
    public ResponseEntity<Page<BlogResponseDTO>> getApprovedBlogsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(blogService.getApprovedBlogsPaged(page, size));
    }

}
