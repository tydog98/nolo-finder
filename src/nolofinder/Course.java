package nolofinder;

import java.util.ArrayList;
import java.util.Collections;

public class Course {
    //included in bookstore CSV
    private final ArrayList<Book> books = new ArrayList<>();
    private String courseSubject;
    private String courseNumber;
    private String courseSection;
    private String instructorName;

    //included in roomlist CSV
    private String crn;
    private String courseName;
    private String instructorEmail;

    //gotten by calculations
    private double totalCost;
    private boolean nolo;
    private boolean manualReview;

    public void addBook(String price, String requirement, String title) {
        books.add(new Book(price, requirement, title));
    }

    public Course() {
        courseName = "N/A";
        courseNumber = "N/A";
        crn = "N/A";
        totalCost = 0;
        courseSection = "N/A";
        courseSubject = "N/A";
        instructorName = "N/A";
        instructorEmail = "N/A";
    }

    public void calculateNolo(double noloThreshhold) {
        //if a book list is empty, it means all books have been filtered out because they are not purchasable
        if (books.isEmpty()) {
            nolo = false;
            return;
        }

        //sorts all "choice" books to find the lowest value one
        ArrayList<Book> choiceBooks = new ArrayList<>();
        double requiredTotal = 0;

        //removes duplicate titles
        int previousBook;
        for (int currentBook = 1; currentBook < books.size(); currentBook++) {
            previousBook = currentBook - 1;

            //compares the two books titles
            if (books.get(currentBook).getTitle().equals(books.get(previousBook).getTitle())) {

                //if the current book is more expensive, remove the previous one, else do the reverse
                if (books.get(currentBook).getPrice() >= books.get(previousBook).getPrice()) {
                    books.remove(previousBook);
                } else {
                    books.remove(currentBook);
                }
            }
        }

        for (Book book : books) {
            //if the book is a choice, add it to be sorted later
            if (book.getRequirement().equals("CHC")) {
                choiceBooks.add(book);

                //if the book is required, add the price to the total of all required books
            } else if (book.getRequirement().equals("REQ")) {
                requiredTotal += book.getPrice();
            }
        }

        Collections.sort(choiceBooks);

        double grandTotal;

        //add the total of all required books to the cheapest choice book if there is one
        if (!choiceBooks.isEmpty()) {
            grandTotal = choiceBooks.get(0).getPrice() + requiredTotal;
        } else {
            grandTotal = requiredTotal;
        }

        totalCost = grandTotal;

        if (grandTotal > noloThreshhold) {
            nolo = false;
        } else if (grandTotal <= noloThreshhold) {
            nolo = true;
        }

    }

    public void setCourseSubject(String newCourseSubject) {
        courseSubject = newCourseSubject;
    }

    public void setCourseNumber(String newCourseNumber) {
        courseNumber = newCourseNumber;
    }

    public void setCourseSection(String newCourseSection) {
        courseSection = newCourseSection;
    }

    public void setTotalCost(int newTotalCost) {
        totalCost = newTotalCost;
    }

    public void setInstructorName(String newInstructorName) {
        instructorName = newInstructorName;
    }

    public void setCrn(String newCrn) {
        crn = newCrn;
    }

    public void setCourseName(String newCourseName) {
        courseName = newCourseName;
    }

    public void setInstructorEmail(String newInstructorEmail) {
        instructorEmail = newInstructorEmail;
    }

    public void setManualReview(boolean newValue) {
        manualReview = newValue;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public String getTotalCost() {
        return String.valueOf(totalCost);
    }

    public String isNolo() {
        if (nolo) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public String getManualReview() {
        if (manualReview) {
            return "Error was found and course should be reviewed manually";
        } else {
            return "N/A";
        }
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getCrn() {
        return crn;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

}
