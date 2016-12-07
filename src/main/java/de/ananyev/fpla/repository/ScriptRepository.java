package de.ananyev.fpla.repository;

import de.ananyev.fpla.domain.Script;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Script entity.
 */
@SuppressWarnings("unused")
public interface ScriptRepository extends JpaRepository<Script,Long> {

    List<Script> findByName(String name);
    Script findOneByName(String name);
}
