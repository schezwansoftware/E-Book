package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A Orders.
 */
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private String orderid;

    private String ordername;

    private String ordertype;

    private String orderby;

    private ZonedDateTime orderdate;

    private String orderattendee;

    private Boolean isassigned;

    private String orderaddress;

    private Boolean delivered;

    private String description;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public Orders orderid(String orderid) {
        this.orderid = orderid;
        return this;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdername() {
        return ordername;
    }

    public Orders ordername(String ordername) {
        this.ordername = ordername;
        return this;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public Orders ordertype(String ordertype) {
        this.ordertype = ordertype;
        return this;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrderby() {
        return orderby;
    }

    public Orders orderby(String orderby) {
        this.orderby = orderby;
        return this;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public ZonedDateTime getOrderdate() {
        return orderdate;
    }

    public Orders orderdate(ZonedDateTime orderdate) {
        this.orderdate = orderdate;
        return this;
    }

    public void setOrderdate(ZonedDateTime orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderattendee() {
        return orderattendee;
    }

    public Orders orderattendee(String orderattendee) {
        this.orderattendee = orderattendee;
        return this;
    }

    public void setOrderattendee(String orderattendee) {
        this.orderattendee = orderattendee;
    }

    public Boolean isIsassigned() {
        return isassigned;
    }

    public Orders isassigned(Boolean isassigned) {
        this.isassigned = isassigned;
        return this;
    }

    public void setIsassigned(Boolean isassigned) {
        this.isassigned = isassigned;
    }

    public String getOrderaddress() {
        return orderaddress;
    }

    public Orders orderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
        return this;
    }

    public void setOrderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
    }

    public Boolean isDelivered() {
        return delivered;
    }

    public Orders delivered(Boolean delivered) {
        this.delivered = delivered;
        return this;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String getDescription() {
        return description;
    }

    public Orders description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if (orders.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orders.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", orderid='" + getOrderid() + "'" +
            ", ordername='" + getOrdername() + "'" +
            ", ordertype='" + getOrdertype() + "'" +
            ", orderby='" + getOrderby() + "'" +
            ", orderdate='" + getOrderdate() + "'" +
            ", orderattendee='" + getOrderattendee() + "'" +
            ", isassigned='" + isIsassigned() + "'" +
            ", orderaddress='" + getOrderaddress() + "'" +
            ", delivered='" + isDelivered() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
