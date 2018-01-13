package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.UserCertificates;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the UserCertificates entity.
 */
@Repository
public class UserCertificatesRepository {

    private final Session session;

    private Mapper<UserCertificates> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public UserCertificatesRepository(Session session) {
        this.session = session;
        this.mapper = new MappingManager(session).mapper(UserCertificates.class);
        this.findAllStmt = session.prepare("SELECT * FROM userCertificates");
        this.truncateStmt = session.prepare("TRUNCATE userCertificates");
    }

    public List<UserCertificates> findAll() {
        List<UserCertificates> userCertificatesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                UserCertificates userCertificates = new UserCertificates();
                userCertificates.setId(row.getUUID("id"));
                userCertificates.setUserlogin(row.getString("userlogin"));
                userCertificates.setCertificateurl(row.getString("certificateurl"));
                return userCertificates;
            }
        ).forEach(userCertificatesList::add);
        return userCertificatesList;
    }

    public UserCertificates findOne(UUID id) {
        return mapper.get(id);
    }

    public UserCertificates save(UserCertificates userCertificates) {
        if (userCertificates.getId() == null) {
            userCertificates.setId(UUID.randomUUID());
        }
        mapper.save(userCertificates);
        return userCertificates;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
