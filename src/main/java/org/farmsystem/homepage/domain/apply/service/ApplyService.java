package org.farmsystem.homepage.domain.apply.service;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.apply.dto.*;
import org.farmsystem.homepage.domain.apply.entity.*;
import org.farmsystem.homepage.domain.apply.exception.*;
import org.farmsystem.homepage.domain.apply.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyService {

    private final QuestionRepository questionRepository;
    private final ApplyRepository applyRepository;
    private final AnswerRepository answerRepository;
    private final ChoiceRepository choiceRepository;
    private final AnswerChoiceRepository answerChoiceRepository;

    public List<QuestionDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> QuestionDto.builder()
                        .questionId(question.getQuestionId())
                        .track(question.getTrack())
                        .isRequired(question.getIsRequired())
                        .content(question.getContent())
                        .maxLength(question.getMaxLength())
                        .type(question.getType())
                        .isDuplicated(question.getIsDuplicated())
                        .priority(question.getPriority())
                        .choices(question.getChoices().stream()
                                .map(choice -> ChoiceDto.builder()
                                        .choiceId(choice.getChoiceId())
                                        .content(choice.getContent())
                                        .build())
                                .collect(Collectors.toList()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Transactional
    public CreateApplyResponseDto createApply(CreateApplyRequestDto request) {
        Apply apply = Apply.builder()
                .password(request.password())
                // TODO: password 암호화
                .name(request.name())
                .major(request.major())
                .studentNumber(request.studentNumber())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .build();
        Apply savedApply = applyRepository.save(apply);
        return CreateApplyResponseDto.builder()
                .applyId(savedApply.getApplyId())
                .build();
    }

    @Transactional
    public ApplyResponseDto saveApply(ApplyRequestDto request, boolean submitFlag) {
        Apply apply = applyRepository.findById(request.applyId())
                .orElseThrow(ApplyNotFoundException::new);
        if (apply.getStatus() == ApplyStatus.SUBMITTED) {
            throw new ApplyAlreadySubmittedException();
        }
        handleApplyStatus(apply, submitFlag);
        switch (apply.getStatus()) {
            case DRAFT -> handleDraftStatus(apply, request);
            case SAVED -> handleSavedStatus(request);
        }
        return ApplyResponseDto.builder()
                .applyId(apply.getApplyId())
                .build();
    }

    public LoadApplyResponseDto loadApply(LoadApplyRequestDto request) {
        // TODO: password 암호화
        Apply apply = applyRepository.findByStudentNumberAndPassword(request.studentNumber(), request.password())
                .orElseThrow(ApplyNotFoundException::new);
        return LoadApplyResponseDto.builder()
                .applyId(apply.getApplyId())
                .status(apply.getStatus())
                .updatedAt(apply.getUpdatedAt())
                .answers(apply.getAnswers().stream()
                        .map(answer -> AnswerDto.builder()
                                .questionId(answer.getQuestion().getQuestionId())
                                .content(answer.getContent())
                                .choiceId(answer.getAnswerChoices().stream()
                                        .map(answerChoice -> answerChoice.getChoice().getChoiceId())
                                        .collect(Collectors.toList())
                                )
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    private void handleApplyStatus(Apply apply, boolean isSubmit) {
        if (isSubmit) {
            apply.updateStatus(ApplyStatus.SUBMITTED);
        } else {
            if (apply.getStatus() == ApplyStatus.DRAFT) {
                apply.updateStatus(ApplyStatus.SAVED);
            }
        }
    }

    private void handleDraftStatus(Apply apply, ApplyRequestDto request) {
        for (AnswerDto answer : request.answers()) {
            Question question = questionRepository.findById(answer.questionId())
                    .orElseThrow(QuestionNotFoundException::new);
            Answer savedAnswer = answerRepository.save(Answer.builder()
                    .content(answer.content())
                    .apply(apply)
                    .question(question)
                    .build());
            saveAnswerChoices(answer, savedAnswer);
        }
    }

    private void handleSavedStatus(ApplyRequestDto request) {
        for (AnswerDto answer : request.answers()) {
            Answer savedAnswer = answerRepository.findByApplyApplyIdAndQuestionQuestionId(request.applyId(), answer.questionId())
                    .orElseThrow(AnswerNotFoundException::new);
            savedAnswer.updateContent(answer.content());
            answerChoiceRepository.deleteByAnswerAnswerId(savedAnswer.getAnswerId());
            saveAnswerChoices(answer, savedAnswer);
        }
    }

    private void saveAnswerChoices(AnswerDto answer, Answer savedAnswer) {
        if (answer.choiceId() != null) {
            for (Long choiceId : answer.choiceId()) {
                Choice choice = choiceRepository.findById(choiceId)
                        .orElseThrow(ChoiceNotFoundException::new);
                AnswerChoice answerChoice = AnswerChoice.builder()
                        .id(AnswerChoiceId.builder()
                                .answerId(savedAnswer.getAnswerId())
                                .choiceId(choiceId)
                                .build()
                        )
                        .answer(savedAnswer)
                        .choice(choice)
                        .build();
                answerChoiceRepository.save(answerChoice);
            }
        }
    }
}
