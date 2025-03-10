package org.farmsystem.homepage.domain.user.repository;

import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> findByStudentNumber(String studentNumber);

    Optional<User> findByName(String name);

    List<User> findByNameJamoStartsWith(String nameJamo);
}
