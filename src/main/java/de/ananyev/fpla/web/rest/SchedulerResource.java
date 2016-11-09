package de.ananyev.fpla.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.ananyev.fpla.domain.Scheduler;

import de.ananyev.fpla.repository.SchedulerRepository;
import de.ananyev.fpla.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Scheduler.
 */
@RestController
@RequestMapping("/api")
public class SchedulerResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerResource.class);
        
    @Inject
    private SchedulerRepository schedulerRepository;

    /**
     * POST  /schedulers : Create a new scheduler.
     *
     * @param scheduler the scheduler to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduler, or with status 400 (Bad Request) if the scheduler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedulers")
    @Timed
    public ResponseEntity<Scheduler> createScheduler(@RequestBody Scheduler scheduler) throws URISyntaxException {
        log.debug("REST request to save Scheduler : {}", scheduler);
        if (scheduler.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("scheduler", "idexists", "A new scheduler cannot already have an ID")).body(null);
        }
        Scheduler result = schedulerRepository.save(scheduler);
        return ResponseEntity.created(new URI("/api/schedulers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("scheduler", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedulers : Updates an existing scheduler.
     *
     * @param scheduler the scheduler to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduler,
     * or with status 400 (Bad Request) if the scheduler is not valid,
     * or with status 500 (Internal Server Error) if the scheduler couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedulers")
    @Timed
    public ResponseEntity<Scheduler> updateScheduler(@RequestBody Scheduler scheduler) throws URISyntaxException {
        log.debug("REST request to update Scheduler : {}", scheduler);
        if (scheduler.getId() == null) {
            return createScheduler(scheduler);
        }
        Scheduler result = schedulerRepository.save(scheduler);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("scheduler", scheduler.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedulers : get all the schedulers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schedulers in body
     */
    @GetMapping("/schedulers")
    @Timed
    public List<Scheduler> getAllSchedulers() {
        log.debug("REST request to get all Schedulers");
        List<Scheduler> schedulers = schedulerRepository.findAll();
        return schedulers;
    }

    /**
     * GET  /schedulers/:id : get the "id" scheduler.
     *
     * @param id the id of the scheduler to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduler, or with status 404 (Not Found)
     */
    @GetMapping("/schedulers/{id}")
    @Timed
    public ResponseEntity<Scheduler> getScheduler(@PathVariable Long id) {
        log.debug("REST request to get Scheduler : {}", id);
        Scheduler scheduler = schedulerRepository.findOne(id);
        return Optional.ofNullable(scheduler)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /schedulers/:id : delete the "id" scheduler.
     *
     * @param id the id of the scheduler to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedulers/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheduler(@PathVariable Long id) {
        log.debug("REST request to delete Scheduler : {}", id);
        schedulerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("scheduler", id.toString())).build();
    }

}
