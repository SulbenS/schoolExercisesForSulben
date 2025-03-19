package no.ntnu.crudrest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a resource: a book. We store Book objects in the application state.
 */
@Entity
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String lastName;
  private int birthYear;
  @ManyToMany
  @JoinTable(
      name = "author_book",
      joinColumns = @JoinColumn(name = "author_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id")
  )
  private Set<Book> books = new HashSet<>();

  public Author() {
  }

  /**
   * constructor for book
   */
  public Author(int id, String firstName, String lastName, int birthYear) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthYear = birthYear;
  }

  public Set<Book> getBooks() {
      return books;
  }

  public void setBooks(Set<Book> books) {
      this.books = books;
  }

  /**
   * Check if this object is a valid book.
   *
   * @return True if the book is valid, false otherwise
   */
  @JsonIgnore
  public boolean isValid() {
    return firstName != null && !firstName.equals("");
  }

  public int getId() {
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public int getBirthYear() {
    return this.birthYear;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setFirstName(String title) {
    this.firstName = title;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName == null ? "" : lastName;
  }

  public void setBirthYear(int numberOfPages) {
    this.birthYear = numberOfPages;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Author) obj;
    return this.id == that.id &&
        Objects.equals(this.firstName, that.firstName) &&
        Objects.equals(this.lastName, that.lastName)&&
        this.birthYear == that.birthYear;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, birthYear);
  }

  @Override
  public String toString() {
    return "Author[" +
        "id=" + id + ", " +
        "title=" + firstName + ", " +
        "year=" + lastName + ", " +
        "numberOfPages=" + birthYear;
  }

}
