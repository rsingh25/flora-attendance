package com.rps.flora.web.rest;

import com.rps.flora.repository.AttendanceEntryRepository;
import com.rps.flora.service.AttendanceEntryQueryService;
import com.rps.flora.service.AttendanceEntryService;
import com.rps.flora.service.criteria.AttendanceEntryCriteria;
import com.rps.flora.service.dto.AttendanceEntryDTO;
import com.rps.flora.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.rps.flora.domain.AttendanceEntry}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceEntryResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceEntryResource.class);

    private static final String ENTITY_NAME = "attendanceEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceEntryService attendanceEntryService;

    private final AttendanceEntryRepository attendanceEntryRepository;

    private final AttendanceEntryQueryService attendanceEntryQueryService;

    public AttendanceEntryResource(
        AttendanceEntryService attendanceEntryService,
        AttendanceEntryRepository attendanceEntryRepository,
        AttendanceEntryQueryService attendanceEntryQueryService
    ) {
        this.attendanceEntryService = attendanceEntryService;
        this.attendanceEntryRepository = attendanceEntryRepository;
        this.attendanceEntryQueryService = attendanceEntryQueryService;
    }

    /**
     * {@code POST  /attendance-entries} : Create a new attendanceEntry.
     *
     * @param attendanceEntryDTO the attendanceEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceEntryDTO, or with status {@code 400 (Bad Request)} if the attendanceEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-entries")
    public ResponseEntity<AttendanceEntryDTO> createAttendanceEntry(@Valid @RequestBody AttendanceEntryDTO attendanceEntryDTO)
        throws URISyntaxException {
        log.debug("REST request to save AttendanceEntry : {}", attendanceEntryDTO);
        if (attendanceEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendanceEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceEntryDTO result = attendanceEntryService.save(attendanceEntryDTO);
        return ResponseEntity
            .created(new URI("/api/attendance-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-entries/:id} : Updates an existing attendanceEntry.
     *
     * @param id the id of the attendanceEntryDTO to save.
     * @param attendanceEntryDTO the attendanceEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceEntryDTO,
     * or with status {@code 400 (Bad Request)} if the attendanceEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-entries/{id}")
    public ResponseEntity<AttendanceEntryDTO> updateAttendanceEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttendanceEntryDTO attendanceEntryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttendanceEntry : {}, {}", id, attendanceEntryDTO);
        if (attendanceEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendanceEntryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendanceEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttendanceEntryDTO result = attendanceEntryService.update(attendanceEntryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendanceEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attendance-entries/:id} : Partial updates given fields of an existing attendanceEntry, field will ignore if it is null
     *
     * @param id the id of the attendanceEntryDTO to save.
     * @param attendanceEntryDTO the attendanceEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceEntryDTO,
     * or with status {@code 400 (Bad Request)} if the attendanceEntryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attendanceEntryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attendanceEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attendance-entries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttendanceEntryDTO> partialUpdateAttendanceEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttendanceEntryDTO attendanceEntryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttendanceEntry partially : {}, {}", id, attendanceEntryDTO);
        if (attendanceEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendanceEntryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendanceEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttendanceEntryDTO> result = attendanceEntryService.partialUpdate(attendanceEntryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendanceEntryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attendance-entries} : get all the attendanceEntries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceEntries in body.
     */
    @GetMapping("/attendance-entries")
    public ResponseEntity<List<AttendanceEntryDTO>> getAllAttendanceEntries(
        AttendanceEntryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AttendanceEntries by criteria: {}", criteria);
        Page<AttendanceEntryDTO> page = attendanceEntryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-entries} : get all the attendanceEntries.
     *
     * @param criteria the criteria which the requested entities should match.
     */
    @GetMapping("/attendance-entries/download")
    public void downloadAllAttendanceEntries(AttendanceEntryCriteria criteria, HttpServletResponse servletResponse) throws IOException {
        log.debug("REST request to download AttendanceEntries by criteria: {}", criteria);
        List<AttendanceEntryDTO> page = attendanceEntryQueryService.findByCriteria(criteria);
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"report.csv\"");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy-HH:mm");
        try (CSVPrinter csvPrinter = new CSVPrinter(servletResponse.getWriter(), CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(
                "Created By",
                "Created Date",
                "Last Modified By",
                "Last Modified Date",
                "Year Month",
                "Att Start",
                "Att End",
                "Time (Hrs.)"
            );
            for (AttendanceEntryDTO att : page) {
                csvPrinter.printRecord(
                    att.getCreatedBy(),
                    att.getCreatedDate() == null ? "" : att.getCreatedDate().atZone(ZoneId.systemDefault()).format(formatter),
                    att.getLastModifiedBy(),
                    att.getLastModifiedDate() == null ? "" : att.getLastModifiedDate().atZone(ZoneId.systemDefault()).format(formatter),
                    att.getYearMonth(),
                    att.getAttStart() == null ? "" : att.getAttStart().atZone(ZoneId.systemDefault()).format(formatter),
                    att.getAttEnd() == null ? "" : att.getAttEnd().atZone(ZoneId.systemDefault()).format(formatter),
                    att.getAttEnd() != null && att.getAttStart() != null
                        ? String.format("%.2f", att.getAttStart().until(att.getAttEnd(), ChronoUnit.MINUTES) / 60.0F)
                        : "",
                    att.getComment()
                );
            }
        }
    }

    /**
     * {@code GET  /attendance-entries/count} : count all the attendanceEntries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attendance-entries/count")
    public ResponseEntity<Long> countAttendanceEntries(AttendanceEntryCriteria criteria) {
        log.debug("REST request to count AttendanceEntries by criteria: {}", criteria);
        return ResponseEntity.ok().body(attendanceEntryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attendance-entries/:id} : get the "id" attendanceEntry.
     *
     * @param id the id of the attendanceEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-entries/{id}")
    public ResponseEntity<AttendanceEntryDTO> getAttendanceEntry(@PathVariable Long id) {
        log.debug("REST request to get AttendanceEntry : {}", id);
        Optional<AttendanceEntryDTO> attendanceEntryDTO = attendanceEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceEntryDTO);
    }

    /**
     * {@code DELETE  /attendance-entries/:id} : delete the "id" attendanceEntry.
     *
     * @param id the id of the attendanceEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-entries/{id}")
    public ResponseEntity<Void> deleteAttendanceEntry(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceEntry : {}", id);
        attendanceEntryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
