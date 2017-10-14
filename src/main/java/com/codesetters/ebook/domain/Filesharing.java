package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A Filesharing.
 */
@Table(name = "filesharing")
public class Filesharing implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private String filename;

    private String sharedby;

    private String sharedto;

    private LocalDate sharedon;

    private Boolean verified;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public Filesharing filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSharedby() {
        return sharedby;
    }

    public Filesharing sharedby(String sharedby) {
        this.sharedby = sharedby;
        return this;
    }

    public void setSharedby(String sharedby) {
        this.sharedby = sharedby;
    }

    public String getSharedto() {
        return sharedto;
    }

    public Filesharing sharedto(String sharedto) {
        this.sharedto = sharedto;
        return this;
    }

    public void setSharedto(String sharedto) {
        this.sharedto = sharedto;
    }

    public LocalDate getSharedon() {
        return sharedon;
    }

    public Filesharing sharedon(LocalDate sharedon) {
        this.sharedon = sharedon;
        return this;
    }

    public void setSharedon(LocalDate sharedon) {
        this.sharedon = sharedon;
    }

    public Boolean isVerified() {
        return verified;
    }

    public Filesharing verified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
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
        Filesharing filesharing = (Filesharing) o;
        if (filesharing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filesharing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filesharing{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", sharedby='" + getSharedby() + "'" +
            ", sharedto='" + getSharedto() + "'" +
            ", sharedon='" + getSharedon() + "'" +
            ", verified='" + isVerified() + "'" +
            "}";
    }
}
