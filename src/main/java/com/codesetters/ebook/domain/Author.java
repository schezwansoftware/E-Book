package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Author.
 */
@Table(name = "author")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    @NotNull
    private String name;

    private String biography;

    private String genre;

    private Double ratings_total;

    private Double ratings_avg;

    private Integer ratings;

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

    public Author name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public Author biography(String biography) {
        this.biography = biography;
        return this;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getGenre() {
        return genre;
    }

    public Author genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRatings_total() {
        return ratings_total;
    }

    public Author ratings_total(Double ratings_total) {
        this.ratings_total = ratings_total;
        return this;
    }

    public void setRatings_total(Double ratings_total) {
        this.ratings_total = ratings_total;
    }

    public Double getRatings_avg() {
        return ratings_avg;
    }

    public Author ratings_avg(Double ratings_avg) {
        this.ratings_avg = ratings_avg;
        return this;
    }

    public void setRatings_avg(Double ratings_avg) {
        this.ratings_avg = ratings_avg;
    }

    public Integer getRatings() {
        return ratings;
    }

    public Author ratings(Integer ratings) {
        this.ratings = ratings;
        return this;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
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
        Author author = (Author) o;
        if (author.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Author{" +
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
