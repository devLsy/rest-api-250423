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

    private String code;
    private String message;
    private T data;
    private Long totalCount;
    private HttpStatus httpStatus;

    public static <T> ApiResponse<T> success(T data, Long totalCount) {
        return new ApiResponse<>(ResponseStatus.SUCCESS.getCode(),
                ResponseStatus.SUCCESS.getMsg(),
                data,
                totalCount,
                ResponseStatus.SUCCESS.getHttpStatus());
    }

    public static <T> ApiResponse<T> noData() {
        return new ApiResponse<>(
                ResponseStatus.NO_DATA.getCode(),
                ResponseStatus.NO_DATA.getMsg(),
                null,
                0L,
                ResponseStatus.NO_DATA.getHttpStatus()
        );
    }

    public static <T> ApiResponse<T> fail(String message, HttpStatus status) {
        return new ApiResponse<>(ResponseStatus.SERVER_FAIL.getCode(),
                ResponseStatus.SERVER_FAIL.getMsg(),
                null,
                null,
                status);
    }

    public static <T> ApiResponse<T> fail(HttpStatus status) {
        return fail(ResponseStatus.BAR_REQUEST.getMsg(), status);
    }
}
