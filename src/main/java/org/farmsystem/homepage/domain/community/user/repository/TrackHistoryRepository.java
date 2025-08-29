package org.farmsystem.homepage.domain.community.user.repository;

import org.farmsystem.homepage.domain.community.user.entity.TrackHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackHistoryRepository extends JpaRepository<TrackHistory, Long> {
}
