package org.microservice.countrycode.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.microservice.countrycode.domain.LookUpCode;
import org.microservice.countrycode.domain.PrivateCountryCode;
import org.microservice.countrycode.repository.LookUpCodeRepository;
import org.microservice.countrycode.repository.PrivateCountryCodeRepository;
import org.microservice.countrycode.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LookUpCode.
 */
@RestController
@RequestMapping("/api")
public class LookUpCodeResource {

    private final Logger log = LoggerFactory.getLogger(LookUpCodeResource.class);

    @Inject
    private LookUpCodeRepository lookUpCodeRepository;
    @Inject
    private PrivateCountryCodeRepository privateCountryCodeRepository;

    /**
     * POST  /look-up-codes : Create a new lookUpCode.
     *
     * @param lookUpCode the lookUpCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lookUpCode, or with status 400 (Bad Request) if the lookUpCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/look-up-codes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookUpCode> createLookUpCode(@RequestBody LookUpCode lookUpCode) throws URISyntaxException {
        log.debug("REST request to save LookUpCode : {}", lookUpCode);
        if (lookUpCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lookUpCode", "idexists", "A new lookUpCode cannot already have an ID")).body(null);
        }
        LookUpCode result = lookUpCodeRepository.save(lookUpCode);
        return ResponseEntity.created(new URI("/api/look-up-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lookUpCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /look-up-codes : Updates an existing lookUpCode.
     *
     * @param lookUpCode the lookUpCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lookUpCode,
     * or with status 400 (Bad Request) if the lookUpCode is not valid,
     * or with status 500 (Internal Server Error) if the lookUpCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/look-up-codes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookUpCode> updateLookUpCode(@RequestBody LookUpCode lookUpCode) throws URISyntaxException {
        log.debug("REST request to update LookUpCode : {}", lookUpCode);
        if (lookUpCode.getId() == null) {
            return createLookUpCode(lookUpCode);
        }
        LookUpCode result = lookUpCodeRepository.save(lookUpCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lookUpCode", lookUpCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /look-up-codes : get all the lookUpCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lookUpCodes in body
     */
    @RequestMapping(value = "/look-up-codes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LookUpCode> getAllLookUpCodes() {
        log.debug("REST request to get all LookUpCodes");
        List<LookUpCode> lookUpCodes = lookUpCodeRepository.findAll();
        return lookUpCodes;
    }

    /**
     * GET  /look-up-codes/:id : get the "id" lookUpCode.
     *
     * @param id the id of the lookUpCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lookUpCode, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/look-up-codes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LookUpCode> getLookUpCode(@PathVariable Long id) {
        log.debug("REST request to get LookUpCode : {}", id);
        LookUpCode lookUpCode = lookUpCodeRepository.findOne(id);
        return Optional.ofNullable(lookUpCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * DELETE  /look-up-codes/:id : delete the "id" lookUpCode.
     *
     * @param id the id of the lookUpCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/look-up-codes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLookUpCode(@PathVariable Long id) {
        log.debug("REST request to delete LookUpCode : {}", id);
        lookUpCodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lookUpCode", id.toString())).build();
    }





    /**
     * GET  /privateCountryCodeList/:user_login : get the list of private codes for user
     *
     * @param user_login for the list of private country codes
     * @return the ResponseEntity with status 200 (OK) and with body the lookUpCode, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/privateCountryCodeList/{user_login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrivateCountryCode> getPrivateCountryCodesForUser(@PathVariable String user_login) {
        log.debug("REST REQUEST WAS MADE! to get country codes for user_login : {}", user_login);
        ArrayList<PrivateCountryCode> privateCountryCodeArrayList = new ArrayList<PrivateCountryCode>();
        return privateCountryCodeRepository.findByUserLoginIsEqualTo(user_login);
    }

}
