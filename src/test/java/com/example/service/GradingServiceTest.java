package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class GradingServiceTest {

    private GradingService gradingService;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        employeeService = new EmployeeService();
        gradingService = new GradingService(employeeService);

        employeeService.addEmployee(new Employee("Jan", "Kowalski", "j.kowalski@techcorp.pl",
                "TechCorp", Position.PROGRAMISTA, 8500));
        employeeService.addEmployee(new Employee("Anna", "Nowak", "a.nowak@techcorp.pl",
                "TechCorp", Position.MANAGER, 13000));
        employeeService.addEmployee(new Employee("Janusz", "Kowalski", "j.kowalski@techcorp.pl",
                "InnaFirma", Position.MANAGER, 15000));
    }

    @ParameterizedTest
    @MethodSource("provideGrades")
    void testCalculateAverageGrade(List<Double> grades, double expectedResult) {
        // Arrange
        String email = "j.kowalski@techcorp.pl";
        for (Double grade : grades) {
            gradingService.addGrade(email);
        }

        // Act
        double avg = gradingService.calculateAverageGrade(email);

        // Assert
        assertThat(avg).isCloseTo(expectedResult, within(0.01));
    }

    static Stream<Arguments> provideGrades() {
        return Stream.of(
                Arguments.of(Arrays.asList(5), 5.0),
                Arguments.of(Arrays.asList(4, 4, 4, 4), 4.0),
                Arguments.of(Arrays.asList(3, 4, 5), 4.0),
                Arguments.of(Arrays.asList(1, 2, 3, 4, 5), 3.0),
                Arguments.of(Arrays.asList(5, 5, 3, 4), 4.25)
        );
    }

}
