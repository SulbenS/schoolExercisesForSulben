package no.ntnu.crudrest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a resource: a book. We store Book objects in the application state.
 */
@Entity
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  private int year;
  private int numberOfPages;
  @ManyToMany (mappedBy = "books")
  @JsonIgnore
  private Set<Author> authors = new HashSet<>();

  public Book() {
  }

  public Set<Author> getAuthors() {
      return authors;
  }

  public void setAuthors(Set<Author> authors) {
      this.authors = authors;
  }

  /**
   * constructor for book
   */
  public Book(int id, String title, int year, int numberOfPages) {
    this.id = id;
    this.title = title;
    this.year = year;
    this.numberOfPages = numberOfPages;
  }

  /**
   * Check if this object is a valid book.
   *
   * @return True if the book is valid, false otherwise
   */
  @JsonIgnore
  public boolean isValid() {
    return title != null && !title.equals("");
  }

  public int getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public int getYear() {
    return this.year;
  }

  public int getNumberOfPages() {
    return this.numberOfPages;
  }

  public void setId(int id) {
      this.id = id;
  }

  public void setTitle(String title) {
      this.title = title;
  }

  public void setYear(int year) {
      this.year = year;
  }

  public void setNumberOfPages(int numberOfPages) {
      this.numberOfPages = numberOfPages;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Book) obj;
    return this.id == that.id &&
        Objects.equals(this.title, that.title) &&
        this.year == that.year &&
        this.numberOfPages == that.numberOfPages;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, year, numberOfPages);
  }

  @Override
  public String toString() {
    return "Book[" +
        "id=" + id + ", " +
        "title=" + title + ", " +
        "year=" + year + ", " +
        "numberOfPages=" + numberOfPages;
  }

}
