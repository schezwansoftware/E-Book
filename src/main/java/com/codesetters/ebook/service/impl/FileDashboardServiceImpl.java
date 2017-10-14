package com.codesetters.ebook.service.impl;

import com.codesetters.ebook.service.FileDashboardService;
import com.codesetters.ebook.domain.FileDashboard;
import com.codesetters.ebook.repository.FileDashboardRepository;
import com.codesetters.ebook.service.dto.FileDashboardDTO;
import com.codesetters.ebook.service.mapper.FileDashboardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FileDashboard.
 */
@Service
public class FileDashboardServiceImpl implements FileDashboardService{

    private final Logger log = LoggerFactory.getLogger(FileDashboardServiceImpl.class);

    private final FileDashboardRepository fileDashboardRepository;

    private final FileDashboardMapper fileDashboardMapper;

    public FileDashboardServiceImpl(FileDashboardRepository fileDashboardRepository, FileDashboardMapper fileDashboardMapper) {
        this.fileDashboardRepository = fileDashboardRepository;
        this.fileDashboardMapper = fileDashboardMapper;
    }

    /**
     * Save a fileDashboard.
     *
     * @param fileDashboardDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileDashboardDTO save(FileDashboardDTO fileDashboardDTO) {
        log.debug("Request to save FileDashboard : {}", fileDashboardDTO);
        FileDashboard fileDashboard = fileDashboardMapper.toEntity(fileDashboardDTO);
        fileDashboard = fileDashboardRepository.save(fileDashboard);
        return fileDashboardMapper.toDto(fileDashboard);
    }

    /**
     *  Get all the fileDashboards.
     *
     *  @return the list of entities
     */
    @Override
    public List<FileDashboardDTO> findAll() {
        log.debug("Request to get all FileDashboards");
        return fileDashboardRepository.findAll().stream()
            .map(fileDashboardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one fileDashboard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public FileDashboardDTO findOne(String id) {
        log.debug("Request to get FileDashboard : {}", id);
        FileDashboard fileDashboard = fileDashboardRepository.findOne(UUID.fromString(id));
        return fileDashboardMapper.toDto(fileDashboard);
    }

    /**
     *  Delete the  fileDashboard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete FileDashboard : {}", id);
        fileDashboardRepository.delete(UUID.fromString(id));
    }
}
