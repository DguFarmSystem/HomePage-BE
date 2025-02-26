package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "apply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String major;

    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Track track;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplyStatusEnum status;

    @Builder
    public Apply(String password, String name, String major, String studentNumber, String phoneNumber, String email) {
        this.password = password;
        this.name = name;
        this.major = major;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = ApplyStatusEnum.DRAFT;
    }

    @OneToMany(mappedBy = "apply")
    private List<Answer> answers = new ArrayList<>();

    public void updateStatus(ApplyStatusEnum status) {
        this.status = status;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateMajor(String major) {
        this.major = major;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateTrack(Track track) {
        this.track = track;
    }
}
