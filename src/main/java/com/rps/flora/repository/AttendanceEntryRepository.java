package com.rps.flora.repository;

import com.rps.flora.domain.AttendanceEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttendanceEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceEntryRepository extends JpaRepository<AttendanceEntry, Long>, JpaSpecificationExecutor<AttendanceEntry> {}
