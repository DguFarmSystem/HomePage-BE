package org.farmsystem.homepage.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SuccessResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ResponseEntity<SuccessResponse<?>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessCode.OK, data));
    }

    public static <T> ResponseEntity<SuccessResponse<?>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessCode.CREATED, data));
    }

    public static ResponseEntity<SuccessResponse<?>> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(SuccessResponse.of(SuccessCode.NO_CONTENT, null));
    }

    public static <T> SuccessResponse<?> of(SuccessCode successCode, T data) {
        return SuccessResponse.builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

}