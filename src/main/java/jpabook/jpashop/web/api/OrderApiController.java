package jpabook.jpashop.web.api;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderSearch;
import jpabook.jpashop.domain.order.orderrepository.OrderRepository;
import jpabook.jpashop.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getMember().getAddress();
            order.getDelivery().getId();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName());
        }

        return all;
    }
}
