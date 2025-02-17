package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
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
    @Column(length = 500)
    private String profileImageUrl;

    @Setter
    @Column(nullable = true, length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Track track;

    @Column(nullable = true)
    private int generation;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ColumnDefault("0")
    private int currentSeed;

    @ColumnDefault("0")
    private int totalSeed;

    @OneToMany(mappedBy = "user")
    private List<TrackHistory> trackHistories;

    @Builder
    public User(String profileImageUrl,String name, String studentNumber, SocialType socialType) {
        this.profileImageUrl = profileImageUrl;
        this.name = name;
        this.studentNumber = studentNumber;
        this.socialType = socialType;
    }
}
