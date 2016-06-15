package org.microservice.countrycode.web.rest;

import org.microservice.countrycode.CountryCodeLookupApp;
import org.microservice.countrycode.domain.PrivateCountryCode;
import org.microservice.countrycode.repository.PrivateCountryCodeRepository;
import org.microservice.countrycode.service.PrivateCountryCodeService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PrivateCountryCodeResource REST controller.
 *
 * @see PrivateCountryCodeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CountryCodeLookupApp.class)
@WebAppConfiguration
@IntegrationTest
public class PrivateCountryCodeResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);
    private static final String DEFAULT_COUNTRY_NAME = "AAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBB";
    private static final String DEFAULT_COUNTRY_CODE = "AAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBB";

    @Inject
    private PrivateCountryCodeRepository privateCountryCodeRepository;

    @Inject
    private PrivateCountryCodeService privateCountryCodeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrivateCountryCodeMockMvc;

    private PrivateCountryCode privateCountryCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrivateCountryCodeResource privateCountryCodeResource = new PrivateCountryCodeResource();
        ReflectionTestUtils.setField(privateCountryCodeResource, "privateCountryCodeService", privateCountryCodeService);
        this.restPrivateCountryCodeMockMvc = MockMvcBuilders.standaloneSetup(privateCountryCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        privateCountryCode = new PrivateCountryCode();
        privateCountryCode.setStartDate(DEFAULT_START_DATE);
        privateCountryCode.setEndDate(DEFAULT_END_DATE);
        privateCountryCode.setCountryName(DEFAULT_COUNTRY_NAME);
        privateCountryCode.setCountryCode(DEFAULT_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void createPrivateCountryCode() throws Exception {
        int databaseSizeBeforeCreate = privateCountryCodeRepository.findAll().size();

        // Create the PrivateCountryCode

        restPrivateCountryCodeMockMvc.perform(post("/api/private-country-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(privateCountryCode)))
                .andExpect(status().isCreated());

        // Validate the PrivateCountryCode in the database
        List<PrivateCountryCode> privateCountryCodes = privateCountryCodeRepository.findAll();
        assertThat(privateCountryCodes).hasSize(databaseSizeBeforeCreate + 1);
        PrivateCountryCode testPrivateCountryCode = privateCountryCodes.get(privateCountryCodes.size() - 1);
        assertThat(testPrivateCountryCode.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPrivateCountryCode.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPrivateCountryCode.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testPrivateCountryCode.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void getAllPrivateCountryCodes() throws Exception {
        // Initialize the database
        privateCountryCodeRepository.saveAndFlush(privateCountryCode);

        // Get all the privateCountryCodes
        restPrivateCountryCodeMockMvc.perform(get("/api/private-country-codes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(privateCountryCode.getId().intValue())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME.toString())))
                .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())));
    }

    @Test
    @Transactional
    public void getPrivateCountryCode() throws Exception {
        // Initialize the database
        privateCountryCodeRepository.saveAndFlush(privateCountryCode);

        // Get the privateCountryCode
        restPrivateCountryCodeMockMvc.perform(get("/api/private-country-codes/{id}", privateCountryCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(privateCountryCode.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrivateCountryCode() throws Exception {
        // Get the privateCountryCode
        restPrivateCountryCodeMockMvc.perform(get("/api/private-country-codes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrivateCountryCode() throws Exception {
        // Initialize the database
        privateCountryCodeService.save(privateCountryCode);

        int databaseSizeBeforeUpdate = privateCountryCodeRepository.findAll().size();

        // Update the privateCountryCode
        PrivateCountryCode updatedPrivateCountryCode = new PrivateCountryCode();
        updatedPrivateCountryCode.setId(privateCountryCode.getId());
        updatedPrivateCountryCode.setStartDate(UPDATED_START_DATE);
        updatedPrivateCountryCode.setEndDate(UPDATED_END_DATE);
        updatedPrivateCountryCode.setCountryName(UPDATED_COUNTRY_NAME);
        updatedPrivateCountryCode.setCountryCode(UPDATED_COUNTRY_CODE);

        restPrivateCountryCodeMockMvc.perform(put("/api/private-country-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPrivateCountryCode)))
                .andExpect(status().isOk());

        // Validate the PrivateCountryCode in the database
        List<PrivateCountryCode> privateCountryCodes = privateCountryCodeRepository.findAll();
        assertThat(privateCountryCodes).hasSize(databaseSizeBeforeUpdate);
        PrivateCountryCode testPrivateCountryCode = privateCountryCodes.get(privateCountryCodes.size() - 1);
        assertThat(testPrivateCountryCode.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPrivateCountryCode.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPrivateCountryCode.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testPrivateCountryCode.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    public void deletePrivateCountryCode() throws Exception {
        // Initialize the database
        privateCountryCodeService.save(privateCountryCode);

        int databaseSizeBeforeDelete = privateCountryCodeRepository.findAll().size();

        // Get the privateCountryCode
        restPrivateCountryCodeMockMvc.perform(delete("/api/private-country-codes/{id}", privateCountryCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PrivateCountryCode> privateCountryCodes = privateCountryCodeRepository.findAll();
        assertThat(privateCountryCodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
