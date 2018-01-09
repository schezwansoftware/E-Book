package com.codesetters.ebook.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the FileDashboard entity.
 */
public class FileDashboardDTO implements Serializable {

    private UUID id;

    private String filename;

    private Long filesize;

    private String createdby;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileDashboardDTO fileDashboardDTO = (FileDashboardDTO) o;
        if(fileDashboardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileDashboardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileDashboardDTO{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", filesize='" + getFilesize() + "'" +
            ", createdby='" + getCreatedby() + "'" +
            "}";
    }
}
