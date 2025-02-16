package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;


@NoArgsConstructor
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Column(length = 500)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ColumnDefault("0")
    private int currentSeed;

    @ColumnDefault("0")
    private int totalSeed;

    @OneToMany(mappedBy = "user")
    private List<TrackHistory> trackHistories;
}
