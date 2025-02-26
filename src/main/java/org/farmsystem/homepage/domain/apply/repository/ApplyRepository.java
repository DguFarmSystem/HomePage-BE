package org.farmsystem.homepage.domain.apply.repository;

import org.farmsystem.homepage.domain.apply.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Optional<Apply> findByStudentNumber(String studentNumber);
}
