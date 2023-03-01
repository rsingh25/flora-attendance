package com.rps.flora.service;

import com.rps.flora.domain.*; // for static metamodels
import com.rps.flora.domain.AttendanceEntry;
import com.rps.flora.repository.AttendanceEntryRepository;
import com.rps.flora.service.criteria.AttendanceEntryCriteria;
import com.rps.flora.service.dto.AttendanceEntryDTO;
import com.rps.flora.service.mapper.AttendanceEntryMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AttendanceEntry} entities in the database.
 * The main input is a {@link AttendanceEntryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendanceEntryDTO} or a {@link Page} of {@link AttendanceEntryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceEntryQueryService extends QueryService<AttendanceEntry> {

    private final Logger log = LoggerFactory.getLogger(AttendanceEntryQueryService.class);

    private final AttendanceEntryRepository attendanceEntryRepository;

    private final AttendanceEntryMapper attendanceEntryMapper;

    public AttendanceEntryQueryService(AttendanceEntryRepository attendanceEntryRepository, AttendanceEntryMapper attendanceEntryMapper) {
        this.attendanceEntryRepository = attendanceEntryRepository;
        this.attendanceEntryMapper = attendanceEntryMapper;
    }

    /**
     * Return a {@link List} of {@link AttendanceEntryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceEntryDTO> findByCriteria(AttendanceEntryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AttendanceEntry> specification = createSpecification(criteria);
        return attendanceEntryMapper.toDto(attendanceEntryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttendanceEntryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceEntryDTO> findByCriteria(AttendanceEntryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AttendanceEntry> specification = createSpecification(criteria);
        return attendanceEntryRepository.findAll(specification, page).map(attendanceEntryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendanceEntryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AttendanceEntry> specification = createSpecification(criteria);
        return attendanceEntryRepository.count(specification);
    }

    /**
     * Function to convert {@link AttendanceEntryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AttendanceEntry> createSpecification(AttendanceEntryCriteria criteria) {
        Specification<AttendanceEntry> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AttendanceEntry_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AttendanceEntry_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), AttendanceEntry_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AttendanceEntry_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), AttendanceEntry_.lastModifiedDate));
            }
            if (criteria.getYearMonth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYearMonth(), AttendanceEntry_.yearMonth));
            }
            if (criteria.getAttStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttStart(), AttendanceEntry_.attStart));
            }
            if (criteria.getAttEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttEnd(), AttendanceEntry_.attEnd));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), AttendanceEntry_.comment));
            }
        }
        return specification;
    }
}
