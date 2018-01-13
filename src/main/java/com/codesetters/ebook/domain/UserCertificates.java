package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A UserCertificates.
 */

@Table(name = "userCertificates")
public class UserCertificates implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String userlogin;

    private String certificateurl;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserlogin() {
        return userlogin;
    }

    public UserCertificates userlogin(String userlogin) {
        this.userlogin = userlogin;
        return this;
    }

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    public String getCertificateurl() {
        return certificateurl;
    }

    public UserCertificates certificateurl(String certificateurl) {
        this.certificateurl = certificateurl;
        return this;
    }

    public void setCertificateurl(String certificateurl) {
        this.certificateurl = certificateurl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCertificates userCertificates = (UserCertificates) o;
        if (userCertificates.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userCertificates.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserCertificates{" +
            "id=" + id +
            ", userlogin='" + userlogin + "'" +
            ", certificateurl='" + certificateurl + "'" +
            '}';
    }
}
