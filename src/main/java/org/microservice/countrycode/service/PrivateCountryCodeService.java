package org.microservice.countrycode.service;

import org.microservice.countrycode.domain.PrivateCountryCode;
import org.microservice.countrycode.domain.User;
import org.microservice.countrycode.repository.PrivateCountryCodeRepository;
import org.microservice.countrycode.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PrivateCountryCode.
 */
@Service
@Transactional
public class PrivateCountryCodeService {

    private final Logger log = LoggerFactory.getLogger(PrivateCountryCodeService.class);

    @Inject
    private PrivateCountryCodeRepository privateCountryCodeRepository;
    @Inject
    private UserRepository userRepository;

    /**
     * Save a privateCountryCode.
     *
     * @param privateCountryCode the entity to save
     * @return the persisted entity
     */
    public PrivateCountryCode save(PrivateCountryCode privateCountryCode) {
        log.debug("Request to save PrivateCountryCode : {}", privateCountryCode);

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findOneByLogin(login).get();
        privateCountryCode.setUser(user);

        PrivateCountryCode result = privateCountryCodeRepository.save(privateCountryCode);
        return result;
    }

    /**
     *  Get all the privateCountryCodes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PrivateCountryCode> findAll() {
        log.debug("Request to get all PrivateCountryCodes");
        List<PrivateCountryCode> result = privateCountryCodeRepository.findByUserIsCurrentUser();
        return result;
    }

    /**
     *  Get one privateCountryCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PrivateCountryCode findOne(Long id) {
        log.debug("Request to get PrivateCountryCode : {}", id);
        PrivateCountryCode privateCountryCode = privateCountryCodeRepository.findOne(id);
        return privateCountryCode;
    }

    /**
     *  Delete the  privateCountryCode by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PrivateCountryCode : {}", id);
        privateCountryCodeRepository.delete(id);
    }
}
