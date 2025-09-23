package org.farmsystem.homepage.domain.minigame.player.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;

@Entity
@Table(name = "badge")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Badge extends BaseTimeEntity {  //칭호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @Column(name = "badge_type", nullable = false)
    private Integer badgeType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    // 플레이어가 새로운 칭호를 획득할 때 사용하는 생성 메서드
    public static Badge createBadge(Player player, Integer badgeType) {
        return new Badge(player, badgeType);
    }

    // 생성은 createBadge만 사용
    private Badge(Player player, Integer badgeType) {
        this.player = player;
        this.badgeType = badgeType;
    }
}
