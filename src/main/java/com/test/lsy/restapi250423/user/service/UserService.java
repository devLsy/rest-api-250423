package com.test.lsy.restapi250423.user.service;

import com.test.lsy.restapi250423.common.exception.UserNotFoundException;
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
    public UserDto findUser(Long id) {
        UserEntity findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserDto.builder()
                .id(findUser.getId())
                .name(findUser.getName())
                .email(findUser.getEmail())
                .build();
    }

    /**
     * 사용자 목록 조회
     * @return
     */
    public List<UserDto> findUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .toList();
    }
}
