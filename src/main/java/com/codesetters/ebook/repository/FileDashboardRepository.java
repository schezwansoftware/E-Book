package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.FileDashboard;
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
 * Cassandra repository for the FileDashboard entity.
 */
@Repository
public class FileDashboardRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<FileDashboard> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public FileDashboardRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(FileDashboard.class);
        this.findAllStmt = session.prepare("SELECT * FROM fileDashboard");
        this.truncateStmt = session.prepare("TRUNCATE fileDashboard");
    }

    public List<FileDashboard> findAll() {
        List<FileDashboard> fileDashboardsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                FileDashboard fileDashboard = new FileDashboard();
                fileDashboard.setId(row.getUUID("id"));
                fileDashboard.setFilename(row.getString("filename"));
                fileDashboard.setFilesize(row.getLong("filesize"));
                fileDashboard.setCreatedby(row.getString("createdby"));
                return fileDashboard;
            }
        ).forEach(fileDashboardsList::add);
        return fileDashboardsList;
    }

    public FileDashboard findOne(UUID id) {
        return mapper.get(id);
    }

    public FileDashboard save(FileDashboard fileDashboard) {
        if (fileDashboard.getId() == null) {
            fileDashboard.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<FileDashboard>> violations = validator.validate(fileDashboard);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(fileDashboard);
        return fileDashboard;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
