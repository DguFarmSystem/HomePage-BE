package org.farmsystem.homepage.domain.apply.repository;

import org.farmsystem.homepage.domain.apply.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByApplyApplyIdAndQuestionQuestionId(Long applyId, Long questionId);
}
