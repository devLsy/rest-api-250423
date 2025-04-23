package com.test.lsy.restapi250423.common.enu;

import com.test.lsy.restapi250423.common.interfaces.CodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Slf4j
public enum ResponseStatus implements CodeEnum {
    SUCCESS("1", "성공", HttpStatus.OK),
    NO_DATA("2", "데이터 없음", HttpStatus.NO_CONTENT),
    FAIL("-1", "실패", HttpStatus.BAD_GATEWAY);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
