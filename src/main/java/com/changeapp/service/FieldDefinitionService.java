package com.changeapp.service;

import com.changeapp.domain.FieldDefinition;
import java.util.List;

/**
 * Service Interface for managing FieldDefinition.
 */
public interface FieldDefinitionService {

    /**
     * Save a fieldDefinition.
     *
     * @param fieldDefinition the entity to save
     * @return the persisted entity
     */
    FieldDefinition save(FieldDefinition fieldDefinition);

    /**
     *  Get all the fieldDefinitions.
     *
     *  @return the list of entities
     */
    List<FieldDefinition> findAll();

    /**
     *  Get the "id" fieldDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FieldDefinition findOne(Long id);

    /**
     *  Delete the "id" fieldDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
