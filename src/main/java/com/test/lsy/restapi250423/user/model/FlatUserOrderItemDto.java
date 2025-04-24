package com.test.lsy.restapi250423.user.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlatUserOrderItemDto {
    private Long userId;
    private String username;

    private Long orderId;
    private LocalDate orderDate;

    private Long itemId;
    private String itemName;
    private Integer quantity;
}
