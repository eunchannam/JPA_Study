package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.categoryitem.CategoryItem;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    //==비지니스 로직==
    public void addStock(int count) {
        this.stockQuantity += count;
    }

    public void removeStock(int count) {
        int total = stockQuantity - count;

        if (total < 0) {
            throw new NotEnoughStockException("수량이 부족합니다.");
        }
        this.stockQuantity = total;
    }
}
