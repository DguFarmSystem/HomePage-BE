package org.farmsystem.homepage.domain.apply.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "apply_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyStatus {

    @Id
    @Column(nullable = false, length = 20)
    private String studentNumber;

    @Builder
    public ApplyStatus(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
