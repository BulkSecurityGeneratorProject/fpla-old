package de.ananyev.fpla.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.ananyev.fpla.domain.Schedule;

import de.ananyev.fpla.repository.ScheduleRepository;
import de.ananyev.fpla.scenario.ScenarioScheduler;
import de.ananyev.fpla.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Schedule.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    @Inject
    private ScheduleRepository scheduleRepository;

    @Inject
    private ScenarioScheduler scheduler;

    /**
     * POST  /schedules : Create a new schedule.
     *
     * @param schedule the schedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schedule, or with status 400 (Bad Request) if the schedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedules")
    @Timed
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", schedule);
        if (schedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("schedule", "idexists", "A new schedule cannot already have an ID")).body(null);
        }
        Schedule result = scheduleRepository.save(schedule);
        if(result.isActive()) {
            scheduler.addSchedule(result);
        }
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("schedule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedules : Updates an existing schedule.
     *
     * @param schedule the schedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schedule,
     * or with status 400 (Bad Request) if the schedule is not valid,
     * or with status 500 (Internal Server Error) if the schedule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedules")
    @Timed
    public ResponseEntity<Schedule> updateSchedule(@Valid @RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", schedule);
        if (schedule.getId() == null) {
            return createSchedule(schedule);
        }
        Schedule result = scheduleRepository.save(schedule);
        if (schedule.isActive()) {
            scheduler.stopSchedule(schedule);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("schedule", schedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedules : get all the schedules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @GetMapping("/schedules")
    @Timed
    public List<Schedule> getAllSchedules() {
        log.debug("REST request to get all Schedules");
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules;
    }

    /**
     * GET  /schedules/:id : get the "id" schedule.
     *
     * @param id the id of the schedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schedule, or with status 404 (Not Found)
     */
    @GetMapping("/schedules/{id}")
    @Timed
    public ResponseEntity<Schedule> getSchedule(@PathVariable Long id) {
        log.debug("REST request to get Schedule : {}", id);
        Schedule schedule = scheduleRepository.findOne(id);
        return Optional.ofNullable(schedule)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /schedules/:id : delete the "id" schedule.
     *
     * @param id the id of the schedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        log.debug("REST request to delete Schedule : {}", id);
        scheduleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("schedule", id.toString())).build();
    }

}
