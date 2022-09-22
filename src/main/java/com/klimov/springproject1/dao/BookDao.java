package com.klimov.springproject1.dao;

import com.klimov.springproject1.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks(){
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> getBookById(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE book_id = ?",
                new BeanPropertyRowMapper<>(Book.class), id).stream().findAny();
    }

    public void saveBook(Book book){
        jdbcTemplate.update("INSERT INTO books(title, author, publish_year, client_id) VALUES (?,?,?,null)",
                book.getTitle(), book.getAuthor(), book.getPublish_year());
    }

    public void updateBook(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, publish_year = ?, client_id = ? WHERE book_id = ?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getPublish_year(), updatedBook.getClient_id(),
                id);
    }

    public void assignBook(int book_id, int client_id){
        jdbcTemplate.update("UPDATE books SET client_id = ? WHERE book_id = ?", client_id, book_id);
    }

    public void freeBook(int id){
        jdbcTemplate.update("UPDATE books SET client_id = NULL WHERE book_id = ?", id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM books WHERE book_id = ?", id);
    }

    public List<Book> getBooksPerClient(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE client_id = ?", new Object[] {id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
