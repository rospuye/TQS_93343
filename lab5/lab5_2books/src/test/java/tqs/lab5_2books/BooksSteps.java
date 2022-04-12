package tqs.lab5_2books;

import java.util.Date;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooksSteps {

    private Library library;
    List<Book> list;

    @Given("a book with the title {string}, written by {string}, published in {int}-{int}-{int}, in the category {string}")
    public void a_book_with_the_title_written_by_published_in(String title, String author, int year, int month,
            int day, String category) {
        this.library = new Library();
        Date published = new Date(year, month, day);
        Book book = new Book(title, author, published,category);
        library.addBook(book);
    }

    @Given("another book with the title {string}, written by {string}, published in {int}-{int}-{int}, in the category {string}")
    public void another_book_with_the_title_written_by_published_in(String title, String author, int year, int month,
            int day, String category) {
        Date published = new Date(year, month, day);
        Book book = new Book(title, author, published,category);
        library.addBook(book);
    }

    @When("the customer searches for books published between {int} and {int}")
    public void the_customer_searches_for_books_published_between_and(int year1, int year2) {
        Date from = new Date(year1, 1, 1);
        Date to = new Date(year2, 12, 31);
        this.list = this.library.findByDate(from, to);
    }

    @When("the customer searches for books published by {string}")
    public void the_customer_searches_for_books_published_by(String author) {
        this.list = this.library.findByAuthor(author);
    }

    @When("the customer searches for books published in the category of {string}")
    public void the_customer_searches_for_books_publishedin_the_category_of(String category) {
        this.list = this.library.findByCategory(category);
    }

    @Then("{int} books should have been found")
    public void books_should_have_been_found(int expected) {
        assertEquals(expected, this.list.size());
    }

    @Then("Book {int} should have the title {string}")
    public void book_should_have_the_title(int index, String title) {
        assertEquals(title, this.list.get(index - 1).getTitle());
    }

}
