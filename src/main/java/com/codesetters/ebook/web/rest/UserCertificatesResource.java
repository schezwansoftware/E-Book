package com.codesetters.ebook.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.codesetters.ebook.service.UserCertificatesService;
import com.codesetters.ebook.web.rest.util.HeaderUtil;
import com.codesetters.ebook.service.dto.UserCertificatesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserCertificates.
 */
@RestController
@RequestMapping("/api")
public class UserCertificatesResource {

    private final Logger log = LoggerFactory.getLogger(UserCertificatesResource.class);

    private static final String ENTITY_NAME = "userCertificates";
        
    private final UserCertificatesService userCertificatesService;

    public UserCertificatesResource(UserCertificatesService userCertificatesService) {
        this.userCertificatesService = userCertificatesService;
    }

    /**
     * POST  /user-certificates : Create a new userCertificates.
     *
     * @param userCertificatesDTO the userCertificatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userCertificatesDTO, or with status 400 (Bad Request) if the userCertificates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-certificates")
    @Timed
    public ResponseEntity<UserCertificatesDTO> createUserCertificates(@RequestBody UserCertificatesDTO userCertificatesDTO) throws URISyntaxException {
        log.debug("REST request to save UserCertificates : {}", userCertificatesDTO);
        if (userCertificatesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userCertificates cannot already have an ID")).body(null);
        }
        UserCertificatesDTO result = userCertificatesService.save(userCertificatesDTO);
        return ResponseEntity.created(new URI("/api/user-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-certificates : Updates an existing userCertificates.
     *
     * @param userCertificatesDTO the userCertificatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userCertificatesDTO,
     * or with status 400 (Bad Request) if the userCertificatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the userCertificatesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-certificates")
    @Timed
    public ResponseEntity<UserCertificatesDTO> updateUserCertificates(@RequestBody UserCertificatesDTO userCertificatesDTO) throws URISyntaxException {
        log.debug("REST request to update UserCertificates : {}", userCertificatesDTO);
        if (userCertificatesDTO.getId() == null) {
            return createUserCertificates(userCertificatesDTO);
        }
        UserCertificatesDTO result = userCertificatesService.save(userCertificatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userCertificatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-certificates : get all the userCertificates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userCertificates in body
     */
    @GetMapping("/user-certificates")
    @Timed
    public List<UserCertificatesDTO> getAllUserCertificates() {
        log.debug("REST request to get all UserCertificates");
        return userCertificatesService.findAll();
    }

    /**
     * GET  /user-certificates/:id : get the "id" userCertificates.
     *
     * @param id the id of the userCertificatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userCertificatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-certificates/{id}")
    @Timed
    public ResponseEntity<UserCertificatesDTO> getUserCertificates(@PathVariable String id) {
        log.debug("REST request to get UserCertificates : {}", id);
        UserCertificatesDTO userCertificatesDTO = userCertificatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCertificatesDTO));
    }

    /**
     * DELETE  /user-certificates/:id : delete the "id" userCertificates.
     *
     * @param id the id of the userCertificatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-certificates/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserCertificates(@PathVariable String id) {
        log.debug("REST request to delete UserCertificates : {}", id);
        userCertificatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
