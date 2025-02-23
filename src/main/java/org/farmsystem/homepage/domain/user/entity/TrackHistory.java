package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "track_history")
@Entity
public class TrackHistory {
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



}
