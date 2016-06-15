package org.microservice.countrycode.service;

import org.microservice.countrycode.domain.MasterCountryCode;
import org.microservice.countrycode.repository.MasterCountryCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing MasterCountryCode.
 */
@Service
@Transactional
public class MasterCountryCodeService {

    private final Logger log = LoggerFactory.getLogger(MasterCountryCodeService.class);
    
    @Inject
    private MasterCountryCodeRepository masterCountryCodeRepository;
    
    /**
     * Save a masterCountryCode.
     * 
     * @param masterCountryCode the entity to save
     * @return the persisted entity
     */
    public MasterCountryCode save(MasterCountryCode masterCountryCode) {
        log.debug("Request to save MasterCountryCode : {}", masterCountryCode);
        MasterCountryCode result = masterCountryCodeRepository.save(masterCountryCode);
        return result;
    }

    /**
     *  Get all the masterCountryCodes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<MasterCountryCode> findAll() {
        log.debug("Request to get all MasterCountryCodes");
        List<MasterCountryCode> result = masterCountryCodeRepository.findAll();
        return result;
    }

    /**
     *  Get one masterCountryCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public MasterCountryCode findOne(Long id) {
        log.debug("Request to get MasterCountryCode : {}", id);
        MasterCountryCode masterCountryCode = masterCountryCodeRepository.findOne(id);
        return masterCountryCode;
    }

    /**
     *  Delete the  masterCountryCode by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MasterCountryCode : {}", id);
        masterCountryCodeRepository.delete(id);
    }
}
