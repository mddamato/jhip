package org.microservice.countrycode.repository;

import org.microservice.countrycode.domain.LookUpCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LookUpCode entity.
 */
@SuppressWarnings("unused")
public interface LookUpCodeRepository extends JpaRepository<LookUpCode,Long> {

}
