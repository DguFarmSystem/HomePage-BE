package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "apply_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Column(nullable = false)
    private ApplyStatusEnum status;
}
