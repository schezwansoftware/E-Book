package com.codesetters.ebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.ebook.service.FilesharingService;
import com.codesetters.ebook.web.rest.util.HeaderUtil;
import com.codesetters.ebook.service.dto.FilesharingDTO;
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
 * REST controller for managing Filesharing.
 */
@RestController
@RequestMapping("/api")
public class FilesharingResource {

    private final Logger log = LoggerFactory.getLogger(FilesharingResource.class);

    private static final String ENTITY_NAME = "filesharing";

    private final FilesharingService filesharingService;

    public FilesharingResource(FilesharingService filesharingService) {
        this.filesharingService = filesharingService;
    }

    /**
     * POST  /filesharings : Create a new filesharing.
     *
     * @param filesharingDTO the filesharingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filesharingDTO, or with status 400 (Bad Request) if the filesharing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filesharings")
    @Timed
    public ResponseEntity<FilesharingDTO> createFilesharing(@RequestBody FilesharingDTO filesharingDTO) throws URISyntaxException {
        log.debug("REST request to save Filesharing : {}", filesharingDTO);
        if (filesharingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filesharing cannot already have an ID")).body(null);
        }
        FilesharingDTO result = filesharingService.save(filesharingDTO);
        return ResponseEntity.created(new URI("/api/filesharings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filesharings : Updates an existing filesharing.
     *
     * @param filesharingDTO the filesharingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filesharingDTO,
     * or with status 400 (Bad Request) if the filesharingDTO is not valid,
     * or with status 500 (Internal Server Error) if the filesharingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filesharings")
    @Timed
    public ResponseEntity<FilesharingDTO> updateFilesharing(@RequestBody FilesharingDTO filesharingDTO) throws URISyntaxException {
        log.debug("REST request to update Filesharing : {}", filesharingDTO);
        if (filesharingDTO.getId() == null) {
            return createFilesharing(filesharingDTO);
        }
        FilesharingDTO result = filesharingService.save(filesharingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filesharingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filesharings : get all the filesharings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filesharings in body
     */
    @GetMapping("/filesharings")
    @Timed
    public List<FilesharingDTO> getAllFilesharings() {
        log.debug("REST request to get all Filesharings");
        return filesharingService.findAll();
        }

    /**
     * GET  /filesharings/:id : get the "id" filesharing.
     *
     * @param id the id of the filesharingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filesharingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/filesharings/{id}")
    @Timed
    public ResponseEntity<FilesharingDTO> getFilesharing(@PathVariable String id) {
        log.debug("REST request to get Filesharing : {}", id);
        FilesharingDTO filesharingDTO = filesharingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filesharingDTO));
    }

    /**
     * DELETE  /filesharings/:id : delete the "id" filesharing.
     *
     * @param id the id of the filesharingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filesharings/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilesharing(@PathVariable String id) {
        log.debug("REST request to delete Filesharing : {}", id);
        filesharingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
