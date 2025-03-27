package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor
@Table(name = "daily_seed")
@Entity
public class DailySeed extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailySeedId;

    @ColumnDefault("false")
    private boolean isAttendance;

    @ColumnDefault("false")
    private boolean isCheer;

    @ColumnDefault("false")
    private boolean isFarminglog;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public DailySeed(User user) {
        this.user = user;
    }

    public void updateAttendance() {this.isAttendance = true;}
    public void updateCheer() {this.isCheer = true;}
    public void updateFarminglog() {this.isFarminglog = true;}

    public void reset() {
        this.isAttendance = false;
        this.isCheer = false;
        this.isFarminglog = false;
    }
}
