package com.codesetters.ebook.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Book entity.
 */
public class BookDTO implements Serializable {

    private UUID id;

    private String name;

    private Integer price;

    private String author;

    private LocalDate released_date;

    private LocalDate added_date;

    private Integer ratings;

    private String description;

    private String language;

    private String format;

    private String isbn_no;

    private Double ratings_avg;

    private Double ratings_total;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleased_date() {
        return released_date;
    }

    public void setReleased_date(LocalDate released_date) {
        this.released_date = released_date;
    }

    public LocalDate getAdded_date() {
        return added_date;
    }

    public void setAdded_date(LocalDate added_date) {
        this.added_date = added_date;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIsbn_no() {
        return isbn_no;
    }

    public void setIsbn_no(String isbn_no) {
        this.isbn_no = isbn_no;
    }

    public Double getRatings_avg() {
        return ratings_avg;
    }

    public void setRatings_avg(Double ratings_avg) {
        this.ratings_avg = ratings_avg;
    }

    public Double getRatings_total() {
        return ratings_total;
    }

    public void setRatings_total(Double ratings_total) {
        this.ratings_total = ratings_total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if(bookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", author='" + getAuthor() + "'" +
            ", released_date='" + getReleased_date() + "'" +
            ", added_date='" + getAdded_date() + "'" +
            ", ratings='" + getRatings() + "'" +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            ", format='" + getFormat() + "'" +
            ", isbn_no='" + getIsbn_no() + "'" +
            ", ratings_avg='" + getRatings_avg() + "'" +
            ", ratings_total='" + getRatings_total() + "'" +
            "}";
    }
}
