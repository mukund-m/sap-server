package com.changeapp.service.impl;

import com.changeapp.service.FieldChoiceDefinitionService;
import com.changeapp.domain.FieldChoiceDefinition;
import com.changeapp.repository.FieldChoiceDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing FieldChoiceDefinition.
 */
@Service
@Transactional
public class FieldChoiceDefinitionServiceImpl implements FieldChoiceDefinitionService{

    private final Logger log = LoggerFactory.getLogger(FieldChoiceDefinitionServiceImpl.class);

    private final FieldChoiceDefinitionRepository fieldChoiceDefinitionRepository;

    public FieldChoiceDefinitionServiceImpl(FieldChoiceDefinitionRepository fieldChoiceDefinitionRepository) {
        this.fieldChoiceDefinitionRepository = fieldChoiceDefinitionRepository;
    }

    /**
     * Save a fieldChoiceDefinition.
     *
     * @param fieldChoiceDefinition the entity to save
     * @return the persisted entity
     */
    @Override
    public FieldChoiceDefinition save(FieldChoiceDefinition fieldChoiceDefinition) {
        log.debug("Request to save FieldChoiceDefinition : {}", fieldChoiceDefinition);
        return fieldChoiceDefinitionRepository.save(fieldChoiceDefinition);
    }

    /**
     *  Get all the fieldChoiceDefinitions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FieldChoiceDefinition> findAll() {
        log.debug("Request to get all FieldChoiceDefinitions");
        return fieldChoiceDefinitionRepository.findAll();
    }

    /**
     *  Get one fieldChoiceDefinition by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FieldChoiceDefinition findOne(Long id) {
        log.debug("Request to get FieldChoiceDefinition : {}", id);
        return fieldChoiceDefinitionRepository.findOne(id);
    }

    /**
     *  Delete the  fieldChoiceDefinition by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldChoiceDefinition : {}", id);
        fieldChoiceDefinitionRepository.delete(id);
    }
}
