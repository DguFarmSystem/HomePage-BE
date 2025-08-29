package org.farmsystem.homepage.domain.homepage.blog.repository;

import org.farmsystem.homepage.domain.homepage.blog.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.homepage.blog.entity.Blog;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByApprovalStatus(ApprovalStatus status);
    List<Blog> findByUser_UserId(Long userId);
    boolean existsByUserAndLink(User user, String link);
    Page<Blog> findByApprovalStatus(ApprovalStatus status, Pageable pageable);

}
