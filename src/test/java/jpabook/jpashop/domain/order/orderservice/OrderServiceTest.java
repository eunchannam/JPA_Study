package jpabook.jpashop.domain.order.orderservice;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.domain.order.orderrepository.OrderRepository;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;

    @Test
    void order() {
        //given
        Member member = createMember();
        Item item = createItem();
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 3);
        //then
        Order order = orderRepository.findOne(orderId);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(item.getStockQuantity()).isEqualTo(7);
    }

    @Test
    void 수량_초과_에러() {
        //given
        Member member = createMember();
        Item item = createItem();
        //when and then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), 11);
        });
    }

    @Test
    void cancel() {
        //given
        Member member = createMember();
        Item item = createItem();
        Long orderId = orderService.order(member.getId(), item.getId(), 3);
        //when
        orderService.cancel(orderId);
        //then
        em.flush();
        Item findItem = em.find(Item.class, item.getId());
        assertThat(findItem.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @Rollback(value = false)
    void test() {
        Member member = createMember();
        Item item = createItem();
        Long orderId = orderService.order(member.getId(), item.getId(), 3);

        em.flush();
        em.clear();

        Order order = em.find(Order.class, orderId);
        orderService.cancel(order.getId());
    }

    private Item createItem() {
        Item item = new Item();
        item.setPrice(10000);
        item.setStockQuantity(10);
        item.setName("itemA");
        em.persist(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("memberA");
        member.setAddress(new Address("수원시", "창훈로", "12-123"));
        em.persist(member);
        return member;
    }
}