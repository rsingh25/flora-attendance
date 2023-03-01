package com.rps.flora.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FloraEmployeeMapperTest {

    private FloraEmployeeMapper floraEmployeeMapper;

    @BeforeEach
    public void setUp() {
        floraEmployeeMapper = new FloraEmployeeMapperImpl();
    }
}
