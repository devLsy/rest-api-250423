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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ApiResponse<Long> saveUser(UserDto user) {

        try {
            UserEntity userEntity = new UserEntity(user.getName(), user.getEmail());

            Long savedId = userRepository.save(userEntity).getId();

            if (savedId == null || savedId <= 0) {
                throw new IllegalStateException("저장된 ID가 유효하지 않습니다.");
            }

            return ApiResponse.success(savedId, null);

        } catch(Exception e) {
            log.error("사용자 저장 중 예외 발생: {}", e.getMessage(), e);
            // 예외 재던지기 (롤백 유도)
            throw new RuntimeException("사용자 저장 실패", e);
        }
    }
}
