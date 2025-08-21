package org.farmsystem.homepage.domain.minigame.player.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badge")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {  //칭호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @Column(name = "badge_type", nullable = false)
    private Integer badgeType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    public static Badge create(Player player, Integer badgeType) {
        return Badge.builder()
                .badgeType(badgeType)
                .player(player)
                .build();
    }
}
