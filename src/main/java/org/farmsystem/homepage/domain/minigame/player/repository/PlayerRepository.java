
package org.farmsystem.homepage.domain.minigame.player.repository;

import org.farmsystem.homepage.domain.minigame.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    // User의 id로 Player 조회
    Optional<Player> findByUser_UserId(Long userId);
}
