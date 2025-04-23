package com.test.lsy.restapi250423.user.service;

import com.test.lsy.restapi250423.common.constant.MessageConstants;
import com.test.lsy.restapi250423.common.enu.ResponseStatus;
import com.test.lsy.restapi250423.common.model.ApiResponse;
import com.test.lsy.restapi250423.user.model.UserDto;
import com.test.lsy.restapi250423.user.model.UserEntity;
import com.test.lsy.restapi250423.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * 단일 사용자 조회
     * @param id
     * @return
     */
    public ApiResponse<UserDto> findUser(Long id) {
        return userRepository.findById(id)
                .map(user -> ApiResponse.success(
                        UserDto.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .build(),
                        null
                ))
                .orElseGet(() -> ApiResponse.fail(MessageConstants.USER_NOT_FOUND, ResponseStatus.BAR_REQUEST.getHttpStatus()));
    }

    /**
     * 사용자 목록 조회
     * @return
     */
    public ApiResponse<List<UserDto>> findUsers() {
        List<UserEntity> users = userRepository.findAll();

        if(users.isEmpty()) {
            return ApiResponse.noData();
        }

        Long totalCount = (long)users.size();

        // ApiResponse로 감싸서 반환
        return ApiResponse.success(
                users.stream()
                        .map(user -> UserDto.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .build())
                        .toList(),
                totalCount
        );
    }
}
