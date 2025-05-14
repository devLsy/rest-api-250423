package com.test.lsy.restapi250423.user3.service;

import com.test.lsy.restapi250423.common.constant.MessageConstants;
import com.test.lsy.restapi250423.common.enu.ResponseStatus;
import com.test.lsy.restapi250423.common.model.ApiResponse;
import com.test.lsy.restapi250423.common.utils.ValidationUtil;
import com.test.lsy.restapi250423.user.mapper.UserMapper;
import com.test.lsy.restapi250423.user.model.FlatUserOrderItemDto;
import com.test.lsy.restapi250423.user.model.User2Dto;
import com.test.lsy.restapi250423.user.model.UserDto;
import com.test.lsy.restapi250423.user3.model.User3Dto;
import com.test.lsy.restapi250423.user3.model.User3Entity;
import com.test.lsy.restapi250423.user3.repository.User3Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class User3Service {

    private final User3Repository user3Repository;
    private final UserMapper userMapper;

    /**
     * 단일 사용자 조회
     * @param id
     * @return
     */
    public ApiResponse<UserDto> findUser(Long id) {
        return user3Repository.findById(id)
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
        List<User3Entity> users = user3Repository.findAll();

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

    public User2Dto getUserHierarchy() {
        List<FlatUserOrderItemDto> flatList = userMapper.selectUserOrderItems();

        User2Dto user = null;

        for (FlatUserOrderItemDto row : flatList) {
            if (user == null) {
                user = new User2Dto();
                user.setUserId(row.getUserId());
                user.setUsername(row.getUsername());
            }

            User2Dto.OrderDto order = findOrder(user.getOrders(), row.getOrderId());
            if (order == null) {
                order = new User2Dto.OrderDto();
                order.setOrderId(row.getOrderId());
                order.setOrderDate(row.getOrderDate());
                user.getOrders().add(order);
            }

            if (row.getItemId() != null) {
                User2Dto.ItemDto item = new User2Dto.ItemDto();
                item.setItemId(row.getItemId());
                item.setItemName(row.getItemName());
                item.setQuantity(row.getQuantity());
                order.getItems().add(item);
            }
        }
        return user;
    }

    private User2Dto.OrderDto findOrder(List<User2Dto.OrderDto> orders, Long orderId) {
        for (User2Dto.OrderDto order : orders) {
            if (order.getOrderId().equals(orderId)) return order;
        }
        return null;
    }

    public ApiResponse<Long> addUser(User3Dto userDto3, BindingResult br) {

        if(br.hasErrors()) {
            ValidationUtil.invokeErrors(this.getClass().getName(), br);
        }

        try {
            User3Entity user3Entity = new User3Entity(userDto3.getName(), userDto3.getEmail());

            Long savedId = user3Repository.save(user3Entity).getId();
//            user3Repository.flush(); // flush를 강제로 호출(테스트)

            if (savedId == null || savedId <= 0) {
                throw new IllegalStateException("저장된 ID가 유효하지 않습니다.");
            }

            // 일부러 예외 발생시켜 롤백 테스트
//            throw new RuntimeException("강제 롤백 테스트");

            return ApiResponse.success(savedId, null);

        } catch(Exception e) {
            log.error("사용자 저장 중 예외 발생: {}", e.getMessage(), e);
            // 예외 재던지기 (롤백 유도)
            throw new RuntimeException("사용자 저장 실패", e);
        }
    }
}
