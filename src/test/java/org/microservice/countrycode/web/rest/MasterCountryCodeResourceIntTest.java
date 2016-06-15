package org.microservice.countrycode.web.rest;

import org.microservice.countrycode.CountryCodeLookupApp;
import org.microservice.countrycode.domain.MasterCountryCode;
import org.microservice.countrycode.repository.MasterCountryCodeRepository;
import org.microservice.countrycode.service.MasterCountryCodeService;

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
 * Test class for the MasterCountryCodeResource REST controller.
 *
 * @see MasterCountryCodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CountryCodeLookupApp.class)
@WebAppConfiguration
@IntegrationTest
public class MasterCountryCodeResourceIntTest {

    private static final String DEFAULT_MASTER_COUNTRY_CODE = "AAAAA";
    private static final String UPDATED_MASTER_COUNTRY_CODE = "BBBBB";
    private static final String DEFAULT_MASTER_COUNTRY_NAME = "AAAAA";
    private static final String UPDATED_MASTER_COUNTRY_NAME = "BBBBB";

    @Inject
    private MasterCountryCodeRepository masterCountryCodeRepository;

    @Inject
    private MasterCountryCodeService masterCountryCodeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMasterCountryCodeMockMvc;

    private MasterCountryCode masterCountryCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MasterCountryCodeResource masterCountryCodeResource = new MasterCountryCodeResource();
        ReflectionTestUtils.setField(masterCountryCodeResource, "masterCountryCodeService", masterCountryCodeService);
        this.restMasterCountryCodeMockMvc = MockMvcBuilders.standaloneSetup(masterCountryCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        masterCountryCode = new MasterCountryCode();
        masterCountryCode.setMasterCountryCode(DEFAULT_MASTER_COUNTRY_CODE);
        masterCountryCode.setMasterCountryName(DEFAULT_MASTER_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void createMasterCountryCode() throws Exception {
        int databaseSizeBeforeCreate = masterCountryCodeRepository.findAll().size();

        // Create the MasterCountryCode

        restMasterCountryCodeMockMvc.perform(post("/api/master-country-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(masterCountryCode)))
                .andExpect(status().isCreated());

        // Validate the MasterCountryCode in the database
        List<MasterCountryCode> masterCountryCodes = masterCountryCodeRepository.findAll();
        assertThat(masterCountryCodes).hasSize(databaseSizeBeforeCreate + 1);
        MasterCountryCode testMasterCountryCode = masterCountryCodes.get(masterCountryCodes.size() - 1);
        assertThat(testMasterCountryCode.getMasterCountryCode()).isEqualTo(DEFAULT_MASTER_COUNTRY_CODE);
        assertThat(testMasterCountryCode.getMasterCountryName()).isEqualTo(DEFAULT_MASTER_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void getAllMasterCountryCodes() throws Exception {
        // Initialize the database
        masterCountryCodeRepository.saveAndFlush(masterCountryCode);

        // Get all the masterCountryCodes
        restMasterCountryCodeMockMvc.perform(get("/api/master-country-codes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(masterCountryCode.getId().intValue())))
                .andExpect(jsonPath("$.[*].masterCountryCode").value(hasItem(DEFAULT_MASTER_COUNTRY_CODE.toString())))
                .andExpect(jsonPath("$.[*].masterCountryName").value(hasItem(DEFAULT_MASTER_COUNTRY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getMasterCountryCode() throws Exception {
        // Initialize the database
        masterCountryCodeRepository.saveAndFlush(masterCountryCode);

        // Get the masterCountryCode
        restMasterCountryCodeMockMvc.perform(get("/api/master-country-codes/{id}", masterCountryCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(masterCountryCode.getId().intValue()))
            .andExpect(jsonPath("$.masterCountryCode").value(DEFAULT_MASTER_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.masterCountryName").value(DEFAULT_MASTER_COUNTRY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMasterCountryCode() throws Exception {
        // Get the masterCountryCode
        restMasterCountryCodeMockMvc.perform(get("/api/master-country-codes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMasterCountryCode() throws Exception {
        // Initialize the database
        masterCountryCodeService.save(masterCountryCode);

        int databaseSizeBeforeUpdate = masterCountryCodeRepository.findAll().size();

        // Update the masterCountryCode
        MasterCountryCode updatedMasterCountryCode = new MasterCountryCode();
        updatedMasterCountryCode.setId(masterCountryCode.getId());
        updatedMasterCountryCode.setMasterCountryCode(UPDATED_MASTER_COUNTRY_CODE);
        updatedMasterCountryCode.setMasterCountryName(UPDATED_MASTER_COUNTRY_NAME);

        restMasterCountryCodeMockMvc.perform(put("/api/master-country-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMasterCountryCode)))
                .andExpect(status().isOk());

        // Validate the MasterCountryCode in the database
        List<MasterCountryCode> masterCountryCodes = masterCountryCodeRepository.findAll();
        assertThat(masterCountryCodes).hasSize(databaseSizeBeforeUpdate);
        MasterCountryCode testMasterCountryCode = masterCountryCodes.get(masterCountryCodes.size() - 1);
        assertThat(testMasterCountryCode.getMasterCountryCode()).isEqualTo(UPDATED_MASTER_COUNTRY_CODE);
        assertThat(testMasterCountryCode.getMasterCountryName()).isEqualTo(UPDATED_MASTER_COUNTRY_NAME);
    }

    @Test
    @Transactional
    public void deleteMasterCountryCode() throws Exception {
        // Initialize the database
        masterCountryCodeService.save(masterCountryCode);

        int databaseSizeBeforeDelete = masterCountryCodeRepository.findAll().size();

        // Get the masterCountryCode
        restMasterCountryCodeMockMvc.perform(delete("/api/master-country-codes/{id}", masterCountryCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MasterCountryCode> masterCountryCodes = masterCountryCodeRepository.findAll();
        assertThat(masterCountryCodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
