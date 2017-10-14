package com.codesetters.ebook.service.mapper;

import com.codesetters.ebook.domain.*;
import com.codesetters.ebook.service.dto.FilesharingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Filesharing and its DTO FilesharingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FilesharingMapper extends EntityMapper <FilesharingDTO, Filesharing> {
    
    

}
