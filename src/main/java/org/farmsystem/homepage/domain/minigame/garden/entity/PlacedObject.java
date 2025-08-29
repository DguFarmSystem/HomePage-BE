package org.farmsystem.homepage.domain.minigame.garden.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.minigame.inventory.entity.Store;

@Entity
@Table(name = "placed_object")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// TODO: 진수) 이것도 이름 바꾸고 싶은데 고민.. 소은) 그냥 유지하고 placed_object_id 안되나? 꼭 placed를 붙여야 하는 이유가 있다면 그렇게 하는 게 맞는 거 같은데...
// 하연 ) 상관없는데 아이디랑 테이블 명 통일만 해줬으면 좋겠다.
// 소은 ) 인정.
public class PlacedObject extends BaseTimeEntity {  //인벤토리에서 보유한 오브젝트 하나를 삭제하고 정원에 하나를 추가하여 배치함.

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
    @JoinColumn(
            name = "object_kind", nullable = false,
            referencedColumnName = "store_goods_number" // Store 테이블에서 참조할 컬럼명(DB 컬럼명)
    )
    // TODO: objectType으로 통일 (ex. TileType)
    private Store objectKind;

    //PlacedObject 생성 메소드
    public static PlacedObject createPlacedObject(GardenTile tile, Store objectKind, Rotation rotation) {
        return PlacedObject.builder()
                .tile(tile)
                .objectKind(objectKind)
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

    public void updateObjectKind(Store newKind) {
        this.objectKind = newKind;
    }
}
