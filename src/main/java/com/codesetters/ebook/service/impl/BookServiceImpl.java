package com.codesetters.ebook.service.impl;

import com.codesetters.ebook.service.BookService;
import com.codesetters.ebook.domain.Book;
import com.codesetters.ebook.repository.BookRepository;
import com.codesetters.ebook.service.dto.BookDTO;
import com.codesetters.ebook.service.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Book.
 */
@Service
public class BookServiceImpl implements BookService{

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    /**
     *  Get all the books.
     *
     *  @return the list of entities
     */
    @Override
    public List<BookDTO> findAll() {
        log.debug("Request to get all Books");
        return bookRepository.findAll().stream()
            .map(bookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one book by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public BookDTO findOne(String id) {
        log.debug("Request to get Book : {}", id);
        Book book = bookRepository.findOne(UUID.fromString(id));
        return bookMapper.toDto(book);
    }

    /**
     *  Delete the  book by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.delete(UUID.fromString(id));
    }
}
