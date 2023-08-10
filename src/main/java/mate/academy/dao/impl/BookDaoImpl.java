package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
  @Override
  public Book create(Book book) {
    String sqlQuery = "INSERT INTO books (title, price) VALUES(?, ?);";
    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){
      statement.setString(1, book.getTitle());
      statement.setObject(2, book.getPrice());
      int updatedRows = statement.executeUpdate();
      if (updatedRows < 1) {
        throw new SQLException("A row was not inserted.");
      }
      ResultSet resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        book.setId(resultSet.getObject(1, Long.class));
      }
    } catch (SQLException e) {
      throw new DataProcessingException("Can not create a new book: " + book, e);
    }
    return book;
  }

  @Override
  public Optional<Book> findById(Long id) {
    Book book = null;
    String sqlQuery = "SELECT * FROM books WHERE id = ?;";
    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(sqlQuery)){
      statement.setObject(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        book = getBook(resultSet);
      }
    } catch (SQLException e) {
      throw new DataProcessingException("Can not obtain a book by id: " + id, e);
    }
    return Optional.ofNullable(book);
  }

  public List<Book> findAll() {
    List<Book> books = new ArrayList<>();
    String sqlQuery = "SELECT * FROM books;";
    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(sqlQuery)){
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Book book = getBook(resultSet);
        books.add(book);
      }
    } catch (SQLException e) {
      throw new DataProcessingException("Can not obtain all books.", e);
    }
    return books;
  }

  @Override
  public Book update(Book book) {
    String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(sqlQuery)){
      statement.setString(1, book.getTitle());
      statement.setObject(2, book.getPrice());
      statement.setObject(3, book.getId());
      int updatedRows = statement.executeUpdate();
      if (updatedRows < 1) {
        throw new SQLException("A row was not updated.");
      }
    } catch (SQLException e) {
      throw new DataProcessingException("Can not create a new book: " + book, e);
    }
    return book;
  }

  @Override
  public boolean deleteById(Long id) {
    String sqlQuery = "DELETE FROM books WHERE id = ?;";
    try (Connection connection = ConnectionUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(sqlQuery)){
      statement.setObject(1, id);
      int updatedRows = statement.executeUpdate();
      return updatedRows > 0;
    } catch (SQLException e) {
      throw new DataProcessingException("Can not create a new book: " + id, e);
    }
  }

  private Book getBook(ResultSet resultSet) throws SQLException {
    Book book = new Book();
    book.setId(resultSet.getObject("id", Long.class));
    book.setTitle(resultSet.getString("title"));
    book.setPrice(resultSet.getObject("price", BigDecimal.class));
    return book;
  }
}
