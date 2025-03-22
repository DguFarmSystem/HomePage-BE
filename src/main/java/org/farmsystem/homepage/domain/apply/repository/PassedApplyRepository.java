package org.farmsystem.homepage.domain.apply.repository;

import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassedApplyRepository extends JpaRepository<PassedApply, Long> {
    Optional<PassedApply> findByStudentNumber(String studentNumber);

    boolean existsByStudentNumber(String studentNumber);
}
