package org.farmsystem.homepage.domain.community.farmingLog.repository;

import org.farmsystem.homepage.domain.community.farmingLog.entity.FarmingLog;
import org.farmsystem.homepage.domain.community.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmingLogRepository extends JpaRepository<FarmingLog, Long> {

    Page<FarmingLog> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<FarmingLog> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
