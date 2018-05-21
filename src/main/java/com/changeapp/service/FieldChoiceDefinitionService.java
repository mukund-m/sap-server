package com.changeapp.service;

import com.changeapp.domain.FieldChoiceDefinition;
import java.util.List;

/**
 * Service Interface for managing FieldChoiceDefinition.
 */
public interface FieldChoiceDefinitionService {

    /**
     * Save a fieldChoiceDefinition.
     *
     * @param fieldChoiceDefinition the entity to save
     * @return the persisted entity
     */
    FieldChoiceDefinition save(FieldChoiceDefinition fieldChoiceDefinition);

    /**
     *  Get all the fieldChoiceDefinitions.
     *
     *  @return the list of entities
     */
    List<FieldChoiceDefinition> findAll();

    /**
     *  Get the "id" fieldChoiceDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FieldChoiceDefinition findOne(Long id);

    /**
     *  Delete the "id" fieldChoiceDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
