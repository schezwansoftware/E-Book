package com.codesetters.ebook.service;

import com.codesetters.ebook.service.dto.OrdersDTO;
import java.util.List;

/**
 * Service Interface for managing Orders.
 */
public interface OrdersService {

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    OrdersDTO save(OrdersDTO ordersDTO);

    /**
     *  Get all the orders.
     *
     *  @return the list of entities
     */
    List<OrdersDTO> findAll();

    /**
     *  Get the "id" orders.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OrdersDTO findOne(String id);

    /**
     *  Delete the "id" orders.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
