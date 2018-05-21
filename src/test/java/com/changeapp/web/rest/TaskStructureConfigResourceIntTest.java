package com.changeapp.web.rest;

import com.changeapp.ChangeAppServerApp;

import com.changeapp.domain.TaskStructureConfig;
import com.changeapp.repository.TaskStructureConfigRepository;
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
 * Test class for the TaskStructureConfigResource REST controller.
 *
 * @see TaskStructureConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChangeAppServerApp.class)
public class TaskStructureConfigResourceIntTest {

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;

    private static final String DEFAULT_SORT_IN_PARENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SORT_IN_PARENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUESTION_ANSWER_LIST_ID = 1;
    private static final Integer UPDATED_QUESTION_ANSWER_LIST_ID = 2;

    private static final Integer DEFAULT_PEOPLE_ROLE = 1;
    private static final Integer UPDATED_PEOPLE_ROLE = 2;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String DEFAULT_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTION = "BBBBBBBBBB";

    @Autowired
    private TaskStructureConfigRepository taskStructureConfigRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaskStructureConfigMockMvc;

    private TaskStructureConfig taskStructureConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaskStructureConfigResource taskStructureConfigResource = new TaskStructureConfigResource(taskStructureConfigRepository);
        this.restTaskStructureConfigMockMvc = MockMvcBuilders.standaloneSetup(taskStructureConfigResource)
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
    public static TaskStructureConfig createEntity(EntityManager em) {
        TaskStructureConfig taskStructureConfig = new TaskStructureConfig()
            .parentID(DEFAULT_PARENT_ID)
            .sortInParentID(DEFAULT_SORT_IN_PARENT_ID)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .questionType(DEFAULT_QUESTION_TYPE)
            .questionAnswerListID(DEFAULT_QUESTION_ANSWER_LIST_ID)
            .peopleRole(DEFAULT_PEOPLE_ROLE)
            .order(DEFAULT_ORDER)
            .instruction(DEFAULT_INSTRUCTION);
        return taskStructureConfig;
    }

    @Before
    public void initTest() {
        taskStructureConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskStructureConfig() throws Exception {
        int databaseSizeBeforeCreate = taskStructureConfigRepository.findAll().size();

        // Create the TaskStructureConfig
        restTaskStructureConfigMockMvc.perform(post("/api/task-structure-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskStructureConfig)))
            .andExpect(status().isCreated());

        // Validate the TaskStructureConfig in the database
        List<TaskStructureConfig> taskStructureConfigList = taskStructureConfigRepository.findAll();
        assertThat(taskStructureConfigList).hasSize(databaseSizeBeforeCreate + 1);
        TaskStructureConfig testTaskStructureConfig = taskStructureConfigList.get(taskStructureConfigList.size() - 1);
        assertThat(testTaskStructureConfig.getParentID()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testTaskStructureConfig.getSortInParentID()).isEqualTo(DEFAULT_SORT_IN_PARENT_ID);
        assertThat(testTaskStructureConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaskStructureConfig.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTaskStructureConfig.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
        assertThat(testTaskStructureConfig.getQuestionAnswerListID()).isEqualTo(DEFAULT_QUESTION_ANSWER_LIST_ID);
        assertThat(testTaskStructureConfig.getPeopleRole()).isEqualTo(DEFAULT_PEOPLE_ROLE);
        assertThat(testTaskStructureConfig.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testTaskStructureConfig.getInstruction()).isEqualTo(DEFAULT_INSTRUCTION);
    }

    @Test
    @Transactional
    public void createTaskStructureConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskStructureConfigRepository.findAll().size();

        // Create the TaskStructureConfig with an existing ID
        taskStructureConfig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskStructureConfigMockMvc.perform(post("/api/task-structure-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskStructureConfig)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaskStructureConfig> taskStructureConfigList = taskStructureConfigRepository.findAll();
        assertThat(taskStructureConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaskStructureConfigs() throws Exception {
        // Initialize the database
        taskStructureConfigRepository.saveAndFlush(taskStructureConfig);

        // Get all the taskStructureConfigList
        restTaskStructureConfigMockMvc.perform(get("/api/task-structure-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskStructureConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentID").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].sortInParentID").value(hasItem(DEFAULT_SORT_IN_PARENT_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].questionAnswerListID").value(hasItem(DEFAULT_QUESTION_ANSWER_LIST_ID)))
            .andExpect(jsonPath("$.[*].peopleRole").value(hasItem(DEFAULT_PEOPLE_ROLE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION.toString())));
    }

    @Test
    @Transactional
    public void getTaskStructureConfig() throws Exception {
        // Initialize the database
        taskStructureConfigRepository.saveAndFlush(taskStructureConfig);

        // Get the taskStructureConfig
        restTaskStructureConfigMockMvc.perform(get("/api/task-structure-configs/{id}", taskStructureConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taskStructureConfig.getId().intValue()))
            .andExpect(jsonPath("$.parentID").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.sortInParentID").value(DEFAULT_SORT_IN_PARENT_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.questionType").value(DEFAULT_QUESTION_TYPE.toString()))
            .andExpect(jsonPath("$.questionAnswerListID").value(DEFAULT_QUESTION_ANSWER_LIST_ID))
            .andExpect(jsonPath("$.peopleRole").value(DEFAULT_PEOPLE_ROLE))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.instruction").value(DEFAULT_INSTRUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaskStructureConfig() throws Exception {
        // Get the taskStructureConfig
        restTaskStructureConfigMockMvc.perform(get("/api/task-structure-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskStructureConfig() throws Exception {
        // Initialize the database
        taskStructureConfigRepository.saveAndFlush(taskStructureConfig);
        int databaseSizeBeforeUpdate = taskStructureConfigRepository.findAll().size();

        // Update the taskStructureConfig
        TaskStructureConfig updatedTaskStructureConfig = taskStructureConfigRepository.findOne(taskStructureConfig.getId());
        updatedTaskStructureConfig
            .parentID(UPDATED_PARENT_ID)
            .sortInParentID(UPDATED_SORT_IN_PARENT_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .questionType(UPDATED_QUESTION_TYPE)
            .questionAnswerListID(UPDATED_QUESTION_ANSWER_LIST_ID)
            .peopleRole(UPDATED_PEOPLE_ROLE)
            .order(UPDATED_ORDER)
            .instruction(UPDATED_INSTRUCTION);

        restTaskStructureConfigMockMvc.perform(put("/api/task-structure-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaskStructureConfig)))
            .andExpect(status().isOk());

        // Validate the TaskStructureConfig in the database
        List<TaskStructureConfig> taskStructureConfigList = taskStructureConfigRepository.findAll();
        assertThat(taskStructureConfigList).hasSize(databaseSizeBeforeUpdate);
        TaskStructureConfig testTaskStructureConfig = taskStructureConfigList.get(taskStructureConfigList.size() - 1);
        assertThat(testTaskStructureConfig.getParentID()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testTaskStructureConfig.getSortInParentID()).isEqualTo(UPDATED_SORT_IN_PARENT_ID);
        assertThat(testTaskStructureConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaskStructureConfig.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTaskStructureConfig.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testTaskStructureConfig.getQuestionAnswerListID()).isEqualTo(UPDATED_QUESTION_ANSWER_LIST_ID);
        assertThat(testTaskStructureConfig.getPeopleRole()).isEqualTo(UPDATED_PEOPLE_ROLE);
        assertThat(testTaskStructureConfig.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testTaskStructureConfig.getInstruction()).isEqualTo(UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskStructureConfig() throws Exception {
        int databaseSizeBeforeUpdate = taskStructureConfigRepository.findAll().size();

        // Create the TaskStructureConfig

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaskStructureConfigMockMvc.perform(put("/api/task-structure-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taskStructureConfig)))
            .andExpect(status().isCreated());

        // Validate the TaskStructureConfig in the database
        List<TaskStructureConfig> taskStructureConfigList = taskStructureConfigRepository.findAll();
        assertThat(taskStructureConfigList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaskStructureConfig() throws Exception {
        // Initialize the database
        taskStructureConfigRepository.saveAndFlush(taskStructureConfig);
        int databaseSizeBeforeDelete = taskStructureConfigRepository.findAll().size();

        // Get the taskStructureConfig
        restTaskStructureConfigMockMvc.perform(delete("/api/task-structure-configs/{id}", taskStructureConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaskStructureConfig> taskStructureConfigList = taskStructureConfigRepository.findAll();
        assertThat(taskStructureConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskStructureConfig.class);
        TaskStructureConfig taskStructureConfig1 = new TaskStructureConfig();
        taskStructureConfig1.setId(1L);
        TaskStructureConfig taskStructureConfig2 = new TaskStructureConfig();
        taskStructureConfig2.setId(taskStructureConfig1.getId());
        assertThat(taskStructureConfig1).isEqualTo(taskStructureConfig2);
        taskStructureConfig2.setId(2L);
        assertThat(taskStructureConfig1).isNotEqualTo(taskStructureConfig2);
        taskStructureConfig1.setId(null);
        assertThat(taskStructureConfig1).isNotEqualTo(taskStructureConfig2);
    }
}
