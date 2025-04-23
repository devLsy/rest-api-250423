package com.test.lsy.restapi250423.common.enu;

import com.test.lsy.restapi250423.common.interfaces.CodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum ResponseStatus implements CodeEnum {
    SUCCESS("SC", "성공"),
    FAIL("FL", "실패");

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }
}
