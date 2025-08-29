package org.farmsystem.homepage.domain.homepage.apply.repository;

import org.farmsystem.homepage.domain.homepage.apply.entity.AnswerChoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerChoiceRepository extends JpaRepository<AnswerChoice, Long> {

    void deleteByAnswerAnswerId(Long answerId);
}
