package nolofinder;

public class Book {
    private final String TITLE;
    private final String PRICE;
    private final String REQUIREMENT; //says if the books is required or choice

    public Book(String newTitle, String newPrice, String newRequirement) {
        TITLE = newTitle;
        PRICE = newPrice;
        REQUIREMENT = newRequirement;
    }

    String getTITLE() {
        return TITLE;
    }

    String getPRICE() {
        return PRICE;
    }

    String getREQUIREMENT() {
        return REQUIREMENT;
    }
}
