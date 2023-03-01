package com.rps.flora.repository;

import com.rps.flora.domain.FloraEmployee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FloraEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloraEmployeeRepository extends JpaRepository<FloraEmployee, Long>, JpaSpecificationExecutor<FloraEmployee> {}
