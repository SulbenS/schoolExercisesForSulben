package no.ntnu.crudrest.controller;

import java.util.Optional;
import no.ntnu.crudrest.model.Author;
import no.ntnu.crudrest.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST API controller for author collection.
 */
@RestController
@RequestMapping("authors")
public class AuthorController {
  private AuthorRepository authorRepository;
  public static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

  public AuthorController(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
    //initializeData();
  }

  /**
   * Initialize dummy author data for the collection.
   */
  private void initializeData() {
    addAuthor(new Author(-1, "Robert", "Martin", 1952));
    addAuthor(new Author(-1, "Erich", "Gamma", 1961));
    addAuthor(new Author(-1, "Joshua", "Bloch", 1961));
  }

  /**
   * Get All authors.
   * HTTP GET to /authors
   *
   * @return List of all authors currently stored in the collection
   */
  @GetMapping
  public Iterable<Author> getAll() {

    logger.info("Get all authors");

    return this.authorRepository.findAll();
  }


  /**
   * Get a specific author.
   *
   * @param id ID` of the author to be returned
   * @return author with the given ID or status 404
   */
  @GetMapping("/{id}")
  public ResponseEntity<Author> getOne(@PathVariable Integer id) {
    Optional<Author> AuthorOptional = authorRepository.findById(id); // Find author by ID

    if (AuthorOptional.isPresent()) {
      logger.info("Get one author with id" + id);

      return ResponseEntity.ok(AuthorOptional.get()); // Return author if found
    } else {
      logger.info("author with id " + id + " not found");

      return ResponseEntity.notFound().build(); // Return 404 if not found
    }
  }

  /**
   * HTTP POST endpoint for adding a new author.
   *
   * @param author Data of the author to add. ID will be ignored.
   * @return 201 Created on success and the new ID in the response body,
   *     400 Bad request if some data is missing or incorrect
   */
  @PostMapping()
  public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
    Author savedAuthor = authorRepository.save(author);
    logger.info("Added new author:" + savedAuthor);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
  }

  /**
   * Delete author author from the collection.
   *
   * @param id ID of the author to delete
   * @return 200 OK on success, 404 Not found on error
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable Integer id) {
    if (authorRepository.existsById(id)) {
      authorRepository.deleteById(id);
      logger.info("Deleted author with ID: " + id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  /**
   * Update author in the collection.
   *
   * @param id          ID of the author to update
   *                    (must match the ID in the JSON request body)
   * @param updatedAuthor New data for the author
   * @return 200 OK and the updated author on success, 404 Not found if the author was not found
   */
  @PutMapping("/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author updatedAuthor) {
    return authorRepository.findById(id)
        .map(existingAuthor -> {
          existingAuthor.setId(updatedAuthor.getId());
          existingAuthor.setFirstName(updatedAuthor.getFirstName());
          existingAuthor.setLastName(updatedAuthor.getLastName());
          existingAuthor.setBirthYear(updatedAuthor.getBirthYear());
          authorRepository.save(existingAuthor);
          logger.info("Updated author with ID: " + id);
          return ResponseEntity.ok(existingAuthor);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
