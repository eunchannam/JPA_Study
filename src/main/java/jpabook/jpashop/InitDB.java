package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            createMember("memberA", "서울", "JPA BOOK");
        }

        public void dbInit2() {
            createMember("memberB", "부산", "SPRING BOOK");
        }

        private void createMember(String name, String city, String book) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, "1", "1111"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName(book + "1");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName(book + "2");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }


}

