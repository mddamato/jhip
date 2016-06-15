package org.microservice.countrycode.web.rest;

import org.microservice.countrycode.CountryCodeLookupApp;
import org.microservice.countrycode.domain.LookUpCode;
import org.microservice.countrycode.repository.LookUpCodeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LookUpCodeResource REST controller.
 *
 * @see LookUpCodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CountryCodeLookupApp.class)
@WebAppConfiguration
@IntegrationTest
public class LookUpCodeResourceIntTest {


    @Inject
    private LookUpCodeRepository lookUpCodeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLookUpCodeMockMvc;

    private LookUpCode lookUpCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LookUpCodeResource lookUpCodeResource = new LookUpCodeResource();
        ReflectionTestUtils.setField(lookUpCodeResource, "lookUpCodeRepository", lookUpCodeRepository);
        this.restLookUpCodeMockMvc = MockMvcBuilders.standaloneSetup(lookUpCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lookUpCode = new LookUpCode();
    }

    @Test
    @Transactional
    public void createLookUpCode() throws Exception {
        int databaseSizeBeforeCreate = lookUpCodeRepository.findAll().size();

        // Create the LookUpCode

        restLookUpCodeMockMvc.perform(post("/api/look-up-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lookUpCode)))
                .andExpect(status().isCreated());

        // Validate the LookUpCode in the database
        List<LookUpCode> lookUpCodes = lookUpCodeRepository.findAll();
        assertThat(lookUpCodes).hasSize(databaseSizeBeforeCreate + 1);
        LookUpCode testLookUpCode = lookUpCodes.get(lookUpCodes.size() - 1);
    }

    @Test
    @Transactional
    public void getAllLookUpCodes() throws Exception {
        // Initialize the database
        lookUpCodeRepository.saveAndFlush(lookUpCode);

        // Get all the lookUpCodes
        restLookUpCodeMockMvc.perform(get("/api/look-up-codes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lookUpCode.getId().intValue())));
    }

    @Test
    @Transactional
    public void getLookUpCode() throws Exception {
        // Initialize the database
        lookUpCodeRepository.saveAndFlush(lookUpCode);

        // Get the lookUpCode
        restLookUpCodeMockMvc.perform(get("/api/look-up-codes/{id}", lookUpCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lookUpCode.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLookUpCode() throws Exception {
        // Get the lookUpCode
        restLookUpCodeMockMvc.perform(get("/api/look-up-codes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLookUpCode() throws Exception {
        // Initialize the database
        lookUpCodeRepository.saveAndFlush(lookUpCode);
        int databaseSizeBeforeUpdate = lookUpCodeRepository.findAll().size();

        // Update the lookUpCode
        LookUpCode updatedLookUpCode = new LookUpCode();
        updatedLookUpCode.setId(lookUpCode.getId());

        restLookUpCodeMockMvc.perform(put("/api/look-up-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLookUpCode)))
                .andExpect(status().isOk());

        // Validate the LookUpCode in the database
        List<LookUpCode> lookUpCodes = lookUpCodeRepository.findAll();
        assertThat(lookUpCodes).hasSize(databaseSizeBeforeUpdate);
        LookUpCode testLookUpCode = lookUpCodes.get(lookUpCodes.size() - 1);
    }

    @Test
    @Transactional
    public void deleteLookUpCode() throws Exception {
        // Initialize the database
        lookUpCodeRepository.saveAndFlush(lookUpCode);
        int databaseSizeBeforeDelete = lookUpCodeRepository.findAll().size();

        // Get the lookUpCode
        restLookUpCodeMockMvc.perform(delete("/api/look-up-codes/{id}", lookUpCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LookUpCode> lookUpCodes = lookUpCodeRepository.findAll();
        assertThat(lookUpCodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
