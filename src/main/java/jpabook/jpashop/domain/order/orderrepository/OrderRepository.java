package jpabook.jpashop.domain.order.orderrepository;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderSearch;
import jpabook.jpashop.domain.order.orderrepository.ordersimplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public Order findOneToCancel(Long id) {
        String query = "select distinct o from Order o join fetch o.orderItems oi join fetch oi.item where o.id = :orderId";

        return em.createQuery(query, Order.class)
                .setParameter("orderId", id)
                .getSingleResult();
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        if (orderSearch.getOrderStatus() != null && StringUtils.hasText(orderSearch.getMemberName())) {
            String jpql = "select distinct o from Order o join fetch o.member join fetch o.orderItems oi join fetch oi.item" +
                    " where o.status = :status " +
                    "and m.name like :name";
            return em.createQuery(jpql, Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .setParameter("name", orderSearch.getMemberName())
                    .getResultList();
        } else if (orderSearch.getOrderStatus() != null) {
            String jpql = "select distinct o from Order o join fetch o.member join fetch o.orderItems oi join fetch oi.item" +
                    " where o.status = :status ";
            return em.createQuery(jpql, Order.class)
                    .setParameter("status", orderSearch.getOrderStatus())
                    .getResultList();
        } else if (StringUtils.hasText(orderSearch.getMemberName())) {
            String jpql = "select distinct o from Order o join fetch o.member join fetch o.orderItems oi join fetch oi.item" +
                    " where m.name like :name";
            return em.createQuery(jpql, Order.class)
                    .setParameter("name", orderSearch.getMemberName())
                    .getResultList();
        } else {
            String jpql = "select distinct o from Order o join fetch o.member join fetch o.orderItems oi join fetch oi.item";
            return em.createQuery(jpql, Order.class)
                    .getResultList();
        }
    }

    public List<Order> findAllWithMemberDelivery() {
        String query = "select o from Order o " +
                "join fetch o.member " +
                "join fetch o.delivery";
        return em.createQuery(query, Order.class)
                .getResultList();
    }
}
