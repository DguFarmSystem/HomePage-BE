package org.farmsystem.homepage.domain.minigame.farm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.farm.dto.request.TileUpdateRequestDTO;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

import java.time.LocalDateTime;

@Entity
@Table(name = "planted_plant")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlantedPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planted_plant_id", nullable = false)
    private Long plantedPlantId;

    @Column(name = "planted_at")
    private LocalDateTime plantedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PlantStatus status;

    @Column(name = "sunlight_count")
    private Integer sunlightCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "farmplot_tile_id")
    private FarmplotTile farmplotTile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id")
    private Player player;

    // 지정된 타일에 새 식물을 심는 정적 팩토리 메서드
    public static PlantedPlant createNewPlant(FarmplotTile tile, Player player, TileUpdateRequestDTO request) {
        return new PlantedPlant(
                tile,
                player,
                request.plantedAt(),
                PlantStatus.fromString(request.status()),
                request.sunlightCount()
        );
    }

    // 식물 상태 업데이트 비즈니스 메서드
    public void updatePlantState(PlantStatus status, Integer sunlightCount, LocalDateTime plantedAt) {
        this.status = status;
        this.sunlightCount = sunlightCount;
        this.plantedAt = plantedAt;
    }

    // private 생성자 → createNewPlant 통해서만 객체 생성
    private PlantedPlant(FarmplotTile farmplotTile, Player player,
                         LocalDateTime plantedAt, PlantStatus status, Integer sunlightCount) {
        this.farmplotTile = farmplotTile;
        this.player = player;
        this.plantedAt = plantedAt;
        this.status = status;
        this.sunlightCount = sunlightCount;
    }
}
