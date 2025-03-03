package org.farmsystem.homepage.domain.apply.repository;

import org.farmsystem.homepage.domain.apply.entity.ApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyStatusRepository extends JpaRepository<ApplyStatus, Long> {

    boolean existsByStudentNumber(String studentNumber);
}
