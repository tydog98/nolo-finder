package nolofinder;

public class Book {
    private String title;
    private int price;

    void setTitle(String newTitle) {
        title = newTitle;
    }

    void setPrice(int newPrice) {
        price = newPrice;
    }

    String getTitle() {
        return title;
    }

    int getPrice() {
        return price;
    }
}
