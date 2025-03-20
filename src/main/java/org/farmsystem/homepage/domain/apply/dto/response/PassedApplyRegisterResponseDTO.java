package org.farmsystem.homepage.domain.apply.dto.response;

import org.apache.commons.csv.CSVRecord;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.common.entity.Track;

public record PassedApplyRegisterResponseDTO(
        String name,
        String major,
        String studentNumber,
        Track track,
        Integer generation,
        String phoneNumber,
        String notionAccount,
        String githubAccount
) {
    public static PassedApplyRegisterResponseDTO from(PassedApply passedApply) {
        return new PassedApplyRegisterResponseDTO(
                passedApply.getName(),
                passedApply.getMajor(),
                passedApply.getStudentNumber(),
                passedApply.getTrack(),
                passedApply.getGeneration(),
                passedApply.getPhoneNumber(),
                passedApply.getNotionAccount(),
                passedApply.getGithubAccount()
        );
    }

    // 실제 CSV 필드명으로 바꾸기
    public static PassedApplyRegisterResponseDTO fromCsv(CSVRecord csvRecord) {
        return new PassedApplyRegisterResponseDTO(
                csvRecord.get("name"),
                csvRecord.get("major"),
                csvRecord.get("student_number"),
                Track.valueOf(csvRecord.get("track")),
                Integer.parseInt(csvRecord.get("generation")),
                csvRecord.get("phone_number"),
                csvRecord.get("notion_account"),
                csvRecord.get("github_account")
        );
    }

    public PassedApply toEntity() {
        return PassedApply.builder()
                .name(name)
                .major(major)
                .studentNumber(studentNumber)
                .track(track)
                .generation(generation)
                .phoneNumber(phoneNumber)
                .notionAccount(notionAccount)
                .githubAccount(githubAccount)
                .build();
    }
}
