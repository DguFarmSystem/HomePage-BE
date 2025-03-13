package org.farmsystem.homepage.domain.apply.dto.response;

import lombok.Builder;
import org.farmsystem.homepage.domain.apply.dto.AnswerDTO;
import org.farmsystem.homepage.domain.apply.entity.Apply;
import org.farmsystem.homepage.domain.apply.entity.ApplyStatusEnum;
import org.farmsystem.homepage.domain.common.entity.Track;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record SubmittedApplyResponseDTO(
        Long applyId,
        ApplyStatusEnum status,
        LocalDateTime updatedAt,
        String name,
        String major,
        String studentNumber,
        String phoneNumber,
        String email,
        Track track,
        List<AnswerDTO> answers
) {
    public static SubmittedApplyResponseDTO from(Apply apply) {
        return SubmittedApplyResponseDTO.builder()
                .applyId(apply.getApplyId())
                .status(apply.getStatus())
                .updatedAt(apply.getUpdatedAt())
                .name(apply.getName())
                .major(apply.getMajor())
                .studentNumber(apply.getStudentNumber())
                .phoneNumber(apply.getPhoneNumber())
                .email(apply.getEmail())
                .track(apply.getTrack())
                .answers(apply.getAnswers().stream()
                        .filter(answer -> answer.getQuestion().getTrack() == apply.getTrack())
                        .map(answer -> AnswerDTO.builder()
                                .questionId(answer.getQuestion().getQuestionId())
                                .content(answer.getContent())
                                .choiceId(answer.getAnswerChoices().stream()
                                        .map(answerChoice -> answerChoice.getChoice().getChoiceId())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
