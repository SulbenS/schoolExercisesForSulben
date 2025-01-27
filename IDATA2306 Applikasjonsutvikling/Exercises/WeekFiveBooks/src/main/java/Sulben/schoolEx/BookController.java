package Sulben.schoolEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
  private List<Book> bookCollection = new ArrayList<Book>();

  /**
   * Constructor for the BookController class.
   */
  public BookController(){
    initializeData();
  }

  record Book(int id, String title, int year, int pages) {
  }

  /**
   * method to create a collection of books.
   */
  public void  initializeData(){
    bookCollection.add(new Book(1, "The Hobbit", 1937, 310));
    bookCollection.add(new Book(2, "The Lord of the Rings", 1954, 1178));
    bookCollection.add(new Book(3, "The Silmarillion", 1977, 365));
    bookCollection.add(new Book(4, "Unfinished Tales", 1980, 472));
    bookCollection.add(new Book(5, "The Children of Húrin", 2007, 313));
    bookCollection.add(new Book(6, "The Fall of Gondolin", 2018, 304));
  }

  /**
   * method to get the book collection.
   * @return a list of books
   */
  @GetMapping("/bookCollection")
  public List<Book> getBookCollection() {
    return bookCollection;
  }

  /**
   * method to get a book by id.
   * @param id the id of the book
   * @return a book
   */
  @GetMapping("/bookCollection/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable int id) {
    Optional<Book> book = bookCollection.stream().filter(b -> b.id() == id).findFirst();
    return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * method to add a book to the collection.
   */
  @PostMapping("/bookCollection")
  public ResponseEntity<String> addBook(@RequestBody Book newBook) {
    boolean bookExists = bookCollection.stream().anyMatch(book -> book.id() == newBook.id());
    if (bookExists) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Book already exists");
    }
    boolean added = bookCollection.add(newBook);
    if (added) {
      return ResponseEntity.status(HttpStatus.CREATED).body("Book added");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Could not add book");
    }
  }
}
