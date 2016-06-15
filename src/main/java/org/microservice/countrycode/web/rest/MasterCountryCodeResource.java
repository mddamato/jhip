package org.microservice.countrycode.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.microservice.countrycode.domain.MasterCountryCode;
import org.microservice.countrycode.service.MasterCountryCodeService;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MasterCountryCode.
 */
@RestController
@RequestMapping("/api")
public class MasterCountryCodeResource {

    private final Logger log = LoggerFactory.getLogger(MasterCountryCodeResource.class);
        
    @Inject
    private MasterCountryCodeService masterCountryCodeService;
    
    /**
     * POST  /master-country-codes : Create a new masterCountryCode.
     *
     * @param masterCountryCode the masterCountryCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new masterCountryCode, or with status 400 (Bad Request) if the masterCountryCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/master-country-codes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MasterCountryCode> createMasterCountryCode(@RequestBody MasterCountryCode masterCountryCode) throws URISyntaxException {
        log.debug("REST request to save MasterCountryCode : {}", masterCountryCode);
        if (masterCountryCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("masterCountryCode", "idexists", "A new masterCountryCode cannot already have an ID")).body(null);
        }
        MasterCountryCode result = masterCountryCodeService.save(masterCountryCode);
        return ResponseEntity.created(new URI("/api/master-country-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("masterCountryCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /master-country-codes : Updates an existing masterCountryCode.
     *
     * @param masterCountryCode the masterCountryCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated masterCountryCode,
     * or with status 400 (Bad Request) if the masterCountryCode is not valid,
     * or with status 500 (Internal Server Error) if the masterCountryCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/master-country-codes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MasterCountryCode> updateMasterCountryCode(@RequestBody MasterCountryCode masterCountryCode) throws URISyntaxException {
        log.debug("REST request to update MasterCountryCode : {}", masterCountryCode);
        if (masterCountryCode.getId() == null) {
            return createMasterCountryCode(masterCountryCode);
        }
        MasterCountryCode result = masterCountryCodeService.save(masterCountryCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("masterCountryCode", masterCountryCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /master-country-codes : get all the masterCountryCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of masterCountryCodes in body
     */
    @RequestMapping(value = "/master-country-codes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MasterCountryCode> getAllMasterCountryCodes() {
        log.debug("REST request to get all MasterCountryCodes");
        return masterCountryCodeService.findAll();
    }

    /**
     * GET  /master-country-codes/:id : get the "id" masterCountryCode.
     *
     * @param id the id of the masterCountryCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the masterCountryCode, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/master-country-codes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MasterCountryCode> getMasterCountryCode(@PathVariable Long id) {
        log.debug("REST request to get MasterCountryCode : {}", id);
        MasterCountryCode masterCountryCode = masterCountryCodeService.findOne(id);
        return Optional.ofNullable(masterCountryCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /master-country-codes/:id : delete the "id" masterCountryCode.
     *
     * @param id the id of the masterCountryCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/master-country-codes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMasterCountryCode(@PathVariable Long id) {
        log.debug("REST request to delete MasterCountryCode : {}", id);
        masterCountryCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("masterCountryCode", id.toString())).build();
    }

}
