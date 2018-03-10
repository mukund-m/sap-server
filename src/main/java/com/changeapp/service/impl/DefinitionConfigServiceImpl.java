package com.changeapp.service.impl;

import com.changeapp.service.DefinitionConfigService;
import com.changeapp.domain.DefinitionConfig;
import com.changeapp.repository.DefinitionConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing DefinitionConfig.
 */
@Service
@Transactional
public class DefinitionConfigServiceImpl implements DefinitionConfigService{

    private final Logger log = LoggerFactory.getLogger(DefinitionConfigServiceImpl.class);

    private final DefinitionConfigRepository definitionConfigRepository;

    public DefinitionConfigServiceImpl(DefinitionConfigRepository definitionConfigRepository) {
        this.definitionConfigRepository = definitionConfigRepository;
    }

    /**
     * Save a definitionConfig.
     *
     * @param definitionConfig the entity to save
     * @return the persisted entity
     */
    @Override
    public DefinitionConfig save(DefinitionConfig definitionConfig) {
        log.debug("Request to save DefinitionConfig : {}", definitionConfig);
        return definitionConfigRepository.save(definitionConfig);
    }

    /**
     *  Get all the definitionConfigs.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DefinitionConfig> findAll() {
        log.debug("Request to get all DefinitionConfigs");
        return definitionConfigRepository.findAll();
    }


    /**
     *  get all the definitionConfigs where ReqType is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DefinitionConfig> findAllWhereReqTypeIsNull() {
        log.debug("Request to get all definitionConfigs where ReqType is null");
        return StreamSupport
            .stream(definitionConfigRepository.findAll().spliterator(), false)
            .filter(definitionConfig -> definitionConfig.getReqType() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one definitionConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DefinitionConfig findOne(Long id) {
        log.debug("Request to get DefinitionConfig : {}", id);
        return definitionConfigRepository.findOne(id);
    }

    /**
     *  Delete the  definitionConfig by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DefinitionConfig : {}", id);
        definitionConfigRepository.delete(id);
    }
}
