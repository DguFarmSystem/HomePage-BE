package org.farmsystem.homepage.domain.blog.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.blog.dto.request.BlogRequestDTO;
import org.farmsystem.homepage.domain.blog.dto.response.BlogResponseDTO;
import org.farmsystem.homepage.domain.blog.dto.response.MyApplicationResponseDTO;
import org.farmsystem.homepage.domain.blog.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.blog.entity.Blog;
import org.farmsystem.homepage.domain.blog.repository.BlogRepository;
import org.farmsystem.homepage.domain.user.entity.User;
import org.farmsystem.homepage.domain.user.repository.UserRepository;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.util.List;

import static org.farmsystem.homepage.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    /**
     * 블로그 신청
     */
    public void applyForBlog(BlogRequestDTO request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        boolean exists = blogRepository.existsByUserAndLink(user, request.link());
        if (exists) {
            throw new BusinessException(BLOG_DUPLICATED);
        }

        Blog blog = Blog.builder()
                .link(request.link())
                .user(user)
                .categories(request.categories())
                .build();

        blogRepository.save(blog);
    }

    /**
     * 승인된 블로그 목록 조회
     */
    @Transactional(readOnly = true)
    public List<BlogResponseDTO> getApprovedBlogs() {
        return blogRepository.findByApprovalStatus(ApprovalStatus.APPROVED).stream()
                .map(BlogResponseDTO::fromEntity)
                .toList();
    }

    /**
     * '내가 신청한 블로그' 목록 조회
     */
    @Transactional(readOnly = true)
    public List<MyApplicationResponseDTO> getMyBlogs(Long userId) {
        return blogRepository.findByUser_UserId(userId).stream()
                .map(MyApplicationResponseDTO::fromEntity)
                .toList();
    }

    /**
     * 최신 승인된 블로그 페이징 조회
     */
    @Transactional(readOnly = true)
    public Page<BlogResponseDTO> getApprovedBlogsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "approvedAt"));
        return blogRepository.findByApprovalStatus(ApprovalStatus.APPROVED, pageable)
                .map(BlogResponseDTO::fromEntity);
    }
}
