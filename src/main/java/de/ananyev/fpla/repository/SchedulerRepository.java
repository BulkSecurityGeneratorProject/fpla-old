package de.ananyev.fpla.repository;

import de.ananyev.fpla.domain.Scheduler;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Scheduler entity.
 */
@SuppressWarnings("unused")
public interface SchedulerRepository extends JpaRepository<Scheduler,Long> {

}
