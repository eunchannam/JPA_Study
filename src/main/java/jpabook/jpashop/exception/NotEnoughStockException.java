package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String s) {
        super(s);
    }

    public NotEnoughStockException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotEnoughStockException(Throwable throwable) {
        super(throwable);
    }

    protected NotEnoughStockException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
