package org.farmsystem.homepage.domain.apply.repository;

import org.farmsystem.homepage.domain.apply.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
