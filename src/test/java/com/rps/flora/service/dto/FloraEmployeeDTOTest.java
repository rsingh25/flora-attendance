package com.rps.flora.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.rps.flora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FloraEmployeeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FloraEmployeeDTO.class);
        FloraEmployeeDTO floraEmployeeDTO1 = new FloraEmployeeDTO();
        floraEmployeeDTO1.setId(1L);
        FloraEmployeeDTO floraEmployeeDTO2 = new FloraEmployeeDTO();
        assertThat(floraEmployeeDTO1).isNotEqualTo(floraEmployeeDTO2);
        floraEmployeeDTO2.setId(floraEmployeeDTO1.getId());
        assertThat(floraEmployeeDTO1).isEqualTo(floraEmployeeDTO2);
        floraEmployeeDTO2.setId(2L);
        assertThat(floraEmployeeDTO1).isNotEqualTo(floraEmployeeDTO2);
        floraEmployeeDTO1.setId(null);
        assertThat(floraEmployeeDTO1).isNotEqualTo(floraEmployeeDTO2);
    }
}
