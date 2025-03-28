package org.farmsystem.homepage.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "ENUM 입력값이 올바르지 않습니다."),
    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "일치하지 않는 리프레시 토큰입니다."),
    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리소스를 찾을 수 없습니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),

    /**
     * 413 Payload Too Large
     */
    FILE_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "이미지 최대 크기를 초과하였습니다."),

    /**
     * 415 Unsupported Media Type
     */
    INVALID_IMAGE_FORMAT(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 이미지 형식입니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다."),

    /**
     * CSV Error
     */
    CSV_FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "업로드할 CSV 파일이 비어 있습니다."),
    CSV_FIELD_MAPPING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 필드 매핑에 실패하였습니다."),
    CSV_FILE_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CSV 파일 변환에 실패하였습니다."),

    /**
     * News Error
     */
    NEWS_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 뉴스를 찾을 수 없습니다."),

    /**
     * Apply Error
     */
    APPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "지원서를 찾을 수 없습니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문을 찾을 수 없습니다."),
    CHOICE_NOT_FOUND(HttpStatus.NOT_FOUND, "선택지를 찾을 수 없습니다."),
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "답변을 찾을 수 없습니다."),
    APPLY_ALREADY_SUBMITTED(HttpStatus.BAD_REQUEST, "이미 제출한 지원서입니다."),
    APPLY_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    APPLY_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 지원서입니다."),

    /**
     * Passed Apply Error
     */
    PASSED_USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록된 합격자 정보입니다."),
    PASSED_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "합격자 정보를 찾을 수 없습니다."),
    DUPLICATE_STUDENT_NUMBER(HttpStatus.CONFLICT, "이미 등록된 학번입니다."),

    /**
     * User Error
     */
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "사용자 인증에 실패하였습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "다른 소셜 계정으로 이미 가입된 사용자입니다."),
    ALREADY_ATTENDANCE(HttpStatus.BAD_REQUEST, "이미 출석이 완료되었습니다."),

    /**
     * Oauth Error
     */
    OAUTH_TOKEN_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth 토큰 요청에 실패하였습니다."),
    OAUTH_USER_RESOURCE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth 사용자 정보 조회에 실패하였습니다."),
    JSON_PARSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 파싱에 실패하였습니다."),

    /**
     * Farming Log Error
     */
    FARMING_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "파밍로그를 찾을 수 없습니다."),

    /**
     * Blog/Project Error
     */
    BLOG_NOT_FOUND(HttpStatus.NOT_FOUND, "블로그를 찾을 수 없습니다."),
    BLOG_DUPLICATED(HttpStatus.CONFLICT, "이미 신청 처리된 블로그입니다."),
    ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, "이미 승인 처리된 블로그입니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),

    /**
     * Notification Error
     */
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),
    NOTIFICATION_ACCESS_DENIED(HttpStatus.FORBIDDEN, "알림에 대한 권한이 없습니다."),

    /**
     * Cheer Error
     */
    SAME_CHEERER_CHEERED(HttpStatus.BAD_REQUEST, "자기 자신을 응원할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
