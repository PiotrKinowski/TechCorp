package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        employeeService.addEmployee(new Employee("Andrzej", "Kowalski", "a.kowalski@techcorp.pl",
                "InnaFirma", Position.MANAGER, 15000));
    }

    @ParameterizedTest(name = "Czy średnia ocen: {0} wynosi {1}")
    @MethodSource("provideGrades")
    void testCalculateAverageGrade(List<Double> grades, double expectedResult) {
        // Arrange
        String email = "j.kowalski@techcorp.pl";
        for (Double grade : grades) {
            gradingService.addGrade(email, grade);
        }

        // Act
        double avg = gradingService.calculateAverageGrade(email);

        // Assert
        assertThat(avg).isCloseTo(expectedResult, within(0.01));
    }

    static Stream<Arguments> provideGrades() {
        return Stream.of(
                Arguments.of(Arrays.asList(5.0), 5.0),
                Arguments.of(Arrays.asList(4.0, 4.0, 4.0, 4.0), 4.0),
                Arguments.of(Arrays.asList(3.0, 4.0, 5.0), 4.0),
                Arguments.of(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0), 3.0),
                Arguments.of(Arrays.asList(5.0, 5.0, 3.0, 4.0), 4.25)
        );
    }

    @ParameterizedTest
    @MethodSource("provideGradesWithEmail")
    void testBestPerformingEmployee(Map<String,List<Double>> grades, String expectedResult) {
        // Arrange
        for (String email : grades.keySet()) {
            List<Double> employeeGrades =  grades.get(email);
            for (Double grade : employeeGrades) {
                gradingService.addGrade(email, grade);
            }
        }

        // Act
        String result = gradingService.bestPerformingEmployee();

        // Assert
        assertThat(expectedResult).isEqualTo(result);
    }

    static Stream<Arguments> provideGradesWithEmail() {
        Map<String,List<Double>> employeesGrades1 = new HashMap<>();
        employeesGrades1.put("j.kowalski@techcorp.pl",Arrays.asList(3.0, 4.0, 5.0));
        employeesGrades1.put("a.nowak@techcorp.pl",Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        employeesGrades1.put("a.kowalski@techcorp.pl",Arrays.asList(5.0));

        Map<String,List<Double>> employeesGrades2 = new HashMap<>();
        employeesGrades2.put("j.kowalski@techcorp.pl",Arrays.asList(3.0, 4.0, 2.0));
        employeesGrades2.put("a.nowak@techcorp.pl",Arrays.asList(2.0, 2.0, 3.0, 4.0, 4.0));
        employeesGrades2.put("a.kowalski@techcorp.pl",Arrays.asList(4.0, 3.5));

        Map<String,List<Double>> employeesGrades3 = new HashMap<>();
        employeesGrades3.put("j.kowalski@techcorp.pl",Arrays.asList(3.5, 4.5, 5.0));
        employeesGrades3.put("a.nowak@techcorp.pl",Arrays.asList(2.0, 3.5, 5.0, 5.0, 5.0));
        employeesGrades3.put("a.kowalski@techcorp.pl",Arrays.asList(4.0));
        return Stream.of(
                Arguments.of(employeesGrades1, "a.kowalski@techcorp.pl"),
                Arguments.of(employeesGrades2, "a.kowalski@techcorp.pl"),
                Arguments.of(employeesGrades3, "j.kowalski@techcorp.pl")
        );
    }

}
