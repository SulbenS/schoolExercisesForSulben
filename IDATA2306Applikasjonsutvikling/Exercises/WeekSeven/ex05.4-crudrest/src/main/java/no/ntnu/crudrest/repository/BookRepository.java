package no.ntnu.crudrest.repository;


import no.ntnu.crudrest.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {


}
