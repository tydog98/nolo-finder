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
    private int totalCost;
    private boolean nolo;

    //for testing purposes
    public void printBookList() {
        for (Book book : books) {
            System.out.println(book.getTitle() + " " + book.getPrice());
        }

    }

    public void addBook(String title, String price, String requirement) {
        books.add(new Book(title, price, requirement));
    }

    public Course() {
        courseName = "N/A";
        courseNumber = "N/A";
        crn = "N/A";
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
        int requiredTotal = 0;

        for (Book book : books) {
            //if the book is a choice, add it to be sorted later
            if (book.getRequirement().equals("CHC")) {
                choiceBooks.add(book);

                //if the book is required, add the price to the total of all required books
            } else if (book.getRequirement().equals("REQ")) {
                requiredTotal += Double.parseDouble(book.getPrice());
            }
        }

        Collections.sort(choiceBooks);

        double grandTotal;

        //add the total of all required books to the cheapest choice book if there is one
        if (!choiceBooks.isEmpty()) {
            grandTotal = Double.parseDouble(choiceBooks.get(0).getPrice()) + requiredTotal;
        } else {
            grandTotal = requiredTotal;
        }

        if (grandTotal > noloThreshhold) {
            nolo = false;
        } else if (grandTotal < noloThreshhold) {
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

    public void setNolo(boolean newNolo) {
        nolo = newNolo;
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

    public String getCourseSubject() {
        return courseSubject;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public boolean isNolo() {
        return nolo;
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
