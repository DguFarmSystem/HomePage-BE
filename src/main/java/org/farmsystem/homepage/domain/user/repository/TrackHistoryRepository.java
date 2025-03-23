package org.farmsystem.homepage.domain.user.repository;

import org.farmsystem.homepage.domain.user.entity.TrackHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackHistoryRepository extends JpaRepository<TrackHistory, Long> {
}
