package com.test.lsy.restapi250423.common.model;

import com.test.lsy.restapi250423.common.enu.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String code;         // 응답 코드
    private String message;      // 응답 메시지
    private T data;              // 응답 데이터
    private Long totalCount;     // 데이터 총 개수
    private HttpStatus httpStatus; // HTTP 상태 코드

    /**
     * 성공적인 응답 반환
     * @param data 응답 데이터
     * @param totalCount 데이터 총 개수
     * @return 성공 응답 객체
     */
    public static <T> ApiResponse<T> success(T data, Long totalCount) {
        return new ApiResponse<>(ResponseStatus.SUCCESS.getCode(),
                ResponseStatus.SUCCESS.getMsg(),
                data,
                totalCount,
                ResponseStatus.SUCCESS.getHttpStatus());
    }

    /**
     * 데이터 없음 응답 반환 (200 OK)
     * @return 데이터 없음 응답 객체
     */
    public static <T> ApiResponse<T> noData() {
        return new ApiResponse<>(
                ResponseStatus.NO_DATA.getCode(),
                ResponseStatus.NO_DATA.getMsg(),
                null,
                0L,
                ResponseStatus.NO_DATA.getHttpStatus()
        );
    }

    /**
     * 실패 응답 반환 (서버 에러)
     * @param message 에러 메시지
     * @param status HTTP 상태 코드
     * @return 실패 응답 객체
     */
    public static <T> ApiResponse<T> fail(String message, HttpStatus status) {
        return new ApiResponse<>(ResponseStatus.SERVER_FAIL.getCode(),
                ResponseStatus.SERVER_FAIL.getMsg(),
                null,
                null,
                status);
    }

    /**
     * 잘못된 요청 응답 반환 (400 Bad Request)
     * @param status HTTP 상태 코드
     * @return 실패 응답 객체
     */
    public static <T> ApiResponse<T> fail(HttpStatus status) {
        return fail(ResponseStatus.BAR_REQUEST.getMsg(), status);
    }
}
