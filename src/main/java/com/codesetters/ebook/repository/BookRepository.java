package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.Book;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Book entity.
 */
@Repository
public class BookRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Book> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public BookRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Book.class);
        this.findAllStmt = session.prepare("SELECT * FROM book");
        this.truncateStmt = session.prepare("TRUNCATE book");
    }

    public List<Book> findAll() {
        List<Book> booksList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Book book = new Book();
                book.setId(row.getUUID("id"));
                book.setName(row.getString("name"));
                book.setPrice(row.getInt("price"));
                book.setAuthor(row.getString("author"));
                book.setReleased_date(row.get("released_date", LocalDate.class));
                book.setAdded_date(row.get("added_date", LocalDate.class));
                book.setRatings(row.getInt("ratings"));
                book.setDescription(row.getString("description"));
                return book;
            }
        ).forEach(booksList::add);
        return booksList;
    }

    public Book findOne(UUID id) {
        return mapper.get(id);
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(book);
        return book;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
