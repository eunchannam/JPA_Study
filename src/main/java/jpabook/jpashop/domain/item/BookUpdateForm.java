package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookUpdateForm {

    public String name;
    public int price;
    public int stockQuantity;
    public String author;
    public String isbn;

    public BookUpdateForm(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
