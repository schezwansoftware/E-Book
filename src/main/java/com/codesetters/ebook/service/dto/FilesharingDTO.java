package com.codesetters.ebook.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Filesharing entity.
 */
public class FilesharingDTO implements Serializable {

    private UUID id;

    private String filename;

    private String sharedby;

    private String sharedto;

    private LocalDate sharedon;

    private Boolean verified;

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

    public String getSharedby() {
        return sharedby;
    }

    public void setSharedby(String sharedby) {
        this.sharedby = sharedby;
    }

    public String getSharedto() {
        return sharedto;
    }

    public void setSharedto(String sharedto) {
        this.sharedto = sharedto;
    }

    public LocalDate getSharedon() {
        return sharedon;
    }

    public void setSharedon(LocalDate sharedon) {
        this.sharedon = sharedon;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilesharingDTO filesharingDTO = (FilesharingDTO) o;
        if(filesharingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filesharingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FilesharingDTO{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", sharedby='" + getSharedby() + "'" +
            ", sharedto='" + getSharedto() + "'" +
            ", sharedon='" + getSharedon() + "'" +
            ", verified='" + isVerified() + "'" +
            "}";
    }
}
