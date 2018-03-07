package com.codesetters.ebook.service.mapper;

import com.codesetters.ebook.domain.*;
import com.codesetters.ebook.service.dto.MemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Member and its DTO MemberDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MemberMapper extends EntityMapper <MemberDTO, Member> {
    
    

}
