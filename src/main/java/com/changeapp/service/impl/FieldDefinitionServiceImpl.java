package com.changeapp.service.impl;

import com.changeapp.service.FieldDefinitionService;
import com.changeapp.domain.FieldDefinition;
import com.changeapp.repository.FieldDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing FieldDefinition.
 */
@Service
@Transactional
public class FieldDefinitionServiceImpl implements FieldDefinitionService{

    private final Logger log = LoggerFactory.getLogger(FieldDefinitionServiceImpl.class);

    private final FieldDefinitionRepository fieldDefinitionRepository;

    public FieldDefinitionServiceImpl(FieldDefinitionRepository fieldDefinitionRepository) {
        this.fieldDefinitionRepository = fieldDefinitionRepository;
    }

    /**
     * Save a fieldDefinition.
     *
     * @param fieldDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public FieldDefinition save(FieldDefinition fieldDefinition) {
        log.debug("Request to save FieldDefinition : {}", fieldDefinition);
        return fieldDefinitionRepository.save(fieldDefinition);
    }

    /**
     *  Get all the fieldDefinitions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FieldDefinition> findAll() {
        log.debug("Request to get all FieldDefinitions");
        return fieldDefinitionRepository.findAll();
    }

    /**
     *  Get one fieldDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FieldDefinition findOne(Long id) {
        log.debug("Request to get FieldDefinition : {}", id);
        return fieldDefinitionRepository.findOne(id);
    }

    /**
     *  Delete the  fieldDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldDefinition : {}", id);
        fieldDefinitionRepository.delete(id);
    }
}
