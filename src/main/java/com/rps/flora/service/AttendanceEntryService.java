package com.rps.flora.service;

import com.rps.flora.domain.AttendanceEntry;
import com.rps.flora.repository.AttendanceEntryRepository;
import com.rps.flora.service.dto.AttendanceEntryDTO;
import com.rps.flora.service.mapper.AttendanceEntryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttendanceEntry}.
 */
@Service
@Transactional
public class AttendanceEntryService {

    private final Logger log = LoggerFactory.getLogger(AttendanceEntryService.class);

    private final AttendanceEntryRepository attendanceEntryRepository;

    private final AttendanceEntryMapper attendanceEntryMapper;

    public AttendanceEntryService(AttendanceEntryRepository attendanceEntryRepository, AttendanceEntryMapper attendanceEntryMapper) {
        this.attendanceEntryRepository = attendanceEntryRepository;
        this.attendanceEntryMapper = attendanceEntryMapper;
    }

    /**
     * Save a attendanceEntry.
     *
     * @param attendanceEntryDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceEntryDTO save(AttendanceEntryDTO attendanceEntryDTO) {
        log.debug("Request to save AttendanceEntry : {}", attendanceEntryDTO);
        AttendanceEntry attendanceEntry = attendanceEntryMapper.toEntity(attendanceEntryDTO);
        attendanceEntry = attendanceEntryRepository.save(attendanceEntry);
        return attendanceEntryMapper.toDto(attendanceEntry);
    }

    /**
     * Update a attendanceEntry.
     *
     * @param attendanceEntryDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendanceEntryDTO update(AttendanceEntryDTO attendanceEntryDTO) {
        log.debug("Request to update AttendanceEntry : {}", attendanceEntryDTO);
        AttendanceEntry attendanceEntry = attendanceEntryMapper.toEntity(attendanceEntryDTO);
        attendanceEntry = attendanceEntryRepository.save(attendanceEntry);
        return attendanceEntryMapper.toDto(attendanceEntry);
    }

    /**
     * Partially update a attendanceEntry.
     *
     * @param attendanceEntryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AttendanceEntryDTO> partialUpdate(AttendanceEntryDTO attendanceEntryDTO) {
        log.debug("Request to partially update AttendanceEntry : {}", attendanceEntryDTO);

        return attendanceEntryRepository
            .findById(attendanceEntryDTO.getId())
            .map(existingAttendanceEntry -> {
                attendanceEntryMapper.partialUpdate(existingAttendanceEntry, attendanceEntryDTO);

                return existingAttendanceEntry;
            })
            .map(attendanceEntryRepository::save)
            .map(attendanceEntryMapper::toDto);
    }

    /**
     * Get all the attendanceEntries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceEntries");
        return attendanceEntryRepository.findAll(pageable).map(attendanceEntryMapper::toDto);
    }

    /**
     * Get one attendanceEntry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceEntryDTO> findOne(Long id) {
        log.debug("Request to get AttendanceEntry : {}", id);
        return attendanceEntryRepository.findById(id).map(attendanceEntryMapper::toDto);
    }

    /**
     * Delete the attendanceEntry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceEntry : {}", id);
        attendanceEntryRepository.deleteById(id);
    }
}
