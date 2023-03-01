package com.rps.flora.service;

import com.rps.flora.domain.*; // for static metamodels
import com.rps.flora.domain.FloraEmployee;
import com.rps.flora.repository.FloraEmployeeRepository;
import com.rps.flora.service.criteria.FloraEmployeeCriteria;
import com.rps.flora.service.dto.FloraEmployeeDTO;
import com.rps.flora.service.mapper.FloraEmployeeMapper;
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
 * Service for executing complex queries for {@link FloraEmployee} entities in the database.
 * The main input is a {@link FloraEmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FloraEmployeeDTO} or a {@link Page} of {@link FloraEmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FloraEmployeeQueryService extends QueryService<FloraEmployee> {

    private final Logger log = LoggerFactory.getLogger(FloraEmployeeQueryService.class);

    private final FloraEmployeeRepository floraEmployeeRepository;

    private final FloraEmployeeMapper floraEmployeeMapper;

    public FloraEmployeeQueryService(FloraEmployeeRepository floraEmployeeRepository, FloraEmployeeMapper floraEmployeeMapper) {
        this.floraEmployeeRepository = floraEmployeeRepository;
        this.floraEmployeeMapper = floraEmployeeMapper;
    }

    /**
     * Return a {@link List} of {@link FloraEmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FloraEmployeeDTO> findByCriteria(FloraEmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FloraEmployee> specification = createSpecification(criteria);
        return floraEmployeeMapper.toDto(floraEmployeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FloraEmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FloraEmployeeDTO> findByCriteria(FloraEmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FloraEmployee> specification = createSpecification(criteria);
        return floraEmployeeRepository.findAll(specification, page).map(floraEmployeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FloraEmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FloraEmployee> specification = createSpecification(criteria);
        return floraEmployeeRepository.count(specification);
    }

    /**
     * Function to convert {@link FloraEmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FloraEmployee> createSpecification(FloraEmployeeCriteria criteria) {
        Specification<FloraEmployee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FloraEmployee_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), FloraEmployee_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), FloraEmployee_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), FloraEmployee_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), FloraEmployee_.lastModifiedDate));
            }
            if (criteria.getAttStartTime1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttStartTime1(), FloraEmployee_.attStartTime1));
            }
            if (criteria.getAttEndTime1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttEndTime1(), FloraEmployee_.attEndTime1));
            }
            if (criteria.getAttStartTime2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttStartTime2(), FloraEmployee_.attStartTime2));
            }
            if (criteria.getInternalUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInternalUserId(),
                            root -> root.join(FloraEmployee_.internalUser, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
