package org.microservice.countrycode.repository;

import org.microservice.countrycode.domain.PrivateCountryCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrivateCountryCode entity.
 */

@SuppressWarnings("unused")
public interface PrivateCountryCodeRepository extends JpaRepository<PrivateCountryCode,Long> {

    @Query("select privateCountryCode from PrivateCountryCode privateCountryCode where privateCountryCode.user.login = ?#{principal.username}")
    List<PrivateCountryCode> findByUserIsCurrentUser();

    @Query("select privateCountryCode from PrivateCountryCode privateCountryCode where privateCountryCode.user.login = ?#{[0]}")
    List<PrivateCountryCode> findByUserLoginIsEqualTo(String login);

}
