package tqs.lab5_2books;

import java.util.Date;

public class Book {

    private String title;
	private String author;
	private Date published;
    private String category;
    
    public Book(String title, String author, Date published, String category) {
        this.title = title;
        this.author = author;
        this.published = published;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
