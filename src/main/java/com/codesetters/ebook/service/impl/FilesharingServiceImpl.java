package com.codesetters.ebook.service.impl;

import com.codesetters.ebook.service.FilesharingService;
import com.codesetters.ebook.domain.Filesharing;
import com.codesetters.ebook.repository.FilesharingRepository;
import com.codesetters.ebook.service.dto.FilesharingDTO;
import com.codesetters.ebook.service.mapper.FilesharingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Filesharing.
 */
@Service
public class FilesharingServiceImpl implements FilesharingService{

    private final Logger log = LoggerFactory.getLogger(FilesharingServiceImpl.class);

    private final FilesharingRepository filesharingRepository;

    private final FilesharingMapper filesharingMapper;

    public FilesharingServiceImpl(FilesharingRepository filesharingRepository, FilesharingMapper filesharingMapper) {
        this.filesharingRepository = filesharingRepository;
        this.filesharingMapper = filesharingMapper;
    }

    /**
     * Save a filesharing.
     *
     * @param filesharingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FilesharingDTO save(FilesharingDTO filesharingDTO) {
        log.debug("Request to save Filesharing : {}", filesharingDTO);
        Filesharing filesharing = filesharingMapper.toEntity(filesharingDTO);
        filesharing = filesharingRepository.save(filesharing);
        return filesharingMapper.toDto(filesharing);
    }

    /**
     *  Get all the filesharings.
     *
     *  @return the list of entities
     */
    @Override
    public List<FilesharingDTO> findAll() {
        log.debug("Request to get all Filesharings");
        return filesharingRepository.findAll().stream()
            .map(filesharingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one filesharing by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public FilesharingDTO findOne(String id) {
        log.debug("Request to get Filesharing : {}", id);
        Filesharing filesharing = filesharingRepository.findOne(UUID.fromString(id));
        return filesharingMapper.toDto(filesharing);
    }

    /**
     *  Delete the  filesharing by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Filesharing : {}", id);
        filesharingRepository.delete(UUID.fromString(id));
    }
}
