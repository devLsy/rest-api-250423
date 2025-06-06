package com.test.lsy.restapi250423.common.handler;

import com.test.lsy.restapi250423.common.model.ApiResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(ValidationException ex) {
        String errorMessage = ex.getMessage();
        // 서버 측에서 오류 메시지 로깅 (상세한 정보)
        log.error("Validation Error: {}", errorMessage);
        // 오류 메시지를 ";"로 분리하여 리스트로 변환
        List<String> errors = Arrays.asList(errorMessage.split(";"));

        ApiResponse<Object> response = ApiResponse.fail("유효성 검증이 실패했습니다.", HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 다른 예외도 처리 가능
}
