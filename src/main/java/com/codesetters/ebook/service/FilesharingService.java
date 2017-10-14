package com.codesetters.ebook.service;

import com.codesetters.ebook.service.dto.FilesharingDTO;
import java.util.List;

/**
 * Service Interface for managing Filesharing.
 */
public interface FilesharingService {

    /**
     * Save a filesharing.
     *
     * @param filesharingDTO the entity to save
     * @return the persisted entity
     */
    FilesharingDTO save(FilesharingDTO filesharingDTO);

    /**
     *  Get all the filesharings.
     *
     *  @return the list of entities
     */
    List<FilesharingDTO> findAll();

    /**
     *  Get the "id" filesharing.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FilesharingDTO findOne(String id);

    /**
     *  Delete the "id" filesharing.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
