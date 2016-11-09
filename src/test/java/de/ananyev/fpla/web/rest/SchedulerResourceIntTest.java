package de.ananyev.fpla.web.rest;

import de.ananyev.fpla.FplaApp;

import de.ananyev.fpla.domain.Scheduler;
import de.ananyev.fpla.repository.SchedulerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchedulerResource REST controller.
 *
 * @see SchedulerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FplaApp.class)
public class SchedulerResourceIntTest {

    private static final Boolean DEFAULT_RUNNING = false;
    private static final Boolean UPDATED_RUNNING = true;

    @Inject
    private SchedulerRepository schedulerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSchedulerMockMvc;

    private Scheduler scheduler;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchedulerResource schedulerResource = new SchedulerResource();
        ReflectionTestUtils.setField(schedulerResource, "schedulerRepository", schedulerRepository);
        this.restSchedulerMockMvc = MockMvcBuilders.standaloneSetup(schedulerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheduler createEntity(EntityManager em) {
        Scheduler scheduler = new Scheduler()
                .running(DEFAULT_RUNNING);
        return scheduler;
    }

    @Before
    public void initTest() {
        scheduler = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduler() throws Exception {
        int databaseSizeBeforeCreate = schedulerRepository.findAll().size();

        // Create the Scheduler

        restSchedulerMockMvc.perform(post("/api/schedulers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scheduler)))
                .andExpect(status().isCreated());

        // Validate the Scheduler in the database
        List<Scheduler> schedulers = schedulerRepository.findAll();
        assertThat(schedulers).hasSize(databaseSizeBeforeCreate + 1);
        Scheduler testScheduler = schedulers.get(schedulers.size() - 1);
        assertThat(testScheduler.isRunning()).isEqualTo(DEFAULT_RUNNING);
    }

    @Test
    @Transactional
    public void getAllSchedulers() throws Exception {
        // Initialize the database
        schedulerRepository.saveAndFlush(scheduler);

        // Get all the schedulers
        restSchedulerMockMvc.perform(get("/api/schedulers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(scheduler.getId().intValue())))
                .andExpect(jsonPath("$.[*].running").value(hasItem(DEFAULT_RUNNING.booleanValue())));
    }

    @Test
    @Transactional
    public void getScheduler() throws Exception {
        // Initialize the database
        schedulerRepository.saveAndFlush(scheduler);

        // Get the scheduler
        restSchedulerMockMvc.perform(get("/api/schedulers/{id}", scheduler.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheduler.getId().intValue()))
            .andExpect(jsonPath("$.running").value(DEFAULT_RUNNING.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingScheduler() throws Exception {
        // Get the scheduler
        restSchedulerMockMvc.perform(get("/api/schedulers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduler() throws Exception {
        // Initialize the database
        schedulerRepository.saveAndFlush(scheduler);
        int databaseSizeBeforeUpdate = schedulerRepository.findAll().size();

        // Update the scheduler
        Scheduler updatedScheduler = schedulerRepository.findOne(scheduler.getId());
        updatedScheduler
                .running(UPDATED_RUNNING);

        restSchedulerMockMvc.perform(put("/api/schedulers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedScheduler)))
                .andExpect(status().isOk());

        // Validate the Scheduler in the database
        List<Scheduler> schedulers = schedulerRepository.findAll();
        assertThat(schedulers).hasSize(databaseSizeBeforeUpdate);
        Scheduler testScheduler = schedulers.get(schedulers.size() - 1);
        assertThat(testScheduler.isRunning()).isEqualTo(UPDATED_RUNNING);
    }

    @Test
    @Transactional
    public void deleteScheduler() throws Exception {
        // Initialize the database
        schedulerRepository.saveAndFlush(scheduler);
        int databaseSizeBeforeDelete = schedulerRepository.findAll().size();

        // Get the scheduler
        restSchedulerMockMvc.perform(delete("/api/schedulers/{id}", scheduler.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Scheduler> schedulers = schedulerRepository.findAll();
        assertThat(schedulers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
