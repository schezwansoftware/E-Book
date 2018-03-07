package com.codesetters.ebook.web.rest;

import com.codesetters.ebook.AbstractCassandraTest;
import com.codesetters.ebook.EbookApp;

import com.codesetters.ebook.domain.Book;
import com.codesetters.ebook.repository.BookRepository;
import com.codesetters.ebook.service.BookService;
import com.codesetters.ebook.service.dto.BookDTO;
import com.codesetters.ebook.service.mapper.BookMapper;
import com.codesetters.ebook.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbookApp.class)
public class BookResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RELEASED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RELEASED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ADDED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADDED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_RATINGS = 1;
    private static final Integer UPDATED_RATINGS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT = "BBBBBBBBBB";

    private static final String DEFAULT_ISBN_NO = "AAAAAAAAAA";
    private static final String UPDATED_ISBN_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_RATINGS_AVG = 1D;
    private static final Double UPDATED_RATINGS_AVG = 2D;

    private static final Double DEFAULT_RATINGS_TOTAL = 1D;
    private static final Double UPDATED_RATINGS_TOTAL = 2D;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restBookMockMvc;

    private Book book;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookResource bookResource = new BookResource(bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity() {
        Book book = new Book()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .author(DEFAULT_AUTHOR)
            .released_date(DEFAULT_RELEASED_DATE)
            .added_date(DEFAULT_ADDED_DATE)
            .ratings(DEFAULT_RATINGS)
            .description(DEFAULT_DESCRIPTION)
            .language(DEFAULT_LANGUAGE)
            .format(DEFAULT_FORMAT)
            .isbn_no(DEFAULT_ISBN_NO)
            .ratings_avg(DEFAULT_RATINGS_AVG)
            .ratings_total(DEFAULT_RATINGS_TOTAL);
        return book;
    }

    @Before
    public void initTest() {
        bookRepository.deleteAll();
        book = createEntity();
    }

    @Test
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBook.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getReleased_date()).isEqualTo(DEFAULT_RELEASED_DATE);
        assertThat(testBook.getAdded_date()).isEqualTo(DEFAULT_ADDED_DATE);
        assertThat(testBook.getRatings()).isEqualTo(DEFAULT_RATINGS);
        assertThat(testBook.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBook.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testBook.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testBook.getIsbn_no()).isEqualTo(DEFAULT_ISBN_NO);
        assertThat(testBook.getRatings_avg()).isEqualTo(DEFAULT_RATINGS_AVG);
        assertThat(testBook.getRatings_total()).isEqualTo(DEFAULT_RATINGS_TOTAL);
    }

    @Test
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(UUID.randomUUID());
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].released_date").value(hasItem(DEFAULT_RELEASED_DATE.toString())))
            .andExpect(jsonPath("$.[*].added_date").value(hasItem(DEFAULT_ADDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].ratings").value(hasItem(DEFAULT_RATINGS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].isbn_no").value(hasItem(DEFAULT_ISBN_NO.toString())))
            .andExpect(jsonPath("$.[*].ratings_avg").value(hasItem(DEFAULT_RATINGS_AVG.doubleValue())))
            .andExpect(jsonPath("$.[*].ratings_total").value(hasItem(DEFAULT_RATINGS_TOTAL.doubleValue())));
    }

    @Test
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.released_date").value(DEFAULT_RELEASED_DATE.toString()))
            .andExpect(jsonPath("$.added_date").value(DEFAULT_ADDED_DATE.toString()))
            .andExpect(jsonPath("$.ratings").value(DEFAULT_RATINGS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.isbn_no").value(DEFAULT_ISBN_NO.toString()))
            .andExpect(jsonPath("$.ratings_avg").value(DEFAULT_RATINGS_AVG.doubleValue()))
            .andExpect(jsonPath("$.ratings_total").value(DEFAULT_RATINGS_TOTAL.doubleValue()));
    }

    @Test
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findOne(book.getId());
        updatedBook
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .author(UPDATED_AUTHOR)
            .released_date(UPDATED_RELEASED_DATE)
            .added_date(UPDATED_ADDED_DATE)
            .ratings(UPDATED_RATINGS)
            .description(UPDATED_DESCRIPTION)
            .language(UPDATED_LANGUAGE)
            .format(UPDATED_FORMAT)
            .isbn_no(UPDATED_ISBN_NO)
            .ratings_avg(UPDATED_RATINGS_AVG)
            .ratings_total(UPDATED_RATINGS_TOTAL);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBook.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getReleased_date()).isEqualTo(UPDATED_RELEASED_DATE);
        assertThat(testBook.getAdded_date()).isEqualTo(UPDATED_ADDED_DATE);
        assertThat(testBook.getRatings()).isEqualTo(UPDATED_RATINGS);
        assertThat(testBook.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBook.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testBook.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testBook.getIsbn_no()).isEqualTo(UPDATED_ISBN_NO);
        assertThat(testBook.getRatings_avg()).isEqualTo(UPDATED_RATINGS_AVG);
        assertThat(testBook.getRatings_total()).isEqualTo(UPDATED_RATINGS_TOTAL);
    }

    @Test
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);
        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();
        book1.setId(UUID.randomUUID());
        Book book2 = new Book();
        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId(UUID.randomUUID());
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookDTO.class);
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(UUID.randomUUID());
        BookDTO bookDTO2 = new BookDTO();
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO2.setId(bookDTO1.getId());
        assertThat(bookDTO1).isEqualTo(bookDTO2);
        bookDTO2.setId(UUID.randomUUID());
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
        bookDTO1.setId(null);
        assertThat(bookDTO1).isNotEqualTo(bookDTO2);
    }
}
