package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false, length = 20)
    private String studentNumber;
}
