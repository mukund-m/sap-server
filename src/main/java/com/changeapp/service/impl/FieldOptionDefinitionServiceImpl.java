package com.changeapp.service.impl;

import com.changeapp.service.FieldOptionDefinitionService;
import com.changeapp.domain.FieldOptionDefinition;
import com.changeapp.repository.FieldOptionDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing FieldOptionDefinition.
 */
@Service
@Transactional
public class FieldOptionDefinitionServiceImpl implements FieldOptionDefinitionService{

    private final Logger log = LoggerFactory.getLogger(FieldOptionDefinitionServiceImpl.class);

    private final FieldOptionDefinitionRepository fieldOptionDefinitionRepository;

    public FieldOptionDefinitionServiceImpl(FieldOptionDefinitionRepository fieldOptionDefinitionRepository) {
        this.fieldOptionDefinitionRepository = fieldOptionDefinitionRepository;
    }

    /**
     * Save a fieldOptionDefinition.
     *
     * @param fieldOptionDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public FieldOptionDefinition save(FieldOptionDefinition fieldOptionDefinition) {
        log.debug("Request to save FieldOptionDefinition : {}", fieldOptionDefinition);
        return fieldOptionDefinitionRepository.save(fieldOptionDefinition);
    }

    /**
     *  Get all the fieldOptionDefinitions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FieldOptionDefinition> findAll() {
        log.debug("Request to get all FieldOptionDefinitions");
        return fieldOptionDefinitionRepository.findAll();
    }

    /**
     *  Get one fieldOptionDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FieldOptionDefinition findOne(Long id) {
        log.debug("Request to get FieldOptionDefinition : {}", id);
        return fieldOptionDefinitionRepository.findOne(id);
    }

    /**
     *  Delete the  fieldOptionDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldOptionDefinition : {}", id);
        fieldOptionDefinitionRepository.delete(id);
    }
}
