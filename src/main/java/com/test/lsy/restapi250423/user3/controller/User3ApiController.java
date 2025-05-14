package com.test.lsy.restapi250423.user3.controller;

import com.test.lsy.restapi250423.common.model.ApiResponse;
import com.test.lsy.restapi250423.user.model.User2Dto;
import com.test.lsy.restapi250423.user.model.UserDto;
import com.test.lsy.restapi250423.user3.model.User3Dto;
import com.test.lsy.restapi250423.user3.service.User3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user2")
public class User3ApiController {

    private final User3Service user3Service;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveUser(@RequestBody @Valid User3Dto userDto3, BindingResult br) {
        ApiResponse<Long> response = user3Service.addUser(userDto3, br);
        return ResponseEntity
                .status(response.getMeta().getHttpStatus())
                .body(response);
    }

    /**
     * 단일 사용자 조회
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long id) {
        ApiResponse<UserDto> response = user3Service.findUser(id);
        return ResponseEntity
                .status(response.getMeta().getHttpStatus())
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {
        ApiResponse<List<UserDto>> response = user3Service.findUsers();
        return ResponseEntity
                .status(response.getMeta().getHttpStatus())
                .body(response);
    }

    @GetMapping("/1")
    public ResponseEntity<User2Dto> getUserInfo() {
        return ResponseEntity.ok(user3Service.getUserHierarchy());
    }
}
