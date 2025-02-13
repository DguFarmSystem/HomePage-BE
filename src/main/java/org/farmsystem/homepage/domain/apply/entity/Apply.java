package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "apply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply {

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
    private ApplyTrack track;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplyStatus status;

    @Column(nullable = false, length = 20)
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "apply")
    private List<Answer> answers = new ArrayList<>();
}
