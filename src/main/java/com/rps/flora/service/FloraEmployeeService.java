package com.rps.flora.service;

import com.rps.flora.domain.FloraEmployee;
import com.rps.flora.repository.FloraEmployeeRepository;
import com.rps.flora.service.dto.FloraEmployeeDTO;
import com.rps.flora.service.mapper.FloraEmployeeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FloraEmployee}.
 */
@Service
@Transactional
public class FloraEmployeeService {

    private final Logger log = LoggerFactory.getLogger(FloraEmployeeService.class);

    private final FloraEmployeeRepository floraEmployeeRepository;

    private final FloraEmployeeMapper floraEmployeeMapper;

    public FloraEmployeeService(FloraEmployeeRepository floraEmployeeRepository, FloraEmployeeMapper floraEmployeeMapper) {
        this.floraEmployeeRepository = floraEmployeeRepository;
        this.floraEmployeeMapper = floraEmployeeMapper;
    }

    /**
     * Save a floraEmployee.
     *
     * @param floraEmployeeDTO the entity to save.
     * @return the persisted entity.
     */
    public FloraEmployeeDTO save(FloraEmployeeDTO floraEmployeeDTO) {
        log.debug("Request to save FloraEmployee : {}", floraEmployeeDTO);
        FloraEmployee floraEmployee = floraEmployeeMapper.toEntity(floraEmployeeDTO);
        floraEmployee = floraEmployeeRepository.save(floraEmployee);
        return floraEmployeeMapper.toDto(floraEmployee);
    }

    /**
     * Update a floraEmployee.
     *
     * @param floraEmployeeDTO the entity to save.
     * @return the persisted entity.
     */
    public FloraEmployeeDTO update(FloraEmployeeDTO floraEmployeeDTO) {
        log.debug("Request to update FloraEmployee : {}", floraEmployeeDTO);
        FloraEmployee floraEmployee = floraEmployeeMapper.toEntity(floraEmployeeDTO);
        floraEmployee = floraEmployeeRepository.save(floraEmployee);
        return floraEmployeeMapper.toDto(floraEmployee);
    }

    /**
     * Partially update a floraEmployee.
     *
     * @param floraEmployeeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FloraEmployeeDTO> partialUpdate(FloraEmployeeDTO floraEmployeeDTO) {
        log.debug("Request to partially update FloraEmployee : {}", floraEmployeeDTO);

        return floraEmployeeRepository
            .findById(floraEmployeeDTO.getId())
            .map(existingFloraEmployee -> {
                floraEmployeeMapper.partialUpdate(existingFloraEmployee, floraEmployeeDTO);

                return existingFloraEmployee;
            })
            .map(floraEmployeeRepository::save)
            .map(floraEmployeeMapper::toDto);
    }

    /**
     * Get all the floraEmployees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FloraEmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FloraEmployees");
        return floraEmployeeRepository.findAll(pageable).map(floraEmployeeMapper::toDto);
    }

    /**
     * Get one floraEmployee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FloraEmployeeDTO> findOne(Long id) {
        log.debug("Request to get FloraEmployee : {}", id);
        return floraEmployeeRepository.findById(id).map(floraEmployeeMapper::toDto);
    }

    /**
     * Delete the floraEmployee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FloraEmployee : {}", id);
        floraEmployeeRepository.deleteById(id);
    }
}
