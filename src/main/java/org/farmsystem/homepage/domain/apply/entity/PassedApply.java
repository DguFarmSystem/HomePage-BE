package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.Track;

@Entity
@Getter
@Table(name = "passed_apply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PassedApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passedApplyId;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String major;

    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Enumerated(EnumType.STRING)
    private Track track;

    @Column
    private Integer generation;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String notionAccount;

    @Column(nullable = false, length = 100)
    private String githubAccount;




}
