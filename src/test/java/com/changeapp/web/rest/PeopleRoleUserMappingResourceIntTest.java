package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.PeopleRoleUserMapping;
import com.changeapp.repository.PeopleRoleUserMappingRepository;
import com.changeapp.service.PeopleRoleUserMappingService;
import com.changeapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeopleRoleUserMappingResource REST controller.
 *
 * @see PeopleRoleUserMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class PeopleRoleUserMappingResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private PeopleRoleUserMappingRepository peopleRoleUserMappingRepository;

    @Autowired
    private PeopleRoleUserMappingService peopleRoleUserMappingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeopleRoleUserMappingMockMvc;

    private PeopleRoleUserMapping peopleRoleUserMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PeopleRoleUserMappingResource peopleRoleUserMappingResource = new PeopleRoleUserMappingResource(peopleRoleUserMappingService);
        this.restPeopleRoleUserMappingMockMvc = MockMvcBuilders.standaloneSetup(peopleRoleUserMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeopleRoleUserMapping createEntity(EntityManager em) {
        PeopleRoleUserMapping peopleRoleUserMapping = new PeopleRoleUserMapping()
            .userID(DEFAULT_USER_ID);
        return peopleRoleUserMapping;
    }

    @Before
    public void initTest() {
        peopleRoleUserMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeopleRoleUserMapping() throws Exception {
        int databaseSizeBeforeCreate = peopleRoleUserMappingRepository.findAll().size();

        // Create the PeopleRoleUserMapping
        restPeopleRoleUserMappingMockMvc.perform(post("/api/people-role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleRoleUserMapping)))
            .andExpect(status().isCreated());

        // Validate the PeopleRoleUserMapping in the database
        List<PeopleRoleUserMapping> peopleRoleUserMappingList = peopleRoleUserMappingRepository.findAll();
        assertThat(peopleRoleUserMappingList).hasSize(databaseSizeBeforeCreate + 1);
        PeopleRoleUserMapping testPeopleRoleUserMapping = peopleRoleUserMappingList.get(peopleRoleUserMappingList.size() - 1);
        assertThat(testPeopleRoleUserMapping.getUserID()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createPeopleRoleUserMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRoleUserMappingRepository.findAll().size();

        // Create the PeopleRoleUserMapping with an existing ID
        peopleRoleUserMapping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleRoleUserMappingMockMvc.perform(post("/api/people-role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleRoleUserMapping)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PeopleRoleUserMapping> peopleRoleUserMappingList = peopleRoleUserMappingRepository.findAll();
        assertThat(peopleRoleUserMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeopleRoleUserMappings() throws Exception {
        // Initialize the database
        peopleRoleUserMappingRepository.saveAndFlush(peopleRoleUserMapping);

        // Get all the peopleRoleUserMappingList
        restPeopleRoleUserMappingMockMvc.perform(get("/api/people-role-user-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peopleRoleUserMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void getPeopleRoleUserMapping() throws Exception {
        // Initialize the database
        peopleRoleUserMappingRepository.saveAndFlush(peopleRoleUserMapping);

        // Get the peopleRoleUserMapping
        restPeopleRoleUserMappingMockMvc.perform(get("/api/people-role-user-mappings/{id}", peopleRoleUserMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(peopleRoleUserMapping.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeopleRoleUserMapping() throws Exception {
        // Get the peopleRoleUserMapping
        restPeopleRoleUserMappingMockMvc.perform(get("/api/people-role-user-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeopleRoleUserMapping() throws Exception {
        // Initialize the database
        peopleRoleUserMappingService.save(peopleRoleUserMapping);

        int databaseSizeBeforeUpdate = peopleRoleUserMappingRepository.findAll().size();

        // Update the peopleRoleUserMapping
        PeopleRoleUserMapping updatedPeopleRoleUserMapping = peopleRoleUserMappingRepository.findOne(peopleRoleUserMapping.getId());
        updatedPeopleRoleUserMapping
            .userID(UPDATED_USER_ID);

        restPeopleRoleUserMappingMockMvc.perform(put("/api/people-role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeopleRoleUserMapping)))
            .andExpect(status().isOk());

        // Validate the PeopleRoleUserMapping in the database
        List<PeopleRoleUserMapping> peopleRoleUserMappingList = peopleRoleUserMappingRepository.findAll();
        assertThat(peopleRoleUserMappingList).hasSize(databaseSizeBeforeUpdate);
        PeopleRoleUserMapping testPeopleRoleUserMapping = peopleRoleUserMappingList.get(peopleRoleUserMappingList.size() - 1);
        assertThat(testPeopleRoleUserMapping.getUserID()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPeopleRoleUserMapping() throws Exception {
        int databaseSizeBeforeUpdate = peopleRoleUserMappingRepository.findAll().size();

        // Create the PeopleRoleUserMapping

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeopleRoleUserMappingMockMvc.perform(put("/api/people-role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleRoleUserMapping)))
            .andExpect(status().isCreated());

        // Validate the PeopleRoleUserMapping in the database
        List<PeopleRoleUserMapping> peopleRoleUserMappingList = peopleRoleUserMappingRepository.findAll();
        assertThat(peopleRoleUserMappingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeopleRoleUserMapping() throws Exception {
        // Initialize the database
        peopleRoleUserMappingService.save(peopleRoleUserMapping);

        int databaseSizeBeforeDelete = peopleRoleUserMappingRepository.findAll().size();

        // Get the peopleRoleUserMapping
        restPeopleRoleUserMappingMockMvc.perform(delete("/api/people-role-user-mappings/{id}", peopleRoleUserMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeopleRoleUserMapping> peopleRoleUserMappingList = peopleRoleUserMappingRepository.findAll();
        assertThat(peopleRoleUserMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeopleRoleUserMapping.class);
        PeopleRoleUserMapping peopleRoleUserMapping1 = new PeopleRoleUserMapping();
        peopleRoleUserMapping1.setId(1L);
        PeopleRoleUserMapping peopleRoleUserMapping2 = new PeopleRoleUserMapping();
        peopleRoleUserMapping2.setId(peopleRoleUserMapping1.getId());
        assertThat(peopleRoleUserMapping1).isEqualTo(peopleRoleUserMapping2);
        peopleRoleUserMapping2.setId(2L);
        assertThat(peopleRoleUserMapping1).isNotEqualTo(peopleRoleUserMapping2);
        peopleRoleUserMapping1.setId(null);
        assertThat(peopleRoleUserMapping1).isNotEqualTo(peopleRoleUserMapping2);
    }
}
