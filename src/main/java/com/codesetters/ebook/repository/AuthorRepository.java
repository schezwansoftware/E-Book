package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.Author;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Author entity.
 */
@Repository
public class AuthorRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Author> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public AuthorRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Author.class);
        this.findAllStmt = session.prepare("SELECT * FROM author");
        this.truncateStmt = session.prepare("TRUNCATE author");
    }

    public List<Author> findAll() {
        List<Author> authorsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Author author = new Author();
                author.setId(row.getUUID("id"));
                author.setName(row.getString("name"));
                author.setBiography(row.getString("biography"));
                author.setGenre(row.getString("genre"));
                author.setRatings_total(row.getDouble("ratings_total"));
                author.setRatings_avg(row.getDouble("ratings_avg"));
                author.setRatings(row.getInt("ratings"));
                return author;
            }
        ).forEach(authorsList::add);
        return authorsList;
    }

    public Author findOne(UUID id) {
        return mapper.get(id);
    }

    public Author save(Author author) {
        if (author.getId() == null) {
            author.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Author>> violations = validator.validate(author);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(author);
        return author;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
