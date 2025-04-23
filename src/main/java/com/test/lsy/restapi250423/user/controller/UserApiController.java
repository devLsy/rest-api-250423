package com.test.lsy.restapi250423.user.controller;

import com.test.lsy.restapi250423.common.model.ApiResponse;
import com.test.lsy.restapi250423.user.model.UserDto;
import com.test.lsy.restapi250423.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    /**
     * 단일 사용자 조회
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long id) {
        ApiResponse<UserDto> response = userService.findUser(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {
        ApiResponse<List<UserDto>> response = userService.findUsers();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
