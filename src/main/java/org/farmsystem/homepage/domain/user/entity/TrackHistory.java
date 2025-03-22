package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.common.entity.Track;

@NoArgsConstructor
@Table(name = "track_history")
@Entity
public class TrackHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Track track;

    @Column
    private Integer generation;

    public TrackHistory(User user, Track track, Integer generation) {
        this.user = user;
        this.track = track;
        this.generation = generation;
    }

}
