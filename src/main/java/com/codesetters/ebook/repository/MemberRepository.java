package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.Member;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Member entity.
 */
@Repository
public class MemberRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Member> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public MemberRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Member.class);
        this.findAllStmt = session.prepare("SELECT * FROM member");
        this.truncateStmt = session.prepare("TRUNCATE member");
    }

    public List<Member> findAll() {
        List<Member> membersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Member member = new Member();
                member.setId(row.getUUID("id"));
                member.setName(row.getString("name"));
                member.setMember_login(row.getString("member_login"));
                member.setMember_email(row.getString("member_email"));
                member.setJoin_date(row.get("join_date", ZonedDateTime.class));
                member.setMemeber_type(row.getString("memeber_type"));
                return member;
            }
        ).forEach(membersList::add);
        return membersList;
    }

    public Member findOne(UUID id) {
        return mapper.get(id);
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(member);
        return member;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
