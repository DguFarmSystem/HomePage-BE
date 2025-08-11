package org.farmsystem.homepage.domain.minigame.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.minigame.farm.entity.PlantType;
import org.farmsystem.homepage.domain.minigame.player.entity.Player;

@Entity
@Table(name = "plant_inventory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantInventory {  //수확한 식물을 관리하는 인벤토리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "harvest_id", nullable = false)
    private Long harvestId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_type")
    private PlantType plantType;
}
