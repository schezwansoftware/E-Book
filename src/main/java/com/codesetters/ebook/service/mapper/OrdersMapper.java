package com.codesetters.ebook.service.mapper;

import com.codesetters.ebook.domain.*;
import com.codesetters.ebook.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdersMapper extends EntityMapper <OrdersDTO, Orders> {
    
    

}
