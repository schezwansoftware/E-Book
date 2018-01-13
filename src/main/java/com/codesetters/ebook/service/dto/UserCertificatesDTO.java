package com.codesetters.ebook.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the UserCertificates entity.
 */
public class UserCertificatesDTO implements Serializable {

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

    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }
    public String getCertificateurl() {
        return certificateurl;
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

        UserCertificatesDTO userCertificatesDTO = (UserCertificatesDTO) o;

        if ( ! Objects.equals(id, userCertificatesDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserCertificatesDTO{" +
            "id=" + id +
            ", userlogin='" + userlogin + "'" +
            ", certificateurl='" + certificateurl + "'" +
            '}';
    }
}
