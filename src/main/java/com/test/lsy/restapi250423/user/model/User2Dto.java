package com.test.lsy.restapi250423.user.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class User2Dto {
    private Long userId;
    private String username;
    private List<OrderDto> orders = new ArrayList<>();

    @Data
    public static class OrderDto {
        private Long orderId;
        private LocalDate orderDate;
        private List<ItemDto> items = new ArrayList<>();
    }

    @Data
    public static class ItemDto {
        private Long itemId;
        private String itemName;
        private Integer quantity;
    }
}

