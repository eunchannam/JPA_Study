package jpabook.jpashop.domain.order.orderrepository.ordersimplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        String query = "select new jpabook.jpashop.domain.order.orderrepository." +
                "ordersimplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                "join o.member m " +
                "join o.delivery d";
        return em.createQuery(query, OrderSimpleQueryDto.class)
                .getResultList();
    }

}
