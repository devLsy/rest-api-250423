package com.test.lsy.restapi250423.common.model;

import com.test.lsy.restapi250423.common.enu.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private Meta meta;
    private T data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Meta {
        private String code;
        private String message;
        private HttpStatus httpStatus;
        private Long totalCount;
        private String traceId;
    }

    /**
     * 성공적인 응답 반환
     * @param data 응답 데이터
     * @param totalCount 데이터 총 개수
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data, Long totalCount) {
        return ApiResponse.<T>builder()
                .meta(Meta.builder()
                        .code(ResponseStatus.SUCCESS.getCode())
                        .message(ResponseStatus.SUCCESS.getMsg())
                        .httpStatus(ResponseStatus.SUCCESS.getHttpStatus())
                        .totalCount(totalCount)
                        .traceId(generateTraceId())
                        .build())
                .data(data)
                .build();
    }

    /**
     * 데이터 없음 응답 반환 (200 OK)
     * @return 데이터 없음 응답 객체
     */
    public static <T> ApiResponse<T> noData() {
        return ApiResponse.<T>builder()
                .meta(Meta.builder()
                        .code(ResponseStatus.NO_DATA.getCode())
                        .message(ResponseStatus.NO_DATA.getMsg())
                        .httpStatus(ResponseStatus.NO_DATA.getHttpStatus())
                        .totalCount(0L)
                        .traceId(generateTraceId())
                        .build())
                .data(null)
                .build();
    }

    /**
     * 실패 응답 반환 (서버 에러)
     * @param message 에러 메시지
     * @param status HTTP 상태 코드
     * @return 실패 응답 객체
     */
    public static <T> ApiResponse<T> fail(String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .meta(Meta.builder()
                        .code(ResponseStatus.SERVER_FAIL.getCode())
                        .message(message)
                        .httpStatus(status)
                        .traceId(generateTraceId())
                        .build())
                .data(null)
                .build();
    }

    /**
     * 잘못된 요청 응답 반환 (400 Bad Request)
     * @param status HTTP 상태 코드
     * @return 실패 응답 객체
     */
    public static <T> ApiResponse<T> fail(HttpStatus status) {
        return fail(ResponseStatus.BAR_REQUEST.getMsg(), status);
    }

    /**
     * Trace ID 생성기 (UUID 사용)
     */
    private static String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}
