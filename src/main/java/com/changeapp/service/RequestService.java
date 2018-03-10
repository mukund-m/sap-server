package com.changeapp.service;

import com.changeapp.domain.Request;
import java.util.List;

/**
 * Service Interface for managing Request.
 */
public interface RequestService {

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    Request save(Request request);

    /**
     *  Get all the requests.
     *
     *  @return the list of entities
     */
    List<Request> findAll();

    /**
     *  Get the "id" request.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Request findOne(Long id);

    /**
     *  Delete the "id" request.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
