package de.ananyev.fpla.repository;

import de.ananyev.fpla.domain.UserAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

}
