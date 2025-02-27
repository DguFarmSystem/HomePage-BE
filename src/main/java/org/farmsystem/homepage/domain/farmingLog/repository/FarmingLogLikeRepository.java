package org.farmsystem.homepage.domain.farmingLog.repository;

import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLog;
import org.farmsystem.homepage.domain.farmingLog.entity.FarmingLogLike;
import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmingLogLikeRepository extends JpaRepository<FarmingLogLike, Long> {

    Optional<FarmingLogLike> findByUserAndFarmingLog(User user, FarmingLog farmingLog);
    long countByFarmingLog(FarmingLog farmingLog);
    boolean existsByUserAndFarmingLog(User user, FarmingLog farmingLog);
}
