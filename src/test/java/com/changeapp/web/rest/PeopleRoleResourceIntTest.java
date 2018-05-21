package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.PeopleRole;
import com.changeapp.repository.PeopleRoleRepository;
import com.changeapp.service.PeopleRoleService;
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
 * Test class for the PeopleRoleResource REST controller.
 *
 * @see PeopleRoleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class PeopleRoleResourceIntTest {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    @Autowired
    private PeopleRoleRepository peopleRoleRepository;

    @Autowired
    private PeopleRoleService peopleRoleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeopleRoleMockMvc;

    private PeopleRole peopleRole;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PeopleRoleResource peopleRoleResource = new PeopleRoleResource(peopleRoleService);
        this.restPeopleRoleMockMvc = MockMvcBuilders.standaloneSetup(peopleRoleResource)
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
    public static PeopleRole createEntity(EntityManager em) {
        PeopleRole peopleRole = new PeopleRole()
            .roleName(DEFAULT_ROLE_NAME);
        return peopleRole;
    }

    @Before
    public void initTest() {
        peopleRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeopleRole() throws Exception {
        int databaseSizeBeforeCreate = peopleRoleRepository.findAll().size();

        // Create the PeopleRole
        restPeopleRoleMockMvc.perform(post("/api/people-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleRole)))
            .andExpect(status().isCreated());

        // Validate the PeopleRole in the database
        List<PeopleRole> peopleRoleList = peopleRoleRepository.findAll();
        assertThat(peopleRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PeopleRole testPeopleRole = peopleRoleList.get(peopleRoleList.size() - 1);
        assertThat(testPeopleRole.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
    }

    @Test
    @Transactional
    public void createPeopleRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRoleRepository.findAll().size();

        // Create the PeopleRole with an existing ID
        peopleRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleRoleMockMvc.perform(post("/api/people-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleRole)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PeopleRole> peopleRoleList = peopleRoleRepository.findAll();
        assertThat(peopleRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeopleRoles() throws Exception {
        // Initialize the database
        peopleRoleRepository.saveAndFlush(peopleRole);

        // Get all the peopleRoleList
        restPeopleRoleMockMvc.perform(get("/api/people-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peopleRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPeopleRole() throws Exception {
        // Initialize the database
        peopleRoleRepository.saveAndFlush(peopleRole);

        // Get the peopleRole
        restPeopleRoleMockMvc.perform(get("/api/people-roles/{id}", peopleRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(peopleRole.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeopleRole() throws Exception {
        // Get the peopleRole
        restPeopleRoleMockMvc.perform(get("/api/people-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeopleRole() throws Exception {
        // Initialize the database
        peopleRoleService.save(peopleRole);

        int databaseSizeBeforeUpdate = peopleRoleRepository.findAll().size();

        // Update the peopleRole
        PeopleRole updatedPeopleRole = peopleRoleRepository.findOne(peopleRole.getId());
        updatedPeopleRole
            .roleName(UPDATED_ROLE_NAME);

        restPeopleRoleMockMvc.perform(put("/api/people-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeopleRole)))
            .andExpect(status().isOk());

        // Validate the PeopleRole in the database
        List<PeopleRole> peopleRoleList = peopleRoleRepository.findAll();
        assertThat(peopleRoleList).hasSize(databaseSizeBeforeUpdate);
        PeopleRole testPeopleRole = peopleRoleList.get(peopleRoleList.size() - 1);
        assertThat(testPeopleRole.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPeopleRole() throws Exception {
        int databaseSizeBeforeUpdate = peopleRoleRepository.findAll().size();

        // Create the PeopleRole

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeopleRoleMockMvc.perform(put("/api/people-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peopleRole)))
            .andExpect(status().isCreated());

        // Validate the PeopleRole in the database
        List<PeopleRole> peopleRoleList = peopleRoleRepository.findAll();
        assertThat(peopleRoleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeopleRole() throws Exception {
        // Initialize the database
        peopleRoleService.save(peopleRole);

        int databaseSizeBeforeDelete = peopleRoleRepository.findAll().size();

        // Get the peopleRole
        restPeopleRoleMockMvc.perform(delete("/api/people-roles/{id}", peopleRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PeopleRole> peopleRoleList = peopleRoleRepository.findAll();
        assertThat(peopleRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeopleRole.class);
        PeopleRole peopleRole1 = new PeopleRole();
        peopleRole1.setId(1L);
        PeopleRole peopleRole2 = new PeopleRole();
        peopleRole2.setId(peopleRole1.getId());
        assertThat(peopleRole1).isEqualTo(peopleRole2);
        peopleRole2.setId(2L);
        assertThat(peopleRole1).isNotEqualTo(peopleRole2);
        peopleRole1.setId(null);
        assertThat(peopleRole1).isNotEqualTo(peopleRole2);
    }
}
