package de.ananyev.fpla.web.rest;

import de.ananyev.fpla.FplaApp;

import de.ananyev.fpla.domain.Schedule;
import de.ananyev.fpla.domain.enumeration.ScenarioEnum;
import de.ananyev.fpla.repository.ScheduleRepository;

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
 * Test class for the ScheduleResource REST controller.
 *
 * @see ScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FplaApp.class)
public class ScheduleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_CRON_STRING = "AAAAA";
    private static final String UPDATED_CRON_STRING = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final ScenarioEnum DEFAULT_SCENARIO_ENUM = ScenarioEnum.testScenario;
    private static final ScenarioEnum UPDATED_SCENARIO_ENUM = ScenarioEnum.checkIpScenario;

    @Inject
    private ScheduleRepository scheduleRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restScheduleMockMvc;

    private Schedule schedule;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScheduleResource scheduleResource = new ScheduleResource();
        ReflectionTestUtils.setField(scheduleResource, "scheduleRepository", scheduleRepository);
        this.restScheduleMockMvc = MockMvcBuilders.standaloneSetup(scheduleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createEntity(EntityManager em) {
        Schedule schedule = new Schedule()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .cronString(DEFAULT_CRON_STRING)
                .active(DEFAULT_ACTIVE)
                .scenario(DEFAULT_SCENARIO_ENUM);
        return schedule;
    }

    @Before
    public void initTest() {
        schedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedule() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule

        restScheduleMockMvc.perform(post("/api/schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schedule)))
                .andExpect(status().isCreated());

        // Validate the Schedule in the database
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(databaseSizeBeforeCreate + 1);
        Schedule testSchedule = schedules.get(schedules.size() - 1);
        assertThat(testSchedule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchedule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSchedule.getCronString()).isEqualTo(DEFAULT_CRON_STRING);
        assertThat(testSchedule.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testSchedule.getScenario()).isEqualTo(DEFAULT_SCENARIO_ENUM);
    }

    @Test
    @Transactional
    public void checkCronStringIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleRepository.findAll().size();
        // set the field null
        schedule.setCronString(null);

        // Create the Schedule, which fails.

        restScheduleMockMvc.perform(post("/api/schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schedule)))
                .andExpect(status().isBadRequest());

        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchedules() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the schedules
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cronString").value(hasItem(DEFAULT_CRON_STRING.toString())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].scenario").value(hasItem(DEFAULT_SCENARIO_ENUM.toString())));
    }

    @Test
    @Transactional
    public void getSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schedule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cronString").value(DEFAULT_CRON_STRING.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.scenario").value(DEFAULT_SCENARIO_ENUM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchedule() throws Exception {
        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);
        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Update the schedule
        Schedule updatedSchedule = scheduleRepository.findOne(schedule.getId());
        updatedSchedule
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .cronString(UPDATED_CRON_STRING)
                .active(UPDATED_ACTIVE)
                .scenario(UPDATED_SCENARIO_ENUM);

        restScheduleMockMvc.perform(put("/api/schedules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSchedule)))
                .andExpect(status().isOk());

        // Validate the Schedule in the database
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(databaseSizeBeforeUpdate);
        Schedule testSchedule = schedules.get(schedules.size() - 1);
        assertThat(testSchedule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchedule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSchedule.getCronString()).isEqualTo(UPDATED_CRON_STRING);
        assertThat(testSchedule.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testSchedule.getScenario()).isEqualTo(UPDATED_SCENARIO_ENUM);
    }

    @Test
    @Transactional
    public void deleteSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);
        int databaseSizeBeforeDelete = scheduleRepository.findAll().size();

        // Get the schedule
        restScheduleMockMvc.perform(delete("/api/schedules/{id}", schedule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Schedule> schedules = scheduleRepository.findAll();
        assertThat(schedules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
