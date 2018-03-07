package com.codesetters.ebook.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Member entity.
 */
public class MemberDTO implements Serializable {

    private UUID id;

    private String name;

    private String member_login;

    private String member_email;

    private ZonedDateTime join_date;

    private String memeber_type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMember_login() {
        return member_login;
    }

    public void setMember_login(String member_login) {
        this.member_login = member_login;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public ZonedDateTime getJoin_date() {
        return join_date;
    }

    public void setJoin_date(ZonedDateTime join_date) {
        this.join_date = join_date;
    }

    public String getMemeber_type() {
        return memeber_type;
    }

    public void setMemeber_type(String memeber_type) {
        this.memeber_type = memeber_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;
        if(memberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", member_login='" + getMember_login() + "'" +
            ", member_email='" + getMember_email() + "'" +
            ", join_date='" + getJoin_date() + "'" +
            ", memeber_type='" + getMemeber_type() + "'" +
            "}";
    }
}
