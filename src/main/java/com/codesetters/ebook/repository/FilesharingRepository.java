package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.Filesharing;
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
 * Cassandra repository for the Filesharing entity.
 */
@Repository
public class FilesharingRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Filesharing> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FilesharingRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Filesharing.class);
        this.findAllStmt = session.prepare("SELECT * FROM filesharing");
        this.truncateStmt = session.prepare("TRUNCATE filesharing");
    }

    public List<Filesharing> findAll() {
        List<Filesharing> filesharingsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Filesharing filesharing = new Filesharing();
                filesharing.setId(row.getUUID("id"));
                filesharing.setFilename(row.getString("filename"));
                filesharing.setSharedby(row.getString("sharedby"));
                filesharing.setSharedto(row.getString("sharedto"));
                filesharing.setSharedon(row.get("sharedon", LocalDate.class));
                filesharing.setVerified(row.getBool("verified"));
                return filesharing;
            }
        ).forEach(filesharingsList::add);
        return filesharingsList;
    }

    public Filesharing findOne(UUID id) {
        return mapper.get(id);
    }

    public Filesharing save(Filesharing filesharing) {
        if (filesharing.getId() == null) {
            filesharing.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Filesharing>> violations = validator.validate(filesharing);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(filesharing);
        return filesharing;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
