package com.rps.flora.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.rps.flora.IntegrationTest;
import com.rps.flora.domain.AttendanceEntry;
import com.rps.flora.repository.AttendanceEntryRepository;
import com.rps.flora.service.criteria.AttendanceEntryCriteria;
import com.rps.flora.service.dto.AttendanceEntryDTO;
import com.rps.flora.service.mapper.AttendanceEntryMapper;
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
 * Integration tests for the {@link AttendanceEntryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttendanceEntryResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_YEAR_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_MONTH = "BBBBBBBBBB";

    private static final Instant DEFAULT_ATT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATT_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ATT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATT_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/attendance-entries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttendanceEntryRepository attendanceEntryRepository;

    @Autowired
    private AttendanceEntryMapper attendanceEntryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceEntryMockMvc;

    private AttendanceEntry attendanceEntry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceEntry createEntity(EntityManager em) {
        AttendanceEntry attendanceEntry = new AttendanceEntry()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .yearMonth(DEFAULT_YEAR_MONTH)
            .attStart(DEFAULT_ATT_START)
            .attEnd(DEFAULT_ATT_END)
            .comment(DEFAULT_COMMENT);
        return attendanceEntry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceEntry createUpdatedEntity(EntityManager em) {
        AttendanceEntry attendanceEntry = new AttendanceEntry()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .yearMonth(UPDATED_YEAR_MONTH)
            .attStart(UPDATED_ATT_START)
            .attEnd(UPDATED_ATT_END)
            .comment(UPDATED_COMMENT);
        return attendanceEntry;
    }

    @BeforeEach
    public void initTest() {
        attendanceEntry = createEntity(em);
    }

    @Test
    @Transactional
    void createAttendanceEntry() throws Exception {
        int databaseSizeBeforeCreate = attendanceEntryRepository.findAll().size();
        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);
        restAttendanceEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceEntry testAttendanceEntry = attendanceEntryList.get(attendanceEntryList.size() - 1);
        assertThat(testAttendanceEntry.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttendanceEntry.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAttendanceEntry.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAttendanceEntry.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testAttendanceEntry.getYearMonth()).isEqualTo(DEFAULT_YEAR_MONTH);
        assertThat(testAttendanceEntry.getAttStart()).isEqualTo(DEFAULT_ATT_START);
        assertThat(testAttendanceEntry.getAttEnd()).isEqualTo(DEFAULT_ATT_END);
        assertThat(testAttendanceEntry.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void createAttendanceEntryWithExistingId() throws Exception {
        // Create the AttendanceEntry with an existing ID
        attendanceEntry.setId(1L);
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        int databaseSizeBeforeCreate = attendanceEntryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttendanceEntries() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList
        restAttendanceEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].yearMonth").value(hasItem(DEFAULT_YEAR_MONTH)))
            .andExpect(jsonPath("$.[*].attStart").value(hasItem(DEFAULT_ATT_START.toString())))
            .andExpect(jsonPath("$.[*].attEnd").value(hasItem(DEFAULT_ATT_END.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }

    @Test
    @Transactional
    void getAttendanceEntry() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get the attendanceEntry
        restAttendanceEntryMockMvc
            .perform(get(ENTITY_API_URL_ID, attendanceEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceEntry.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.yearMonth").value(DEFAULT_YEAR_MONTH))
            .andExpect(jsonPath("$.attStart").value(DEFAULT_ATT_START.toString()))
            .andExpect(jsonPath("$.attEnd").value(DEFAULT_ATT_END.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    void getAttendanceEntriesByIdFiltering() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        Long id = attendanceEntry.getId();

        defaultAttendanceEntryShouldBeFound("id.equals=" + id);
        defaultAttendanceEntryShouldNotBeFound("id.notEquals=" + id);

        defaultAttendanceEntryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttendanceEntryShouldNotBeFound("id.greaterThan=" + id);

        defaultAttendanceEntryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttendanceEntryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdBy equals to DEFAULT_CREATED_BY
        defaultAttendanceEntryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the attendanceEntryList where createdBy equals to UPDATED_CREATED_BY
        defaultAttendanceEntryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAttendanceEntryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the attendanceEntryList where createdBy equals to UPDATED_CREATED_BY
        defaultAttendanceEntryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdBy is not null
        defaultAttendanceEntryShouldBeFound("createdBy.specified=true");

        // Get all the attendanceEntryList where createdBy is null
        defaultAttendanceEntryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdBy contains DEFAULT_CREATED_BY
        defaultAttendanceEntryShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the attendanceEntryList where createdBy contains UPDATED_CREATED_BY
        defaultAttendanceEntryShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdBy does not contain DEFAULT_CREATED_BY
        defaultAttendanceEntryShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the attendanceEntryList where createdBy does not contain UPDATED_CREATED_BY
        defaultAttendanceEntryShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdDate equals to DEFAULT_CREATED_DATE
        defaultAttendanceEntryShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the attendanceEntryList where createdDate equals to UPDATED_CREATED_DATE
        defaultAttendanceEntryShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultAttendanceEntryShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the attendanceEntryList where createdDate equals to UPDATED_CREATED_DATE
        defaultAttendanceEntryShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where createdDate is not null
        defaultAttendanceEntryShouldBeFound("createdDate.specified=true");

        // Get all the attendanceEntryList where createdDate is null
        defaultAttendanceEntryShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the attendanceEntryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the attendanceEntryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedBy is not null
        defaultAttendanceEntryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the attendanceEntryList where lastModifiedBy is null
        defaultAttendanceEntryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the attendanceEntryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the attendanceEntryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAttendanceEntryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultAttendanceEntryShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the attendanceEntryList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultAttendanceEntryShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultAttendanceEntryShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the attendanceEntryList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultAttendanceEntryShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where lastModifiedDate is not null
        defaultAttendanceEntryShouldBeFound("lastModifiedDate.specified=true");

        // Get all the attendanceEntryList where lastModifiedDate is null
        defaultAttendanceEntryShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByYearMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where yearMonth equals to DEFAULT_YEAR_MONTH
        defaultAttendanceEntryShouldBeFound("yearMonth.equals=" + DEFAULT_YEAR_MONTH);

        // Get all the attendanceEntryList where yearMonth equals to UPDATED_YEAR_MONTH
        defaultAttendanceEntryShouldNotBeFound("yearMonth.equals=" + UPDATED_YEAR_MONTH);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByYearMonthIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where yearMonth in DEFAULT_YEAR_MONTH or UPDATED_YEAR_MONTH
        defaultAttendanceEntryShouldBeFound("yearMonth.in=" + DEFAULT_YEAR_MONTH + "," + UPDATED_YEAR_MONTH);

        // Get all the attendanceEntryList where yearMonth equals to UPDATED_YEAR_MONTH
        defaultAttendanceEntryShouldNotBeFound("yearMonth.in=" + UPDATED_YEAR_MONTH);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByYearMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where yearMonth is not null
        defaultAttendanceEntryShouldBeFound("yearMonth.specified=true");

        // Get all the attendanceEntryList where yearMonth is null
        defaultAttendanceEntryShouldNotBeFound("yearMonth.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByYearMonthContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where yearMonth contains DEFAULT_YEAR_MONTH
        defaultAttendanceEntryShouldBeFound("yearMonth.contains=" + DEFAULT_YEAR_MONTH);

        // Get all the attendanceEntryList where yearMonth contains UPDATED_YEAR_MONTH
        defaultAttendanceEntryShouldNotBeFound("yearMonth.contains=" + UPDATED_YEAR_MONTH);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByYearMonthNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where yearMonth does not contain DEFAULT_YEAR_MONTH
        defaultAttendanceEntryShouldNotBeFound("yearMonth.doesNotContain=" + DEFAULT_YEAR_MONTH);

        // Get all the attendanceEntryList where yearMonth does not contain UPDATED_YEAR_MONTH
        defaultAttendanceEntryShouldBeFound("yearMonth.doesNotContain=" + UPDATED_YEAR_MONTH);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByAttStartIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where attStart equals to DEFAULT_ATT_START
        defaultAttendanceEntryShouldBeFound("attStart.equals=" + DEFAULT_ATT_START);

        // Get all the attendanceEntryList where attStart equals to UPDATED_ATT_START
        defaultAttendanceEntryShouldNotBeFound("attStart.equals=" + UPDATED_ATT_START);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByAttStartIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where attStart in DEFAULT_ATT_START or UPDATED_ATT_START
        defaultAttendanceEntryShouldBeFound("attStart.in=" + DEFAULT_ATT_START + "," + UPDATED_ATT_START);

        // Get all the attendanceEntryList where attStart equals to UPDATED_ATT_START
        defaultAttendanceEntryShouldNotBeFound("attStart.in=" + UPDATED_ATT_START);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByAttStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where attStart is not null
        defaultAttendanceEntryShouldBeFound("attStart.specified=true");

        // Get all the attendanceEntryList where attStart is null
        defaultAttendanceEntryShouldNotBeFound("attStart.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByAttEndIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where attEnd equals to DEFAULT_ATT_END
        defaultAttendanceEntryShouldBeFound("attEnd.equals=" + DEFAULT_ATT_END);

        // Get all the attendanceEntryList where attEnd equals to UPDATED_ATT_END
        defaultAttendanceEntryShouldNotBeFound("attEnd.equals=" + UPDATED_ATT_END);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByAttEndIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where attEnd in DEFAULT_ATT_END or UPDATED_ATT_END
        defaultAttendanceEntryShouldBeFound("attEnd.in=" + DEFAULT_ATT_END + "," + UPDATED_ATT_END);

        // Get all the attendanceEntryList where attEnd equals to UPDATED_ATT_END
        defaultAttendanceEntryShouldNotBeFound("attEnd.in=" + UPDATED_ATT_END);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByAttEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where attEnd is not null
        defaultAttendanceEntryShouldBeFound("attEnd.specified=true");

        // Get all the attendanceEntryList where attEnd is null
        defaultAttendanceEntryShouldNotBeFound("attEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where comment equals to DEFAULT_COMMENT
        defaultAttendanceEntryShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the attendanceEntryList where comment equals to UPDATED_COMMENT
        defaultAttendanceEntryShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultAttendanceEntryShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the attendanceEntryList where comment equals to UPDATED_COMMENT
        defaultAttendanceEntryShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where comment is not null
        defaultAttendanceEntryShouldBeFound("comment.specified=true");

        // Get all the attendanceEntryList where comment is null
        defaultAttendanceEntryShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCommentContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where comment contains DEFAULT_COMMENT
        defaultAttendanceEntryShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the attendanceEntryList where comment contains UPDATED_COMMENT
        defaultAttendanceEntryShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllAttendanceEntriesByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        // Get all the attendanceEntryList where comment does not contain DEFAULT_COMMENT
        defaultAttendanceEntryShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the attendanceEntryList where comment does not contain UPDATED_COMMENT
        defaultAttendanceEntryShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttendanceEntryShouldBeFound(String filter) throws Exception {
        restAttendanceEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].yearMonth").value(hasItem(DEFAULT_YEAR_MONTH)))
            .andExpect(jsonPath("$.[*].attStart").value(hasItem(DEFAULT_ATT_START.toString())))
            .andExpect(jsonPath("$.[*].attEnd").value(hasItem(DEFAULT_ATT_END.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

        // Check, that the count call also returns 1
        restAttendanceEntryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttendanceEntryShouldNotBeFound(String filter) throws Exception {
        restAttendanceEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceEntryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAttendanceEntry() throws Exception {
        // Get the attendanceEntry
        restAttendanceEntryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttendanceEntry() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();

        // Update the attendanceEntry
        AttendanceEntry updatedAttendanceEntry = attendanceEntryRepository.findById(attendanceEntry.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceEntry are not directly saved in db
        em.detach(updatedAttendanceEntry);
        updatedAttendanceEntry
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .yearMonth(UPDATED_YEAR_MONTH)
            .attStart(UPDATED_ATT_START)
            .attEnd(UPDATED_ATT_END)
            .comment(UPDATED_COMMENT);
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(updatedAttendanceEntry);

        restAttendanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceEntryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
        AttendanceEntry testAttendanceEntry = attendanceEntryList.get(attendanceEntryList.size() - 1);
        assertThat(testAttendanceEntry.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttendanceEntry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAttendanceEntry.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAttendanceEntry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testAttendanceEntry.getYearMonth()).isEqualTo(UPDATED_YEAR_MONTH);
        assertThat(testAttendanceEntry.getAttStart()).isEqualTo(UPDATED_ATT_START);
        assertThat(testAttendanceEntry.getAttEnd()).isEqualTo(UPDATED_ATT_END);
        assertThat(testAttendanceEntry.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingAttendanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();
        attendanceEntry.setId(count.incrementAndGet());

        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendanceEntryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttendanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();
        attendanceEntry.setId(count.incrementAndGet());

        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttendanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();
        attendanceEntry.setId(count.incrementAndGet());

        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceEntryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttendanceEntryWithPatch() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();

        // Update the attendanceEntry using partial update
        AttendanceEntry partialUpdatedAttendanceEntry = new AttendanceEntry();
        partialUpdatedAttendanceEntry.setId(attendanceEntry.getId());

        partialUpdatedAttendanceEntry
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .yearMonth(UPDATED_YEAR_MONTH)
            .attStart(UPDATED_ATT_START);

        restAttendanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendanceEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendanceEntry))
            )
            .andExpect(status().isOk());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
        AttendanceEntry testAttendanceEntry = attendanceEntryList.get(attendanceEntryList.size() - 1);
        assertThat(testAttendanceEntry.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttendanceEntry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAttendanceEntry.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAttendanceEntry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testAttendanceEntry.getYearMonth()).isEqualTo(UPDATED_YEAR_MONTH);
        assertThat(testAttendanceEntry.getAttStart()).isEqualTo(UPDATED_ATT_START);
        assertThat(testAttendanceEntry.getAttEnd()).isEqualTo(DEFAULT_ATT_END);
        assertThat(testAttendanceEntry.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateAttendanceEntryWithPatch() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();

        // Update the attendanceEntry using partial update
        AttendanceEntry partialUpdatedAttendanceEntry = new AttendanceEntry();
        partialUpdatedAttendanceEntry.setId(attendanceEntry.getId());

        partialUpdatedAttendanceEntry
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .yearMonth(UPDATED_YEAR_MONTH)
            .attStart(UPDATED_ATT_START)
            .attEnd(UPDATED_ATT_END)
            .comment(UPDATED_COMMENT);

        restAttendanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendanceEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttendanceEntry))
            )
            .andExpect(status().isOk());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
        AttendanceEntry testAttendanceEntry = attendanceEntryList.get(attendanceEntryList.size() - 1);
        assertThat(testAttendanceEntry.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttendanceEntry.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAttendanceEntry.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAttendanceEntry.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testAttendanceEntry.getYearMonth()).isEqualTo(UPDATED_YEAR_MONTH);
        assertThat(testAttendanceEntry.getAttStart()).isEqualTo(UPDATED_ATT_START);
        assertThat(testAttendanceEntry.getAttEnd()).isEqualTo(UPDATED_ATT_END);
        assertThat(testAttendanceEntry.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingAttendanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();
        attendanceEntry.setId(count.incrementAndGet());

        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attendanceEntryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttendanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();
        attendanceEntry.setId(count.incrementAndGet());

        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttendanceEntry() throws Exception {
        int databaseSizeBeforeUpdate = attendanceEntryRepository.findAll().size();
        attendanceEntry.setId(count.incrementAndGet());

        // Create the AttendanceEntry
        AttendanceEntryDTO attendanceEntryDTO = attendanceEntryMapper.toDto(attendanceEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendanceEntryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attendanceEntryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttendanceEntry in the database
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttendanceEntry() throws Exception {
        // Initialize the database
        attendanceEntryRepository.saveAndFlush(attendanceEntry);

        int databaseSizeBeforeDelete = attendanceEntryRepository.findAll().size();

        // Delete the attendanceEntry
        restAttendanceEntryMockMvc
            .perform(delete(ENTITY_API_URL_ID, attendanceEntry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceEntry> attendanceEntryList = attendanceEntryRepository.findAll();
        assertThat(attendanceEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
