package no.ntnu.crudrest;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for book collection.
 */
@RestController
@RequestMapping("/books")
public class BookController {
  private BookRepository bookRepository;
  public static final Logger logger = LoggerFactory.getLogger(BookController.class);

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
    //initializeData();
  }

  /**
   * Initialize dummy book data for the collection.
   */
  private void initializeData() {
    addBook(new Book(-1, "Effective Java", 2008, 346));
    addBook(new Book(-1, "Clean Code", 2008, 464));
    addBook(new Book(-1, "Design Patterns", 1994, 395));
  }

  /**
   * Get All books.
   * HTTP GET to /books
   *
   * @return List of all books currently stored in the collection
   */
  @GetMapping
  public Iterable<Book> getAll() {

    logger.info("Get all books");

    return this.bookRepository.findAll();
  }


  /**
   * Get a specific book.
   *
   * @param id ID` of the book to be returned
   * @return Book with the given ID or status 404
   */
  @GetMapping("/{id}")
  public ResponseEntity<Book> getOne(@PathVariable Integer id) {
    Optional<Book> bookOptional = bookRepository.findById(id); // Find book by ID

    if (bookOptional.isPresent()) {
      logger.info("Get one book with id" + id);

      return ResponseEntity.ok(bookOptional.get()); // Return book if found
    } else {
      logger.info("Book with id " + id + " not found");

      return ResponseEntity.notFound().build(); // Return 404 if not found
    }
  }

  /**
   * HTTP POST endpoint for adding a new book.
   *
   * @param book Data of the book to add. ID will be ignored.
   * @return 201 Created on success and the new ID in the response body,
   *     400 Bad request if some data is missing or incorrect
   */
  @PostMapping()
  public ResponseEntity<Book> addBook(@RequestBody Book book) {
    Book savedBook = bookRepository.save(book);
    logger.info("Added new book:" + savedBook);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
  }

  /**
   * Delete a book from the collection.
   *
   * @param id ID of the book to delete
   * @return 200 OK on success, 404 Not found on error
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
    if (bookRepository.existsById(id)) {
      bookRepository.deleteById(id);
      logger.info("Deleted book with ID: " + id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Update a book in the collection.
   *
   * @param id          ID of the book to update
   *                    (must match the ID in the JSON request body)
   * @param updatedBook New data for the book
   * @return 200 OK and the updated book on success, 404 Not found if the book was not found
   */
  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book updatedBook) {
    return bookRepository.findById(id)
        .map(existingBook -> {
          existingBook.setId(updatedBook.getId());
          existingBook.setTitle(updatedBook.getTitle());
          existingBook.setYear(updatedBook.getYear());
          existingBook.setNumberOfPages(updatedBook.getNumberOfPages());
          bookRepository.save(existingBook);
          logger.info("Updated book with ID: " + id);
          return ResponseEntity.ok(existingBook);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
