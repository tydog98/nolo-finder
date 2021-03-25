package nolofinder;

public class Book implements Comparable<Book> {
    private final String price;
    private final String requirement; //says if the books is required or choice

    public Book(String newPrice, String newRequirement) {
        price = newPrice;
        requirement = newRequirement;
    }


    String getPrice() {
        return price;
    }

    String getRequirement() {
        return requirement;
    }

    @Override
    public int compareTo(Book otherBook) {

        if (Double.parseDouble(this.price) > Double.parseDouble(otherBook.price)) {
            return 1;
        } else if (Double.parseDouble(this.price) < Double.parseDouble(otherBook.price)) {
            return -1;
        }

        return 0;
    }
}
