package com.changeapp.service;

import com.changeapp.domain.FieldDefinition;
import com.changeapp.domain.RequestTypeDefConfig;
import java.util.List;

/**
 * Service Interface for managing RequestTypeDefConfig.
 */
public interface RequestTypeDefConfigService {

    /**
     * Save a requestTypeDefConfig.
     *
     * @param requestTypeDefConfig the entity to save
     * @return the persisted entity
     */
    RequestTypeDefConfig save(RequestTypeDefConfig requestTypeDefConfig);

    /**
     *  Get all the requestTypeDefConfigs.
     *
     *  @return the list of entities
     */
    List<RequestTypeDefConfig> findAll();

    /**
     *  Get the "id" requestTypeDefConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RequestTypeDefConfig findOne(Long id);

    /**
     *  Delete the "id" requestTypeDefConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

	List<FieldDefinition> getDefFields(String type);
}
