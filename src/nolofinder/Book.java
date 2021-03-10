package nolofinder;

public class Book {
    private final String TITLE;
    private final String PRICE;

    public Book(String newTitle, String newPrice) {
        TITLE = newTitle;
        PRICE = newPrice;
    }

    String getTitle() {
        return TITLE;
    }

    String getPrice() {
        return PRICE;
    }
}
