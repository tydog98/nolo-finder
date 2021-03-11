package nolofinder;

public class Book {
    private final String title;
    private final String price;
    private final String requirement; //says if the books is required or choice

    public Book(String newTitle, String newPrice, String newRequirement) {
        title = newTitle;
        price = newPrice;
        requirement = newRequirement;
    }

    String getTitle() {
        return title;
    }

    String getPrice() {
        return price;
    }

    String getRequirement() {
        return requirement;
    }
}
