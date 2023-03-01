package com.rps.flora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rps.flora.IntegrationTest;
import com.rps.flora.domain.FloraEmployee;
import com.rps.flora.domain.User;
import com.rps.flora.repository.FloraEmployeeRepository;
import com.rps.flora.service.criteria.FloraEmployeeCriteria;
import com.rps.flora.service.dto.FloraEmployeeDTO;
import com.rps.flora.service.mapper.FloraEmployeeMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FloraEmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FloraEmployeeResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATT_START_TIME_1 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATT_START_TIME_1 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATT_END_TIME_1 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATT_END_TIME_1 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATT_START_TIME_2 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATT_START_TIME_2 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/flora-employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FloraEmployeeRepository floraEmployeeRepository;

    @Autowired
    private FloraEmployeeMapper floraEmployeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFloraEmployeeMockMvc;

    private FloraEmployee floraEmployee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FloraEmployee createEntity(EntityManager em) {
        FloraEmployee floraEmployee = new FloraEmployee()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .attStartTime1(DEFAULT_ATT_START_TIME_1)
            .attEndTime1(DEFAULT_ATT_END_TIME_1)
            .attStartTime2(DEFAULT_ATT_START_TIME_2);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        floraEmployee.setInternalUser(user);
        return floraEmployee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FloraEmployee createUpdatedEntity(EntityManager em) {
        FloraEmployee floraEmployee = new FloraEmployee()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .attStartTime1(UPDATED_ATT_START_TIME_1)
            .attEndTime1(UPDATED_ATT_END_TIME_1)
            .attStartTime2(UPDATED_ATT_START_TIME_2);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        floraEmployee.setInternalUser(user);
        return floraEmployee;
    }

    @BeforeEach
    public void initTest() {
        floraEmployee = createEntity(em);
    }

    @Test
    @Transactional
    void createFloraEmployee() throws Exception {
        int databaseSizeBeforeCreate = floraEmployeeRepository.findAll().size();
        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);
        restFloraEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        FloraEmployee testFloraEmployee = floraEmployeeList.get(floraEmployeeList.size() - 1);
        assertThat(testFloraEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFloraEmployee.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFloraEmployee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFloraEmployee.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testFloraEmployee.getAttStartTime1()).isEqualTo(DEFAULT_ATT_START_TIME_1);
        assertThat(testFloraEmployee.getAttEndTime1()).isEqualTo(DEFAULT_ATT_END_TIME_1);
        assertThat(testFloraEmployee.getAttStartTime2()).isEqualTo(DEFAULT_ATT_START_TIME_2);
    }

    @Test
    @Transactional
    void createFloraEmployeeWithExistingId() throws Exception {
        // Create the FloraEmployee with an existing ID
        floraEmployee.setId(1L);
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        int databaseSizeBeforeCreate = floraEmployeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloraEmployeeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFloraEmployees() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList
        restFloraEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floraEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].attStartTime1").value(hasItem(DEFAULT_ATT_START_TIME_1.toString())))
            .andExpect(jsonPath("$.[*].attEndTime1").value(hasItem(DEFAULT_ATT_END_TIME_1.toString())))
            .andExpect(jsonPath("$.[*].attStartTime2").value(hasItem(DEFAULT_ATT_START_TIME_2.toString())));
    }

    @Test
    @Transactional
    void getFloraEmployee() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get the floraEmployee
        restFloraEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, floraEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(floraEmployee.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.attStartTime1").value(DEFAULT_ATT_START_TIME_1.toString()))
            .andExpect(jsonPath("$.attEndTime1").value(DEFAULT_ATT_END_TIME_1.toString()))
            .andExpect(jsonPath("$.attStartTime2").value(DEFAULT_ATT_START_TIME_2.toString()));
    }

    @Test
    @Transactional
    void getFloraEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        Long id = floraEmployee.getId();

        defaultFloraEmployeeShouldBeFound("id.equals=" + id);
        defaultFloraEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultFloraEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFloraEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultFloraEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFloraEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdBy equals to DEFAULT_CREATED_BY
        defaultFloraEmployeeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the floraEmployeeList where createdBy equals to UPDATED_CREATED_BY
        defaultFloraEmployeeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultFloraEmployeeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the floraEmployeeList where createdBy equals to UPDATED_CREATED_BY
        defaultFloraEmployeeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdBy is not null
        defaultFloraEmployeeShouldBeFound("createdBy.specified=true");

        // Get all the floraEmployeeList where createdBy is null
        defaultFloraEmployeeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdBy contains DEFAULT_CREATED_BY
        defaultFloraEmployeeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the floraEmployeeList where createdBy contains UPDATED_CREATED_BY
        defaultFloraEmployeeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultFloraEmployeeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the floraEmployeeList where createdBy does not contain UPDATED_CREATED_BY
        defaultFloraEmployeeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultFloraEmployeeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the floraEmployeeList where createdDate equals to UPDATED_CREATED_DATE
        defaultFloraEmployeeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultFloraEmployeeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the floraEmployeeList where createdDate equals to UPDATED_CREATED_DATE
        defaultFloraEmployeeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where createdDate is not null
        defaultFloraEmployeeShouldBeFound("createdDate.specified=true");

        // Get all the floraEmployeeList where createdDate is null
        defaultFloraEmployeeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the floraEmployeeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the floraEmployeeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedBy is not null
        defaultFloraEmployeeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the floraEmployeeList where lastModifiedBy is null
        defaultFloraEmployeeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the floraEmployeeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the floraEmployeeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultFloraEmployeeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultFloraEmployeeShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the floraEmployeeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultFloraEmployeeShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultFloraEmployeeShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the floraEmployeeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultFloraEmployeeShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where lastModifiedDate is not null
        defaultFloraEmployeeShouldBeFound("lastModifiedDate.specified=true");

        // Get all the floraEmployeeList where lastModifiedDate is null
        defaultFloraEmployeeShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttStartTime1IsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attStartTime1 equals to DEFAULT_ATT_START_TIME_1
        defaultFloraEmployeeShouldBeFound("attStartTime1.equals=" + DEFAULT_ATT_START_TIME_1);

        // Get all the floraEmployeeList where attStartTime1 equals to UPDATED_ATT_START_TIME_1
        defaultFloraEmployeeShouldNotBeFound("attStartTime1.equals=" + UPDATED_ATT_START_TIME_1);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttStartTime1IsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attStartTime1 in DEFAULT_ATT_START_TIME_1 or UPDATED_ATT_START_TIME_1
        defaultFloraEmployeeShouldBeFound("attStartTime1.in=" + DEFAULT_ATT_START_TIME_1 + "," + UPDATED_ATT_START_TIME_1);

        // Get all the floraEmployeeList where attStartTime1 equals to UPDATED_ATT_START_TIME_1
        defaultFloraEmployeeShouldNotBeFound("attStartTime1.in=" + UPDATED_ATT_START_TIME_1);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttStartTime1IsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attStartTime1 is not null
        defaultFloraEmployeeShouldBeFound("attStartTime1.specified=true");

        // Get all the floraEmployeeList where attStartTime1 is null
        defaultFloraEmployeeShouldNotBeFound("attStartTime1.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttEndTime1IsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attEndTime1 equals to DEFAULT_ATT_END_TIME_1
        defaultFloraEmployeeShouldBeFound("attEndTime1.equals=" + DEFAULT_ATT_END_TIME_1);

        // Get all the floraEmployeeList where attEndTime1 equals to UPDATED_ATT_END_TIME_1
        defaultFloraEmployeeShouldNotBeFound("attEndTime1.equals=" + UPDATED_ATT_END_TIME_1);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttEndTime1IsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attEndTime1 in DEFAULT_ATT_END_TIME_1 or UPDATED_ATT_END_TIME_1
        defaultFloraEmployeeShouldBeFound("attEndTime1.in=" + DEFAULT_ATT_END_TIME_1 + "," + UPDATED_ATT_END_TIME_1);

        // Get all the floraEmployeeList where attEndTime1 equals to UPDATED_ATT_END_TIME_1
        defaultFloraEmployeeShouldNotBeFound("attEndTime1.in=" + UPDATED_ATT_END_TIME_1);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttEndTime1IsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attEndTime1 is not null
        defaultFloraEmployeeShouldBeFound("attEndTime1.specified=true");

        // Get all the floraEmployeeList where attEndTime1 is null
        defaultFloraEmployeeShouldNotBeFound("attEndTime1.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttStartTime2IsEqualToSomething() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attStartTime2 equals to DEFAULT_ATT_START_TIME_2
        defaultFloraEmployeeShouldBeFound("attStartTime2.equals=" + DEFAULT_ATT_START_TIME_2);

        // Get all the floraEmployeeList where attStartTime2 equals to UPDATED_ATT_START_TIME_2
        defaultFloraEmployeeShouldNotBeFound("attStartTime2.equals=" + UPDATED_ATT_START_TIME_2);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttStartTime2IsInShouldWork() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attStartTime2 in DEFAULT_ATT_START_TIME_2 or UPDATED_ATT_START_TIME_2
        defaultFloraEmployeeShouldBeFound("attStartTime2.in=" + DEFAULT_ATT_START_TIME_2 + "," + UPDATED_ATT_START_TIME_2);

        // Get all the floraEmployeeList where attStartTime2 equals to UPDATED_ATT_START_TIME_2
        defaultFloraEmployeeShouldNotBeFound("attStartTime2.in=" + UPDATED_ATT_START_TIME_2);
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByAttStartTime2IsNullOrNotNull() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        // Get all the floraEmployeeList where attStartTime2 is not null
        defaultFloraEmployeeShouldBeFound("attStartTime2.specified=true");

        // Get all the floraEmployeeList where attStartTime2 is null
        defaultFloraEmployeeShouldNotBeFound("attStartTime2.specified=false");
    }

    @Test
    @Transactional
    void getAllFloraEmployeesByInternalUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User internalUser = floraEmployee.getInternalUser();
        floraEmployeeRepository.saveAndFlush(floraEmployee);
        Long internalUserId = internalUser.getId();

        // Get all the floraEmployeeList where internalUser equals to internalUserId
        defaultFloraEmployeeShouldBeFound("internalUserId.equals=" + internalUserId);

        // Get all the floraEmployeeList where internalUser equals to (internalUserId + 1)
        defaultFloraEmployeeShouldNotBeFound("internalUserId.equals=" + (internalUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFloraEmployeeShouldBeFound(String filter) throws Exception {
        restFloraEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floraEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].attStartTime1").value(hasItem(DEFAULT_ATT_START_TIME_1.toString())))
            .andExpect(jsonPath("$.[*].attEndTime1").value(hasItem(DEFAULT_ATT_END_TIME_1.toString())))
            .andExpect(jsonPath("$.[*].attStartTime2").value(hasItem(DEFAULT_ATT_START_TIME_2.toString())));

        // Check, that the count call also returns 1
        restFloraEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFloraEmployeeShouldNotBeFound(String filter) throws Exception {
        restFloraEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFloraEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFloraEmployee() throws Exception {
        // Get the floraEmployee
        restFloraEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFloraEmployee() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();

        // Update the floraEmployee
        FloraEmployee updatedFloraEmployee = floraEmployeeRepository.findById(floraEmployee.getId()).get();
        // Disconnect from session so that the updates on updatedFloraEmployee are not directly saved in db
        em.detach(updatedFloraEmployee);
        updatedFloraEmployee
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .attStartTime1(UPDATED_ATT_START_TIME_1)
            .attEndTime1(UPDATED_ATT_END_TIME_1)
            .attStartTime2(UPDATED_ATT_START_TIME_2);
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(updatedFloraEmployee);

        restFloraEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floraEmployeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
        FloraEmployee testFloraEmployee = floraEmployeeList.get(floraEmployeeList.size() - 1);
        assertThat(testFloraEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFloraEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFloraEmployee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFloraEmployee.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testFloraEmployee.getAttStartTime1()).isEqualTo(UPDATED_ATT_START_TIME_1);
        assertThat(testFloraEmployee.getAttEndTime1()).isEqualTo(UPDATED_ATT_END_TIME_1);
        assertThat(testFloraEmployee.getAttStartTime2()).isEqualTo(UPDATED_ATT_START_TIME_2);
    }

    @Test
    @Transactional
    void putNonExistingFloraEmployee() throws Exception {
        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();
        floraEmployee.setId(count.incrementAndGet());

        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloraEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floraEmployeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFloraEmployee() throws Exception {
        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();
        floraEmployee.setId(count.incrementAndGet());

        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloraEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFloraEmployee() throws Exception {
        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();
        floraEmployee.setId(count.incrementAndGet());

        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloraEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFloraEmployeeWithPatch() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();

        // Update the floraEmployee using partial update
        FloraEmployee partialUpdatedFloraEmployee = new FloraEmployee();
        partialUpdatedFloraEmployee.setId(floraEmployee.getId());

        partialUpdatedFloraEmployee.createdBy(UPDATED_CREATED_BY).attStartTime2(UPDATED_ATT_START_TIME_2);

        restFloraEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloraEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloraEmployee))
            )
            .andExpect(status().isOk());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
        FloraEmployee testFloraEmployee = floraEmployeeList.get(floraEmployeeList.size() - 1);
        assertThat(testFloraEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFloraEmployee.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFloraEmployee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testFloraEmployee.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testFloraEmployee.getAttStartTime1()).isEqualTo(DEFAULT_ATT_START_TIME_1);
        assertThat(testFloraEmployee.getAttEndTime1()).isEqualTo(DEFAULT_ATT_END_TIME_1);
        assertThat(testFloraEmployee.getAttStartTime2()).isEqualTo(UPDATED_ATT_START_TIME_2);
    }

    @Test
    @Transactional
    void fullUpdateFloraEmployeeWithPatch() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();

        // Update the floraEmployee using partial update
        FloraEmployee partialUpdatedFloraEmployee = new FloraEmployee();
        partialUpdatedFloraEmployee.setId(floraEmployee.getId());

        partialUpdatedFloraEmployee
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .attStartTime1(UPDATED_ATT_START_TIME_1)
            .attEndTime1(UPDATED_ATT_END_TIME_1)
            .attStartTime2(UPDATED_ATT_START_TIME_2);

        restFloraEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloraEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloraEmployee))
            )
            .andExpect(status().isOk());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
        FloraEmployee testFloraEmployee = floraEmployeeList.get(floraEmployeeList.size() - 1);
        assertThat(testFloraEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFloraEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFloraEmployee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testFloraEmployee.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testFloraEmployee.getAttStartTime1()).isEqualTo(UPDATED_ATT_START_TIME_1);
        assertThat(testFloraEmployee.getAttEndTime1()).isEqualTo(UPDATED_ATT_END_TIME_1);
        assertThat(testFloraEmployee.getAttStartTime2()).isEqualTo(UPDATED_ATT_START_TIME_2);
    }

    @Test
    @Transactional
    void patchNonExistingFloraEmployee() throws Exception {
        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();
        floraEmployee.setId(count.incrementAndGet());

        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloraEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, floraEmployeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFloraEmployee() throws Exception {
        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();
        floraEmployee.setId(count.incrementAndGet());

        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloraEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFloraEmployee() throws Exception {
        int databaseSizeBeforeUpdate = floraEmployeeRepository.findAll().size();
        floraEmployee.setId(count.incrementAndGet());

        // Create the FloraEmployee
        FloraEmployeeDTO floraEmployeeDTO = floraEmployeeMapper.toDto(floraEmployee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloraEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floraEmployeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FloraEmployee in the database
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFloraEmployee() throws Exception {
        // Initialize the database
        floraEmployeeRepository.saveAndFlush(floraEmployee);

        int databaseSizeBeforeDelete = floraEmployeeRepository.findAll().size();

        // Delete the floraEmployee
        restFloraEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, floraEmployee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FloraEmployee> floraEmployeeList = floraEmployeeRepository.findAll();
        assertThat(floraEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
