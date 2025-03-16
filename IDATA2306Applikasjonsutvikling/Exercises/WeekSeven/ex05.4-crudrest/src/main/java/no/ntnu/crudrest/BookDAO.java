package no.ntnu.crudrest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
  private static final String URL = "jdbc:mysql://10.212.26.186:3306/library?useSSL=false&allowPublicKeyRetrieval=true"; // Change DB name
  private static final String USER = "chuck";  // Your DB user
  private static final String PASSWORD = "Nunchucks79"; // Your DB password

  /**
   * Fetch all books from the database.
   * @return List of books.
   */
  public List<Book> getAll() {
    List<Book> books = new ArrayList<>();
    String query = "SELECT * FROM books";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        Book book = new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getInt("year_of_publication"),
            rs.getInt("number_of_pages"),
            rs.getInt("author_id")
        );
        books.add(book);
        System.out.println("Fetched book: " + book.title());
      }
    } catch (SQLException e) {
      e.printStackTrace();  // Log error
    }
    return books;
  }
}

