package org.farmsystem.homepage.domain.apply.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.farmsystem.homepage.domain.apply.dto.response.PassedApplyRegisterResponseDTO;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.apply.repository.PassedApplyRepository;
import org.farmsystem.homepage.domain.apply.dto.request.PassedApplyRegisterRequestDTO;
import org.farmsystem.homepage.global.error.exception.BusinessException;
import org.farmsystem.homepage.global.error.exception.IllegalArgumentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.farmsystem.homepage.global.error.ErrorCode.*;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PassedApplyService {

    private final PassedApplyRepository passedApplyRepository;

    // [관리자] 합격자(회원) 개별 등록
    @Transactional
    public PassedApplyRegisterResponseDTO registerPasser(PassedApplyRegisterRequestDTO passedApplyRegisterRequest) {
        passedApplyRepository.findByStudentNumber(passedApplyRegisterRequest.studentNumber())
                .ifPresent(user -> {throw new BusinessException(PASSED_USER_ALREADY_EXISTS);});
        PassedApply registeredPasser = passedApplyRepository.save(passedApplyRegisterRequest.toEntity());
        return PassedApplyRegisterResponseDTO.from(registeredPasser);
    }

    // [관리자] csv파일로 합격자(회원) 리스트 등록
    @Transactional
    public void registerPassers(MultipartFile csvFile) {
        if (csvFile.isEmpty()) {
            throw new IllegalArgumentException(CSV_FILE_IS_EMPTY);
        }
        List<PassedApply> passedApplies = csvToPassedApplyList(csvFile);
        passedApplyRepository.saveAll(passedApplies);
    }

    public List<PassedApply> csvToPassedApplyList(MultipartFile csvFile) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            return csvParser.getRecords().stream()
                    .map(record -> {
                        PassedApplyRegisterResponseDTO passedApplyResponse = PassedApplyRegisterResponseDTO.fromCsv(record);

                        // 학번 중복 체크(중복 등록 방지)
                        if (passedApplyRepository.existsByStudentNumber(passedApplyResponse.studentNumber())) {
                            throw new BusinessException(DUPLICATE_STUDENT_NUMBER);
                        }
                        return passedApplyResponse.toEntity();
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("CSV 파일 파싱 실패", e);
            throw new BusinessException(CSV_FILE_PARSING_FAILED);
        }
    }

}
