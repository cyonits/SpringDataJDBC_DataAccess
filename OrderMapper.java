package com.codestates.order.mapper;

import com.codestates.coffee.entity.Coffee;
import com.codestates.coffee.service.CoffeeService;
import com.codestates.order.dto.OrderCoffeeResponseDto;
import com.codestates.order.entity.Order;
import com.codestates.order.dto.OrderPostDto;
import com.codestates.order.dto.OrderResponseDto;
import com.codestates.order.entity.OrderCoffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    default Order orderPostDtoToOrder(OrderPostDto orderPostDto) {
        Order order = new Order();

        order.setMemberId(orderPostDto.getMemberId());

        Set<OrderCoffee> orderCoffees = orderPostDto.getOrderCoffees()
                .stream()
                .map(orderCoffeeDto -> OrderCoffee.builder()
                        .coffeeId(orderCoffeeDto.getCoffeeId())
                        .quantity(orderCoffeeDto.getQuantity())
                        .build())
                .collect(Collectors.toSet());
        order.setOrderCoffees(orderCoffees);

        return order;
    }

    default OrderResponseDto orderToOrderResponseDto(CoffeeService coffeeService, Order order){
        Long memberId = order.getMemberId();

        List<OrderCoffeeResponseDto> orderCoffees =
                orderCoffeesToOrderCoffeeResponseDtos(coffeeService, order.getOrderCoffees());

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderCoffees(orderCoffees);
        orderResponseDto.setMemberId(memberId);
        orderResponseDto.setCreatedAt(order.getCreatedAt());
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());

        return orderResponseDto;
    }

    default List<OrderCoffeeResponseDto> orderCoffeesToOrderCoffeeResponseDtos(CoffeeService coffeeService,
                                                                               Set<OrderCoffee> orderCoffees){

        return orderCoffees.stream()
                .map(orderCoffee -> {Coffee coffee = coffeeService.findCoffee(orderCoffee.getCoffeeId());
        return new OrderCoffeeResponseDto(coffee.getCoffeeId(),
                    coffee.getKorName(),
                    coffee.getEngName(),
                    coffee.getPrice(),
                    orderCoffee.getQuantity());
                }).collect(Collectors.toList());
    }
}
