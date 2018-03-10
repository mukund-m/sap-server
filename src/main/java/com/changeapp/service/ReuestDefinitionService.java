package com.changeapp.service;

import com.changeapp.domain.ReuestDefinition;
import java.util.List;

/**
 * Service Interface for managing ReuestDefinition.
 */
public interface ReuestDefinitionService {

    /**
     * Save a reuestDefinition.
     *
     * @param reuestDefinition the entity to save
     * @return the persisted entity
     */
    ReuestDefinition save(ReuestDefinition reuestDefinition);

    /**
     *  Get all the reuestDefinitions.
     *
     *  @return the list of entities
     */
    List<ReuestDefinition> findAll();

    /**
     *  Get the "id" reuestDefinition.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ReuestDefinition findOne(Long id);

    /**
     *  Delete the "id" reuestDefinition.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
