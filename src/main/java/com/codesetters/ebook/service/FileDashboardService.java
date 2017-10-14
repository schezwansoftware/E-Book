package com.codesetters.ebook.service;

import com.codesetters.ebook.service.dto.FileDashboardDTO;
import java.util.List;

/**
 * Service Interface for managing FileDashboard.
 */
public interface FileDashboardService {

    /**
     * Save a fileDashboard.
     *
     * @param fileDashboardDTO the entity to save
     * @return the persisted entity
     */
    FileDashboardDTO save(FileDashboardDTO fileDashboardDTO);

    /**
     *  Get all the fileDashboards.
     *
     *  @return the list of entities
     */
    List<FileDashboardDTO> findAll();

    /**
     *  Get the "id" fileDashboard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FileDashboardDTO findOne(String id);

    /**
     *  Delete the "id" fileDashboard.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
