package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A Member.
 */
@Table(name = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private String name;

    private String member_login;

    private String member_email;

    private ZonedDateTime join_date;

    private String memeber_type;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Member name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember_login() {
        return member_login;
    }

    public Member member_login(String member_login) {
        this.member_login = member_login;
        return this;
    }

    public void setMember_login(String member_login) {
        this.member_login = member_login;
    }

    public String getMember_email() {
        return member_email;
    }

    public Member member_email(String member_email) {
        this.member_email = member_email;
        return this;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public ZonedDateTime getJoin_date() {
        return join_date;
    }

    public Member join_date(ZonedDateTime join_date) {
        this.join_date = join_date;
        return this;
    }

    public void setJoin_date(ZonedDateTime join_date) {
        this.join_date = join_date;
    }

    public String getMemeber_type() {
        return memeber_type;
    }

    public Member memeber_type(String memeber_type) {
        this.memeber_type = memeber_type;
        return this;
    }

    public void setMemeber_type(String memeber_type) {
        this.memeber_type = memeber_type;
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
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", member_login='" + getMember_login() + "'" +
            ", member_email='" + getMember_email() + "'" +
            ", join_date='" + getJoin_date() + "'" +
            ", memeber_type='" + getMemeber_type() + "'" +
            "}";
    }
}
