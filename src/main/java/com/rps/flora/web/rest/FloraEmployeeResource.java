package com.rps.flora.web.rest;

import com.rps.flora.repository.FloraEmployeeRepository;
import com.rps.flora.service.FloraEmployeeQueryService;
import com.rps.flora.service.FloraEmployeeService;
import com.rps.flora.service.criteria.FloraEmployeeCriteria;
import com.rps.flora.service.dto.FloraEmployeeDTO;
import com.rps.flora.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.rps.flora.domain.FloraEmployee}.
 */
@RestController
@RequestMapping("/api")
public class FloraEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(FloraEmployeeResource.class);

    private static final String ENTITY_NAME = "floraEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FloraEmployeeService floraEmployeeService;

    private final FloraEmployeeRepository floraEmployeeRepository;

    private final FloraEmployeeQueryService floraEmployeeQueryService;

    public FloraEmployeeResource(
        FloraEmployeeService floraEmployeeService,
        FloraEmployeeRepository floraEmployeeRepository,
        FloraEmployeeQueryService floraEmployeeQueryService
    ) {
        this.floraEmployeeService = floraEmployeeService;
        this.floraEmployeeRepository = floraEmployeeRepository;
        this.floraEmployeeQueryService = floraEmployeeQueryService;
    }

    /**
     * {@code POST  /flora-employees} : Create a new floraEmployee.
     *
     * @param floraEmployeeDTO the floraEmployeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new floraEmployeeDTO, or with status {@code 400 (Bad Request)} if the floraEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flora-employees")
    public ResponseEntity<FloraEmployeeDTO> createFloraEmployee(@Valid @RequestBody FloraEmployeeDTO floraEmployeeDTO)
        throws URISyntaxException {
        log.debug("REST request to save FloraEmployee : {}", floraEmployeeDTO);
        if (floraEmployeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new floraEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FloraEmployeeDTO result = floraEmployeeService.save(floraEmployeeDTO);
        return ResponseEntity
            .created(new URI("/api/flora-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flora-employees/:id} : Updates an existing floraEmployee.
     *
     * @param id the id of the floraEmployeeDTO to save.
     * @param floraEmployeeDTO the floraEmployeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floraEmployeeDTO,
     * or with status {@code 400 (Bad Request)} if the floraEmployeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the floraEmployeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flora-employees/{id}")
    public ResponseEntity<FloraEmployeeDTO> updateFloraEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FloraEmployeeDTO floraEmployeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FloraEmployee : {}, {}", id, floraEmployeeDTO);
        if (floraEmployeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floraEmployeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floraEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FloraEmployeeDTO result = floraEmployeeService.update(floraEmployeeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, floraEmployeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /flora-employees/:id} : Partial updates given fields of an existing floraEmployee, field will ignore if it is null
     *
     * @param id the id of the floraEmployeeDTO to save.
     * @param floraEmployeeDTO the floraEmployeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated floraEmployeeDTO,
     * or with status {@code 400 (Bad Request)} if the floraEmployeeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the floraEmployeeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the floraEmployeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/flora-employees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FloraEmployeeDTO> partialUpdateFloraEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FloraEmployeeDTO floraEmployeeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FloraEmployee partially : {}, {}", id, floraEmployeeDTO);
        if (floraEmployeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, floraEmployeeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!floraEmployeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FloraEmployeeDTO> result = floraEmployeeService.partialUpdate(floraEmployeeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, floraEmployeeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /flora-employees} : get all the floraEmployees.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of floraEmployees in body.
     */
    @GetMapping("/flora-employees")
    public ResponseEntity<List<FloraEmployeeDTO>> getAllFloraEmployees(
        FloraEmployeeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FloraEmployees by criteria: {}", criteria);
        Page<FloraEmployeeDTO> page = floraEmployeeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /flora-employees/count} : count all the floraEmployees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/flora-employees/count")
    public ResponseEntity<Long> countFloraEmployees(FloraEmployeeCriteria criteria) {
        log.debug("REST request to count FloraEmployees by criteria: {}", criteria);
        return ResponseEntity.ok().body(floraEmployeeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /flora-employees/:id} : get the "id" floraEmployee.
     *
     * @param id the id of the floraEmployeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the floraEmployeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flora-employees/{id}")
    public ResponseEntity<FloraEmployeeDTO> getFloraEmployee(@PathVariable Long id) {
        log.debug("REST request to get FloraEmployee : {}", id);
        Optional<FloraEmployeeDTO> floraEmployeeDTO = floraEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(floraEmployeeDTO);
    }

    /**
     * {@code DELETE  /flora-employees/:id} : delete the "id" floraEmployee.
     *
     * @param id the id of the floraEmployeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flora-employees/{id}")
    public ResponseEntity<Void> deleteFloraEmployee(@PathVariable Long id) {
        log.debug("REST request to delete FloraEmployee : {}", id);
        floraEmployeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
