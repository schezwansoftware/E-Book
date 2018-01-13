package com.codesetters.ebook.service;

import com.codesetters.ebook.domain.UserCertificates;
import com.codesetters.ebook.repository.UserCertificatesRepository;
import com.codesetters.ebook.service.dto.UserCertificatesDTO;
import com.codesetters.ebook.service.mapper.UserCertificatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserCertificates.
 */
@Service
public class UserCertificatesService {

    private final Logger log = LoggerFactory.getLogger(UserCertificatesService.class);
    
    private final UserCertificatesRepository userCertificatesRepository;

    private final UserCertificatesMapper userCertificatesMapper;

    public UserCertificatesService(UserCertificatesRepository userCertificatesRepository, UserCertificatesMapper userCertificatesMapper) {
        this.userCertificatesRepository = userCertificatesRepository;
        this.userCertificatesMapper = userCertificatesMapper;
    }

    /**
     * Save a userCertificates.
     *
     * @param userCertificatesDTO the entity to save
     * @return the persisted entity
     */
    public UserCertificatesDTO save(UserCertificatesDTO userCertificatesDTO) {
        log.debug("Request to save UserCertificates : {}", userCertificatesDTO);
        UserCertificates userCertificates = userCertificatesMapper.userCertificatesDTOToUserCertificates(userCertificatesDTO);
        userCertificates = userCertificatesRepository.save(userCertificates);
        UserCertificatesDTO result = userCertificatesMapper.userCertificatesToUserCertificatesDTO(userCertificates);
        return result;
    }

    /**
     *  Get all the userCertificates.
     *  
     *  @return the list of entities
     */
    public List<UserCertificatesDTO> findAll() {
        log.debug("Request to get all UserCertificates");
        List<UserCertificatesDTO> result = userCertificatesRepository.findAll().stream()
            .map(userCertificatesMapper::userCertificatesToUserCertificatesDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one userCertificates by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public UserCertificatesDTO findOne(String id) {
        log.debug("Request to get UserCertificates : {}", id);
        UserCertificates userCertificates = userCertificatesRepository.findOne(UUID.fromString(id));
        UserCertificatesDTO userCertificatesDTO = userCertificatesMapper.userCertificatesToUserCertificatesDTO(userCertificates);
        return userCertificatesDTO;
    }

    /**
     *  Delete the  userCertificates by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete UserCertificates : {}", id);
        userCertificatesRepository.delete(UUID.fromString(id));
    }
}
