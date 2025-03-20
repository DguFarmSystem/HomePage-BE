package org.farmsystem.homepage.domain.apply.dto.response;

import org.apache.commons.csv.CSVRecord;
import org.farmsystem.homepage.domain.apply.entity.PassedApply;
import org.farmsystem.homepage.domain.common.entity.Track;

public record PassedApplyResponseDTO(
        String name,
        String major,
        String studentNumber,
        String track,
        int generation,
        String phoneNumber,
        String notionAccount,
        String githubAccount
) {

    // 실제 CSV 필드명으로 바꾸기
    public static PassedApplyResponseDTO fromCsv(CSVRecord csvRecord) {
        return new PassedApplyResponseDTO(
                csvRecord.get("name"),
                csvRecord.get("major"),
                csvRecord.get("student_number"),
                csvRecord.get("track"),
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
                .track(Track.valueOf(track.toUpperCase()))
                .generation(generation)
                .phoneNumber(phoneNumber)
                .notionAccount(notionAccount)
                .githubAccount(githubAccount)
                .build();
    }
}
