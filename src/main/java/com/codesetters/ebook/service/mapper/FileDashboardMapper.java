package com.codesetters.ebook.service.mapper;

import com.codesetters.ebook.domain.*;
import com.codesetters.ebook.service.dto.FileDashboardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FileDashboard and its DTO FileDashboardDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileDashboardMapper extends EntityMapper <FileDashboardDTO, FileDashboard> {
    
    

}
