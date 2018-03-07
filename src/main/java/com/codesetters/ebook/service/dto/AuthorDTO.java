package com.codesetters.ebook.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Author entity.
 */
public class AuthorDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    private String biography;

    private String genre;

    private Double ratings_total;

    private Double ratings_avg;

    private Integer ratings;

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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRatings_total() {
        return ratings_total;
    }

    public void setRatings_total(Double ratings_total) {
        this.ratings_total = ratings_total;
    }

    public Double getRatings_avg() {
        return ratings_avg;
    }

    public void setRatings_avg(Double ratings_avg) {
        this.ratings_avg = ratings_avg;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorDTO authorDTO = (AuthorDTO) o;
        if(authorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), authorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", biography='" + getBiography() + "'" +
            ", genre='" + getGenre() + "'" +
            ", ratings_total='" + getRatings_total() + "'" +
            ", ratings_avg='" + getRatings_avg() + "'" +
            ", ratings='" + getRatings() + "'" +
            "}";
    }
}
