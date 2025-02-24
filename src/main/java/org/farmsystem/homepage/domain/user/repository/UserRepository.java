package org.farmsystem.homepage.domain.user.repository;

import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByStudentNumber(String studentNumber);
}
