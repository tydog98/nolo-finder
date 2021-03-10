package nolofinder;

public class Book {
    private String title;
    private String price;

    public Book(String newTitle, String newPrice) {
        title = newTitle;
        price = newPrice;
    }

    void setTitle(String newTitle) {
        title = newTitle;
    }

    void setPrice(String newPrice) {
        price = newPrice;
    }

    String getTitle() {
        return title;
    }

    String getPrice() {
        return price;
    }
}
