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

    @Column(nullable = false, length = 20)
    private String major;

    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Track track;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplyStatus status;

    @Builder
    public Apply(String password, String name, String major, String studentNumber, String phoneNumber, String email) {
        this.password = password;
        this.name = name;
        this.major = major;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = ApplyStatus.DRAFT;
    }

    @OneToMany(mappedBy = "apply")
    private List<Answer> answers = new ArrayList<>();

    public void updateStatus(ApplyStatus status) {
        this.status = status;
    }
}
