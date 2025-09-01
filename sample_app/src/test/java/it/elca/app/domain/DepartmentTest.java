package it.elca.app.domain;

import static it.elca.app.domain.DepartmentTestSamples.*;
import static it.elca.app.domain.EmployeeTestSamples.*;
import static it.elca.app.domain.JobHistoryTestSamples.*;
import static it.elca.app.domain.LocationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import it.elca.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DepartmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Department.class);
        Department department1 = getDepartmentSample1();
        Department department2 = new Department();
        assertThat(department1).isNotEqualTo(department2);

        department2.setId(department1.getId());
        assertThat(department1).isEqualTo(department2);

        department2 = getDepartmentSample2();
        assertThat(department1).isNotEqualTo(department2);
    }

    @Test
    void locationTest() {
        Department department = getDepartmentRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        department.setLocation(locationBack);
        assertThat(department.getLocation()).isEqualTo(locationBack);

        department.location(null);
        assertThat(department.getLocation()).isNull();
    }

    @Test
    void employeeTest() {
        Department department = getDepartmentRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        department.addEmployee(employeeBack);
        assertThat(department.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartment()).isEqualTo(department);

        department.removeEmployee(employeeBack);
        assertThat(department.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartment()).isNull();

        department.employees(new HashSet<>(Set.of(employeeBack)));
        assertThat(department.getEmployees()).containsOnly(employeeBack);
        assertThat(employeeBack.getDepartment()).isEqualTo(department);

        department.setEmployees(new HashSet<>());
        assertThat(department.getEmployees()).doesNotContain(employeeBack);
        assertThat(employeeBack.getDepartment()).isNull();
    }

    @Test
    void jobHistoryTest() {
        Department department = getDepartmentRandomSampleGenerator();
        JobHistory jobHistoryBack = getJobHistoryRandomSampleGenerator();

        department.setJobHistory(jobHistoryBack);
        assertThat(department.getJobHistory()).isEqualTo(jobHistoryBack);
        assertThat(jobHistoryBack.getDepartment()).isEqualTo(department);

        department.jobHistory(null);
        assertThat(department.getJobHistory()).isNull();
        assertThat(jobHistoryBack.getDepartment()).isNull();
    }
}
