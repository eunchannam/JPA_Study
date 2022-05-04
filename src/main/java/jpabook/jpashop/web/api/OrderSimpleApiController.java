package jpabook.jpashop.web.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderSearch;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.order.orderrepository.OrderRepository;
import jpabook.jpashop.domain.order.orderrepository.ordersimplequery.OrderSimpleQueryDto;
import jpabook.jpashop.domain.order.orderrepository.ordersimplequery.OrderSimpleQueryRepository;
import jpabook.jpashop.domain.order.orderservice.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        return orderService.findOrders(new OrderSearch());
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderService.findOrders(new OrderSearch());
        List<SimpleOrderDto> dtos = orders.stream().map(o -> {
                    SimpleOrderDto dto = new SimpleOrderDto(o.getId(), o.getMember().getName(), o.getOrderDate(),
                            o.getStatus(), o.getDelivery().getAddress());
                    return dto;
                })
                .collect(Collectors.toList());
        return new Result(dtos);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> dtos = orders.stream()
                .map(o -> {
                    SimpleOrderDto dto = new SimpleOrderDto(o.getId(), o.getMember().getName(), o.getOrderDate(),
                            o.getStatus(), o.getDelivery().getAddress());
                    return dto;
                }).collect(Collectors.toList());
        return new Result(dtos);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result orderV4() {
        List<OrderSimpleQueryDto> dtos = orderSimpleQueryRepository.findOrderDtos();
        return new Result(dtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


    @Data
    @AllArgsConstructor
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
    }

}
