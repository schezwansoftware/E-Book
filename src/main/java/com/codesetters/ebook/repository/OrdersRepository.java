package com.codesetters.ebook.repository;

import com.codesetters.ebook.domain.Orders;
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
 * Cassandra repository for the Orders entity.
 */
@Repository
public class OrdersRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Orders> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public OrdersRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Orders.class);
        this.findAllStmt = session.prepare("SELECT * FROM orders");
        this.truncateStmt = session.prepare("TRUNCATE orders");
    }

    public List<Orders> findAll() {
        List<Orders> ordersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Orders orders = new Orders();
                orders.setId(row.getUUID("id"));
                orders.setOrderid(row.getString("orderid"));
                orders.setOrdername(row.getString("ordername"));
                orders.setOrdertype(row.getString("ordertype"));
                orders.setOrderby(row.getString("orderby"));
                orders.setOrderdate(row.get("orderdate", ZonedDateTime.class));
                orders.setOrderattendee(row.getString("orderattendee"));
                orders.setIsassigned(row.getBool("isassigned"));
                orders.setOrderaddress(row.getString("orderaddress"));
                orders.setDelivered(row.getBool("delivered"));
                orders.setDescription(row.getString("description"));
                return orders;
            }
        ).forEach(ordersList::add);
        return ordersList;
    }

    public Orders findOne(UUID id) {
        return mapper.get(id);
    }

    public Orders save(Orders orders) {
        if (orders.getId() == null) {
            orders.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Orders>> violations = validator.validate(orders);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(orders);
        return orders;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
