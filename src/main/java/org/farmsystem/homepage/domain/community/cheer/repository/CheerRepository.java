package org.farmsystem.homepage.domain.community.cheer.repository;

import org.farmsystem.homepage.domain.community.cheer.entity.Cheer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheerRepository extends JpaRepository<Cheer, Long> {

    @EntityGraph(attributePaths = {"cheerer", "cheered"})
    Page<Cheer> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
