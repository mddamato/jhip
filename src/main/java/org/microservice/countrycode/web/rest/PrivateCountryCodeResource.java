package org.microservice.countrycode.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.microservice.countrycode.domain.PrivateCountryCode;
import org.microservice.countrycode.service.PrivateCountryCodeService;
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
 * REST controller for managing PrivateCountryCode.
 */
@RestController
@RequestMapping("/api")
public class PrivateCountryCodeResource {

    private final Logger log = LoggerFactory.getLogger(PrivateCountryCodeResource.class);
        
    @Inject
    private PrivateCountryCodeService privateCountryCodeService;
    
    /**
     * POST  /private-country-codes : Create a new privateCountryCode.
     *
     * @param privateCountryCode the privateCountryCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new privateCountryCode, or with status 400 (Bad Request) if the privateCountryCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/private-country-codes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrivateCountryCode> createPrivateCountryCode(@RequestBody PrivateCountryCode privateCountryCode) throws URISyntaxException {
        log.debug("REST request to save PrivateCountryCode : {}", privateCountryCode);
        if (privateCountryCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("privateCountryCode", "idexists", "A new privateCountryCode cannot already have an ID")).body(null);
        }
        PrivateCountryCode result = privateCountryCodeService.save(privateCountryCode);
        return ResponseEntity.created(new URI("/api/private-country-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("privateCountryCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /private-country-codes : Updates an existing privateCountryCode.
     *
     * @param privateCountryCode the privateCountryCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated privateCountryCode,
     * or with status 400 (Bad Request) if the privateCountryCode is not valid,
     * or with status 500 (Internal Server Error) if the privateCountryCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/private-country-codes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrivateCountryCode> updatePrivateCountryCode(@RequestBody PrivateCountryCode privateCountryCode) throws URISyntaxException {
        log.debug("REST request to update PrivateCountryCode : {}", privateCountryCode);
        if (privateCountryCode.getId() == null) {
            return createPrivateCountryCode(privateCountryCode);
        }
        PrivateCountryCode result = privateCountryCodeService.save(privateCountryCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("privateCountryCode", privateCountryCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /private-country-codes : get all the privateCountryCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of privateCountryCodes in body
     */
    @RequestMapping(value = "/private-country-codes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrivateCountryCode> getAllPrivateCountryCodes() {
        log.debug("REST request to get all PrivateCountryCodes");
        return privateCountryCodeService.findAll();
    }

    /**
     * GET  /private-country-codes/:id : get the "id" privateCountryCode.
     *
     * @param id the id of the privateCountryCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the privateCountryCode, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/private-country-codes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrivateCountryCode> getPrivateCountryCode(@PathVariable Long id) {
        log.debug("REST request to get PrivateCountryCode : {}", id);
        PrivateCountryCode privateCountryCode = privateCountryCodeService.findOne(id);
        return Optional.ofNullable(privateCountryCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /private-country-codes/:id : delete the "id" privateCountryCode.
     *
     * @param id the id of the privateCountryCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/private-country-codes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrivateCountryCode(@PathVariable Long id) {
        log.debug("REST request to delete PrivateCountryCode : {}", id);
        privateCountryCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("privateCountryCode", id.toString())).build();
    }

}
