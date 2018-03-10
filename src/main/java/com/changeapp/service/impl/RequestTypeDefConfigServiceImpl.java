package com.changeapp.service.impl;

import com.changeapp.service.RequestTypeDefConfigService;
import com.changeapp.domain.FieldDefinition;
import com.changeapp.domain.RequestTypeDefConfig;
import com.changeapp.repository.RequestTypeDefConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestTypeDefConfig.
 */
@Service
@Transactional
public class RequestTypeDefConfigServiceImpl implements RequestTypeDefConfigService{

    private final Logger log = LoggerFactory.getLogger(RequestTypeDefConfigServiceImpl.class);

    private final RequestTypeDefConfigRepository requestTypeDefConfigRepository;

    public RequestTypeDefConfigServiceImpl(RequestTypeDefConfigRepository requestTypeDefConfigRepository) {
        this.requestTypeDefConfigRepository = requestTypeDefConfigRepository;
    }

    /**
     * Save a requestTypeDefConfig.
     *
     * @param requestTypeDefConfig the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestTypeDefConfig save(RequestTypeDefConfig requestTypeDefConfig) {
        log.debug("Request to save RequestTypeDefConfig : {}", requestTypeDefConfig);
        return requestTypeDefConfigRepository.save(requestTypeDefConfig);
    }

    /**
     *  Get all the requestTypeDefConfigs.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RequestTypeDefConfig> findAll() {
        log.debug("Request to get all RequestTypeDefConfigs");
        return requestTypeDefConfigRepository.findAll();
    }

    /**
     *  Get one requestTypeDefConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RequestTypeDefConfig findOne(Long id) {
        log.debug("Request to get RequestTypeDefConfig : {}", id);
        return requestTypeDefConfigRepository.findOne(id);
    }

    /**
     *  Delete the  requestTypeDefConfig by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestTypeDefConfig : {}", id);
        requestTypeDefConfigRepository.delete(id);
    }

	@Override
	public List<FieldDefinition> getDefFields(String type) {
		List<RequestTypeDefConfig> list = this.findAll();
		for(RequestTypeDefConfig config: list) {
			if(config.getRequestType().equals(type)) {
				return config.getDefinition().getFieldConfigs();
			}
		}
		return null;
	}
}
