package jpabook.jpashop.domain.order.orderservice;

import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.itemrepository.ItemRepository;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.memberrepository.MemberRepository;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderSearch;
import jpabook.jpashop.domain.order.orderrepository.OrderRepository;
import jpabook.jpashop.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public Long order(Long memberId, Long ItemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(ItemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, count);

        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    public void cancel(Long orderId) {
        Order findOrder = orderRepository.findOneToCancel(orderId);

        findOrder.cancel();
    }

    @Transactional(readOnly = true)
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
