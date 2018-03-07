package com.codesetters.ebook.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Orders entity.
 */
public class OrdersDTO implements Serializable {

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public ZonedDateTime getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(ZonedDateTime orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderattendee() {
        return orderattendee;
    }

    public void setOrderattendee(String orderattendee) {
        this.orderattendee = orderattendee;
    }

    public Boolean isIsassigned() {
        return isassigned;
    }

    public void setIsassigned(Boolean isassigned) {
        this.isassigned = isassigned;
    }

    public String getOrderaddress() {
        return orderaddress;
    }

    public void setOrderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
    }

    public Boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if(ordersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
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
