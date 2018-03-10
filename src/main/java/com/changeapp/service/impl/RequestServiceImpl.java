package com.changeapp.service.impl;

import com.changeapp.service.RequestService;
import com.changeapp.domain.Request;
import com.changeapp.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Request.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService{

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    @Override
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    /**
     *  Get all the requests.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Request> findAll() {
        log.debug("Request to get all Requests");
        return requestRepository.findAll();
    }

    /**
     *  Get one request by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Request findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findOne(id);
    }

    /**
     *  Delete the  request by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.delete(id);
    }
}
