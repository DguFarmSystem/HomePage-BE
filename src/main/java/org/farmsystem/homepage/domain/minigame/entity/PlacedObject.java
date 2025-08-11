package org.farmsystem.homepage.domain.minigame.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "placed_object")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacedObject {  //인벤토리에서 보유한 오브젝트 하나를 삭제하고 정원에 하나를 추가하여 배치함.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rotation", nullable = false)
    private Rotation rotation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tile_id", nullable = false)
    private GardenTile tile;

    // 오브젝트 종류
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "object_kind", nullable = false)
    private Store objectKind;
}
