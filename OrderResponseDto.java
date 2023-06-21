package com.codestates.order.dto;

import com.codestates.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
public class OrderResponseDto {
    private long orderId;
    private long memberId;
    //private long coffeeId;

    private Order.OrderStatus orderStatus;
    private List<OrderCoffeeResponseDto> orderCoffees;
    private LocalDateTime createdAt;
}
