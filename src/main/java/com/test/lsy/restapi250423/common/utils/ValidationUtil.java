package com.test.lsy.restapi250423.common.utils;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ValidationUtil {

    public static void invokeErrors(String className, BindingResult br) throws ValidationException {

        if(br.hasErrors()) {
            log.error("Validation Error @Class={}", className);

            List<ObjectError> errList = br.getAllErrors();
            StringBuilder errorMsg = new StringBuilder();

            for (ObjectError err : errList) {
                if (err instanceof FieldError) {
                    FieldError fieldError = (FieldError) err;
                    String fieldName = fieldError.getField();
                    String errorMessage = fieldError.getDefaultMessage();

                    log.error("Detailed Error: {}", fieldError.toString());     // 상세 오류 출력
                    errorMsg.append(fieldName).append(": ").append(errorMessage).append("; ");
                }
            }

            List<String> errors = Arrays.asList(errorMsg.toString().split(";"));
            throw new ValidationException(errorMsg.toString());
        }
    }
}
