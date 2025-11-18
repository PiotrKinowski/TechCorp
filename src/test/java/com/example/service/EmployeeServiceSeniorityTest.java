package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EmployeeServiceSeniorityTest {

    private EmployeeService employeeService;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();

        employee1 = new Employee("Jan", "Kowalski", "j.kowalski@techcorp.pl",
                "TechCorp", Position.PROGRAMISTA, 7500);
        employee2 = new Employee("Anna", "Nowak", "a.nowak@techcorp.pl",
                "TechCorp", Position.MANAGER, 13000);
        employee3 = new Employee("Piotr", "Wiśniewski", "p.wisniewski@innafirma.pl",
                "InnaFirma", Position.PROGRAMISTA, 9000);
        employee4 = new Employee("Maria", "Lewandowska", "m.lewandowska@techcorp.pl",
                "TechCorp", Position.STAZYSTA, 3500);
    }

    @ParameterizedTest
    @CsvSource({
            "2020-01-01",
            "2018-06-15",
            "2022-12-01",
            "2020-02-29"
    })
    void testSetHireDate(String hireDate) {
        // Arrange
        LocalDate hire = LocalDate.parse(hireDate);
        employee1.setHireDate(hire);

        // Assert
        assertThat(employee1.getHireDate(), is(equalTo(hireDate)));
    }

    @ParameterizedTest
    @CsvSource({
            "2020-01-01, 5",
            "2018-06-15, 7",
            "2022-12-01, 2",
            "2020-02-29, 5"
    })
    void testCalculateSeniority(String hireDate, String currentDate, int expectedSeniority) {
        // Arrange
        LocalDate hire = LocalDate.parse(hireDate);
        LocalDate current = LocalDate.parse("2025-11-18");
        employee1.setHireDate(hire);
        employeeService.addEmployee(employee1);

        // Assert
        assertThat(employeeService.calculateSeniority(current), is(equalTo(expectedSeniority)));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 3, p.wisniewski@innafirma.pl",
            "1, 10, j.kowalski@techcorp.pl",
            "1, 5, m.lewandowska@techcorp.pl",
            "0, 0, a.nowak@techcorp.pl"
    })
    void testFilterBySeniorityRange(int min, int max, String expected) {
        // Arrange
        employee1.setHireDate(LocalDate.parse("2016-11-01"));
        employee2.setHireDate(LocalDate.parse("2025-07-21"));
        employee3.setHireDate(LocalDate.parse("2022-02-05"));
        employee4.setHireDate(LocalDate.parse("2021-10-13"));
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);
        LocalDate current = LocalDate.parse("2025-11-18");

        // Assert
        assertThat(employeeService.filterBySeniorityRange(min, max, current), hasItem(hasProperty("email", is(expected))));
    }

    @ParameterizedTest
    @CsvSource({
            "2025-01-01, j.kowalski@techcorp.pl",
            "2025-12-15, a.nowak@techcorp.pl",
            "2022-09-01, p.wisniewski@innafirma.pl",
            "2022-09-29, m.lewandowska@techcorp.pl"
    })
    void testFindJubilees(String currentDate, String expected) {
        // Arrange
        employee1.setHireDate(LocalDate.parse("2014-11-01"));
        employee2.setHireDate(LocalDate.parse("2005-07-21"));
        employee3.setHireDate(LocalDate.parse("1992-02-05"));
        employee4.setHireDate(LocalDate.parse("2011-10-13"));
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);
        LocalDate current = LocalDate.parse(currentDate);

        // Assert
        assertThat(employeeService.findJubilees(current), hasItem(hasProperty("email", is(expected))));
    }
}
