package nolofinder;

public class Book implements Comparable<Book> {
    private final String title;
    private final String price;
    private final String requirement; //says if the books is required or choice
    private final String duration;

    public Book(String newTitle, String newPrice, String newRequirement, String newDuration) {
        title = newTitle;
        price = newPrice;
        requirement = newRequirement;
        duration = newDuration;
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

    String getDuration() {
        return duration;
    }

    @Override
    public int compareTo(Book otherBook) {

        if (Double.parseDouble(this.price) > Double.parseDouble(otherBook.price)) {
            return 1;
        } else if (Double.parseDouble(this.price) < Double.parseDouble(otherBook.price)) {
            return -1;
        }

        return 0;
        //return Integer.compare(Integer.parseInt(this.getPrice()), Integer.parseInt(otherBook.getPrice()));
    }
}
