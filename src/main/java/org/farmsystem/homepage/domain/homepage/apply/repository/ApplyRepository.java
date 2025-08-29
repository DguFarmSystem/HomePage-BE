package org.farmsystem.homepage.domain.homepage.apply.repository;

import org.farmsystem.homepage.domain.homepage.apply.entity.Apply;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    @EntityGraph(attributePaths = {"answers"})
    List<Apply> findAllByStudentNumber(String studentNumber);

    @EntityGraph(attributePaths = {"answers"})
    @Query("SELECT a FROM Apply a WHERE a.status = org.farmsystem.homepage.domain.homepage.apply.entity.ApplyStatusEnum.SUBMITTED")
    List<Apply> findAllSubmitted();

    @EntityGraph(attributePaths = {"answers"})
    @Query("SELECT a FROM Apply a WHERE a.track = :track AND a.status = org.farmsystem.homepage.domain.homepage.apply.entity.ApplyStatusEnum.SUBMITTED")
    List<Apply> findAllSubmittedByTrack(Track track);
}
