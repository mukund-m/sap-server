package com.changeapp.service;

import com.changeapp.domain.FieldOptionDefinition;
import java.util.List;

/**
 * Service Interface for managing FieldOptionDefinition.
 */
public interface FieldOptionDefinitionService {

    /**
     * Save a fieldOptionDefinition.
     *
     * @param fieldOptionDefinition the entity to save
     * @return the persisted entity
     */
    FieldOptionDefinition save(FieldOptionDefinition fieldOptionDefinition);

    /**
     *  Get all the fieldOptionDefinitions.
     *
     *  @return the list of entities
     */
    List<FieldOptionDefinition> findAll();

    /**
     *  Get the "id" fieldOptionDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FieldOptionDefinition findOne(Long id);

    /**
     *  Delete the "id" fieldOptionDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
