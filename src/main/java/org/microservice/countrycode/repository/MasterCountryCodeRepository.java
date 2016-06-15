package org.microservice.countrycode.repository;

import org.microservice.countrycode.domain.MasterCountryCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MasterCountryCode entity.
 */
@SuppressWarnings("unused")
public interface MasterCountryCodeRepository extends JpaRepository<MasterCountryCode,Long> {

}
