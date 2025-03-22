package org.farmsystem.homepage.domain.user.repository;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.user.entity.Role;
import org.farmsystem.homepage.domain.user.entity.SocialType;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> findByStudentNumber(String studentNumber);

    Optional<User> findByName(String name);

    List<User> findByNameJamoStartsWith(String nameJamo);

    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM user WHERE is_deleted = true", nativeQuery = true)
    Page<User> findDeletedUsers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE " +
            "(:track IS NULL OR u.track = :track) AND " +
            "(:generation IS NULL OR u.generation = :generation) AND " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:major IS NULL OR u.major LIKE CONCAT('%', :major, '%'))")
    Page<User> findFilteredUsers(Pageable pageable, Track track, Integer generation, Role role, String major);

}
