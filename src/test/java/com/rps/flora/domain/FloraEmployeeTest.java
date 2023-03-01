package com.rps.flora.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rps.flora.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FloraEmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FloraEmployee.class);
        FloraEmployee floraEmployee1 = new FloraEmployee();
        floraEmployee1.setId(1L);
        FloraEmployee floraEmployee2 = new FloraEmployee();
        floraEmployee2.setId(floraEmployee1.getId());
        assertThat(floraEmployee1).isEqualTo(floraEmployee2);
        floraEmployee2.setId(2L);
        assertThat(floraEmployee1).isNotEqualTo(floraEmployee2);
        floraEmployee1.setId(null);
        assertThat(floraEmployee1).isNotEqualTo(floraEmployee2);
    }
}
