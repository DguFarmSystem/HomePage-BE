package org.farmsystem.homepage.domain.cheer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.farmsystem.homepage.domain.common.entity.BaseTimeEntity;
import org.farmsystem.homepage.domain.user.entity.User;

@Entity
@Getter
@Table(name = "cheer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cheer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cheerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheerer_id")
    private User cheerer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheered_id")
    private User cheered;

    private String content;

    private CheerTag tag;
}
