package nolofinder;

public class Book implements Comparable<Book> {
    private final double price;
    private final String requirement; //says if the books is required or choice
    private final String title;

    public Book(String newPrice, String newRequirement, String newTitle) {
        price = Double.parseDouble(newPrice);
        requirement = newRequirement;
        title = newTitle;
    }


    Double getPrice() {
        return price;
    }

    String getRequirement() {
        return requirement;
    }

    String getTitle() {
        return title;
    }

    @Override
    public int compareTo(Book otherBook) {

        if (this.price > otherBook.price) {
            return 1;
        } else if (this.price < otherBook.price) {
            return -1;
        }

        return 0;
    }
}
