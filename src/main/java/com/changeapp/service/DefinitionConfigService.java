package com.changeapp.service;

import com.changeapp.domain.DefinitionConfig;
import java.util.List;

/**
 * Service Interface for managing DefinitionConfig.
 */
public interface DefinitionConfigService {

    /**
     * Save a definitionConfig.
     *
     * @param definitionConfig the entity to save
     * @return the persisted entity
     */
    DefinitionConfig save(DefinitionConfig definitionConfig);

    /**
     *  Get all the definitionConfigs.
     *
     *  @return the list of entities
     */
    List<DefinitionConfig> findAll();
    /**
     *  Get all the DefinitionConfigDTO where ReqType is null.
     *
     *  @return the list of entities
     */
    List<DefinitionConfig> findAllWhereReqTypeIsNull();

    /**
     *  Get the "id" definitionConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DefinitionConfig findOne(Long id);

    /**
     *  Delete the "id" definitionConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
