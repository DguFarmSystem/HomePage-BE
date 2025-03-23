package org.farmsystem.homepage.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.farmsystem.homepage.domain.apply.entity.Apply;
import org.farmsystem.homepage.domain.cheer.entity.Cheer;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.common.util.JamoUtil;
import org.farmsystem.homepage.domain.notification.entity.Notification;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
@Where(clause = "is_deleted = false")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column
    private String socialId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String studentNumber;

    @Column(nullable = false, length = 50)
    private String major;

    @Column(length = 500)
    private String profileImageUrl;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String notionAccount;

    @Column(nullable = false, length = 100)
    private String githubAccount;

    @Enumerated(EnumType.STRING)
    private Track track;

    // 최근 활동 기수
    @Column
    private Integer generation;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ColumnDefault("0")
    private int totalSeed;

    @Column(nullable = false, length = 100)
    private String nameJamo;

    @OneToMany(mappedBy = "user")
    private List<TrackHistory> trackHistories;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
  
    @OneToMany(mappedBy = "cheerer")
    private List<Cheer> sentCheers = new ArrayList<>();

    @OneToMany(mappedBy = "cheered")
    private List<Cheer> receivedCheers = new ArrayList<>();

    @PrePersist
    public void initNameJamo() {
        this.nameJamo = JamoUtil.convertToJamo(this.name);
    }

    public void delete() {this.isDeleted = true;}

    public void updateUserByAdmin(User user) {
        if (user.getRole() != null) this.role = user.getRole();
        if (user.getStudentNumber() != null) this.studentNumber = user.getStudentNumber();
        if (user.getMajor() != null) this.major = user.getMajor();
        if (user.getTrack() != null) this.track = user.getTrack();
        if (user.getGeneration() != null) this.generation = user.getGeneration();
        if (user.getName() != null) {this.name = user.getName(); this.nameJamo = JamoUtil.convertToJamo(this.name);
        }
    }

    public void updateUser(User user){
        if (user.getProfileImageUrl() != null) this.profileImageUrl = user.getProfileImageUrl();
        if (user.getPhoneNumber() != null) this.phoneNumber = user.getPhoneNumber();
        if (user.getNotionAccount() != null) this.notionAccount = user.getNotionAccount();
        if (user.getGithubAccount() != null) this.githubAccount = user.getGithubAccount();
    }

}
