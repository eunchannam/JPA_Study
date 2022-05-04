package jpabook.jpashop.domain.order.orderrepository.ordersimplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
}
