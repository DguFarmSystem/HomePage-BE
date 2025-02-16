package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.farmsystem.homepage.global.common.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Setter
    @Column(length = 100)
    private String profileImageUrl;

    @Setter
    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Track track;
    private int generation;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ColumnDefault("0")
    private int currentSeed;

    @ColumnDefault("0")
    private int totalSeed;

    @OneToMany(mappedBy = "user")
    private List<TrackHistory> trackHistories;
}
