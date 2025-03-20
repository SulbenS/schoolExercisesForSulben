package no.ntnu.crudrest.repository;

import no.ntnu.crudrest.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
