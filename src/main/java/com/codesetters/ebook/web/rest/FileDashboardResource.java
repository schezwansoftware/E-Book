package com.codesetters.ebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.ebook.service.FileDashboardService;
import com.codesetters.ebook.web.rest.util.HeaderUtil;
import com.codesetters.ebook.service.dto.FileDashboardDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing FileDashboard.
 */
@RestController
@RequestMapping("/api")
public class FileDashboardResource {

    private final Logger log = LoggerFactory.getLogger(FileDashboardResource.class);

    private static final String ENTITY_NAME = "fileDashboard";

    private final FileDashboardService fileDashboardService;

    public FileDashboardResource(FileDashboardService fileDashboardService) {
        this.fileDashboardService = fileDashboardService;
    }

    /**
     * POST  /file-dashboards : Create a new fileDashboard.
     *
     * @param fileDashboardDTO the fileDashboardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fileDashboardDTO, or with status 400 (Bad Request) if the fileDashboard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/file-dashboards")
    @Timed
    public ResponseEntity<FileDashboardDTO> createFileDashboard(@RequestBody FileDashboardDTO fileDashboardDTO) throws URISyntaxException {
        log.debug("REST request to save FileDashboard : {}", fileDashboardDTO);
        if (fileDashboardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fileDashboard cannot already have an ID")).body(null);
        }
        FileDashboardDTO result = fileDashboardService.save(fileDashboardDTO);
        return ResponseEntity.created(new URI("/api/file-dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /file-dashboards : Updates an existing fileDashboard.
     *
     * @param fileDashboardDTO the fileDashboardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fileDashboardDTO,
     * or with status 400 (Bad Request) if the fileDashboardDTO is not valid,
     * or with status 500 (Internal Server Error) if the fileDashboardDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/file-dashboards")
    @Timed
    public ResponseEntity<FileDashboardDTO> updateFileDashboard(@RequestBody FileDashboardDTO fileDashboardDTO) throws URISyntaxException {
        log.debug("REST request to update FileDashboard : {}", fileDashboardDTO);
        if (fileDashboardDTO.getId() == null) {
            return createFileDashboard(fileDashboardDTO);
        }
        FileDashboardDTO result = fileDashboardService.save(fileDashboardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fileDashboardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /file-dashboards : get all the fileDashboards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fileDashboards in body
     */
    @GetMapping("/file-dashboards")
    @Timed
    public List<FileDashboardDTO> getAllFileDashboards() {
        log.debug("REST request to get all FileDashboards");
        return fileDashboardService.findAll();
        }

    /**
     * GET  /file-dashboards/:id : get the "id" fileDashboard.
     *
     * @param id the id of the fileDashboardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fileDashboardDTO, or with status 404 (Not Found)
     */
    @GetMapping("/file-dashboards/{id}")
    @Timed
    public ResponseEntity<FileDashboardDTO> getFileDashboard(@PathVariable String id) {
        log.debug("REST request to get FileDashboard : {}", id);
        FileDashboardDTO fileDashboardDTO = fileDashboardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fileDashboardDTO));
    }

    /**
     * DELETE  /file-dashboards/:id : delete the "id" fileDashboard.
     *
     * @param id the id of the fileDashboardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/file-dashboards/{id}")
    @Timed
    public ResponseEntity<Void> deleteFileDashboard(@PathVariable String id) {
        log.debug("REST request to delete FileDashboard : {}", id);
        fileDashboardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
