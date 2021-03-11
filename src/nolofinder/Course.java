package nolofinder;

import java.util.ArrayList;

public class Course {
    //included in bookstore CSV
    private ArrayList<Book> books = new ArrayList<>();
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

    public void addBook(String title, String price) {
        books.add(new Book(title, price));
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
