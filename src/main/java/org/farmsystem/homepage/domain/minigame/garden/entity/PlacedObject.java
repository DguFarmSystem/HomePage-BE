package org.farmsystem.homepage.domain.minigame.garden.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Goods;

@Entity
@Table(name = "placed_object")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PlacedObject extends BaseTimeEntity {  //인벤토리에서 보유한 오브젝트 하나를 삭제하고 정원에 하나를 추가하여 배치함.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placed_object_id", nullable = false)
    private Long placedObjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rotation", nullable = false)
    private Rotation rotation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "garden_tile_id", nullable = false)
    private GardenTile tile;

    // 오브젝트 종류
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "object_type", nullable = false,
            referencedColumnName = "goods_number" // Goods 테이블에서 참조할 컬럼명(DB 컬럼명)
    )
    // TODO: objectType으로 통일 (ex. TileType)
    private Goods objectType;

    //PlacedObject 생성 메소드
    public static PlacedObject createPlacedObject(GardenTile tile, Goods objectType, Rotation rotation) {
        return PlacedObject.builder()
                .tile(tile)
                .objectType(objectType)
                .rotation(rotation)
                .build();
    }

    //업데이트
    public void updatePlacedLocation(GardenTile newTile) {
        this.tile = newTile;
    }

    public void updateRotation(Rotation newRotation) {
        this.rotation = newRotation;
    }

    public void updateObjectType(Goods newObjectType) {
        this.objectType = newObjectType;
    }
}
