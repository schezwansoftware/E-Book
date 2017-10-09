package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A FileDashboard.
 */
@Table(name = "fileDashboard")
public class FileDashboard implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private String filename;

    private Long filesize;

    private String createdby;

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

    public FileDashboard filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public FileDashboard filesize(Long filesize) {
        this.filesize = filesize;
        return this;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getCreatedby() {
        return createdby;
    }

    public FileDashboard createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
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
        FileDashboard fileDashboard = (FileDashboard) o;
        if (fileDashboard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileDashboard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileDashboard{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", filesize='" + getFilesize() + "'" +
            ", createdby='" + getCreatedby() + "'" +
            "}";
    }
}
