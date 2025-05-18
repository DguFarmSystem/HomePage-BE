package org.farmsystem.homepage.domain.news.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "소식 작성 요청 DTO")
public record NewsRequestDTO(
        @Schema(description = "소식 제목", example = "파밍 시스템 리뉴얼 안내")
        String title,

        @Schema(description = "소식 본문", example = "파밍 시스템이 새롭게 리뉴얼되었습니다. 자세한 사항은 홈페이지를 참고하세요.")
        String content,

        @Schema(description = "썸네일 이미지 URL", example = "https://s3-bucket/thumbnail.jpg")
        String thumbnailUrl,

        @Schema(description = "본문에 첨부될 이미지 URL 목록", example = "[\"https://s3-bucket/image1.jpg\", \"https://s3-bucket/image2.jpg\"]")
        List<String> imageUrls,

        @Schema(description = "태그 목록", example = "[\"#활동\", \"#이벤트\"]")
        List<String> tags
) {}

