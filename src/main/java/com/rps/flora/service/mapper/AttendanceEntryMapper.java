package com.rps.flora.service.mapper;

import com.rps.flora.domain.AttendanceEntry;
import com.rps.flora.service.dto.AttendanceEntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttendanceEntry} and its DTO {@link AttendanceEntryDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendanceEntryMapper extends EntityMapper<AttendanceEntryDTO, AttendanceEntry> {}
