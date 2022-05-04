package jpabook.jpashop.domain.category;

import jpabook.jpashop.domain.categoryitem.CategoryItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category category) {
        children.add(category);
        category.setParent(this);
    }
}
