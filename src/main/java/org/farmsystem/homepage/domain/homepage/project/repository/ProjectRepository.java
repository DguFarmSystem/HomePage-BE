package org.farmsystem.homepage.domain.homepage.project.repository;

import org.farmsystem.homepage.domain.common.entity.Track;
import org.farmsystem.homepage.domain.homepage.project.entity.ApprovalStatus;
import org.farmsystem.homepage.domain.homepage.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByApprovalStatus(ApprovalStatus status);

    List<Project> findByUser_UserId(Long userId);


    Page<Project> findByApprovalStatus(ApprovalStatus status, Pageable pageable);


    Page<Project> findByApprovalStatusAndGeneration(
            ApprovalStatus status,
            Integer generation,
            Pageable pageable
    );

    Page<Project> findByApprovalStatusAndTrack(
            ApprovalStatus status,
            Track track,
            Pageable pageable
    );

    Page<Project> findByApprovalStatusAndGenerationAndTrack(
            ApprovalStatus status,
            Integer generation,
            Track track,
            Pageable pageable
    );
}
