package com.codesetters.ebook.domain;

import com.datastax.driver.mapping.annotations.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A Book.
 */
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @PartitionKey
    private UUID id;

    private String name;

    private Integer price;

    private String author;

    private LocalDate released_date;

    private LocalDate added_date;

    private Integer ratings;

    private String description;

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

    public Book name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public Book price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleased_date() {
        return released_date;
    }

    public Book released_date(LocalDate released_date) {
        this.released_date = released_date;
        return this;
    }

    public void setReleased_date(LocalDate released_date) {
        this.released_date = released_date;
    }

    public LocalDate getAdded_date() {
        return added_date;
    }

    public Book added_date(LocalDate added_date) {
        this.added_date = added_date;
        return this;
    }

    public void setAdded_date(LocalDate added_date) {
        this.added_date = added_date;
    }

    public Integer getRatings() {
        return ratings;
    }

    public Book ratings(Integer ratings) {
        this.ratings = ratings;
        return this;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Book book = (Book) o;
        if (book.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", author='" + getAuthor() + "'" +
            ", released_date='" + getReleased_date() + "'" +
            ", added_date='" + getAdded_date() + "'" +
            ", ratings='" + getRatings() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
