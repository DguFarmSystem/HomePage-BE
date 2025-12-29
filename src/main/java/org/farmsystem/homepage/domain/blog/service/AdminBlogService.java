package org.farmsystem.homepage.domain.blog.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.domain.blog.dto.response.BlogApprovalResponseDTO;
import org.farmsystem.homepage.domain.blog.dto.response.PendingBlogResponseDTO;
import org.farmsystem.homepage.domain.blog.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.blog.entity.Blog;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.blog.repository.BlogRepository;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.farmsystem.homepage.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminBlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    /**
     * 승인 대기 중인 블로그 목록 조회
     */
    public List<PendingBlogResponseDTO> getPendingBlogs() {
        return blogRepository.findByApprovalStatus(ApprovalStatus.PENDING).stream()
                .map(PendingBlogResponseDTO::fromEntity)
                .toList();
    }

    /**
     * 블로그 승인
     */
    @Transactional
    public BlogApprovalResponseDTO approveBlog(Long blogId, Long adminUserId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException(BLOG_NOT_FOUND));

        User admin = userRepository.findById(adminUserId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        blog.approve(admin);

        return BlogApprovalResponseDTO.fromEntity(blog, admin);
    }

    /**
     * 블로그 거절
     */
    @Transactional
    public void rejectBlog(Long blogId, Long adminUserId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException(BLOG_NOT_FOUND));

        User admin = userRepository.findById(adminUserId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        blog.reject(admin);
    }


    /**
     * 관리자 - 블로그 직접 생성
     * 승인 상태를 바로 APPROVED로 설정합니다.
     */
    @Transactional
    public BlogApprovalResponseDTO createBlog(BlogRequestDTO request, Long adminUserId) {
        User admin = userRepository.findById(adminUserId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        boolean exists = blogRepository.existsByUserAndLink(admin, request.link());
        if (exists) {
            throw new BusinessException(BLOG_DUPLICATED);
        }

        Blog blog = Blog.createByAdmin(request, admin);
        blogRepository.save(blog);

        return BlogApprovalResponseDTO.fromEntity(blog, admin);
    }
}