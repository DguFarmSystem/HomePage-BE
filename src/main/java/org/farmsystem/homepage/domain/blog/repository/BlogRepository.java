package org.farmsystem.homepage.domain.blog.repository;

import org.farmsystem.homepage.domain.blog.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.blog.entity.Blog;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByApprovalStatus(ApprovalStatus status);
    List<Blog> findByUser_UserId(Long userId);
    boolean existsByUserAndLink(User user, String link);

}
