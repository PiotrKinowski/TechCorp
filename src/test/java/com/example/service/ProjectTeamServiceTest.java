package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTeamServiceTest {

    private ProjectTeamService teamService;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
        teamService = new ProjectTeamService(employeeService);

        Employee manager = new Employee("Manager", "One", "manager@company.com",
                "Company", Position.MANAGER, 12000);
        Employee vicePresident = new Employee("Vice", "President", "vicepresident@company.com",
                "Company", Position.WICEPREZES, 18000);
        Employee programmer1 = new Employee("Programmer", "One", "prog1@company.com",
                "Company", Position.PROGRAMISTA, 8000);
        Employee programmer2 = new Employee("Programmer", "Two", "prog2@company.com",
                "Company", Position.PROGRAMISTA, 8500);
        Employee programmer3 = new Employee("Programmer", "Three", "prog3@company.com",
                "Company", Position.PROGRAMISTA, 8200);
        Employee programmer4 = new Employee("Programmer", "Four", "prog4@company.com",
                "Company", Position.PROGRAMISTA, 8300);
        Employee programmer5 = new Employee("Programmer", "Five", "prog5@company.com",
                "Company", Position.PROGRAMISTA, 8400);
        Employee intern = new Employee("Intern", "One", "intern@company.com",
                "Company", Position.STAZYSTA, 3000);
        Employee employee1 = new Employee("Employee", "One", "employee1@company.com",
                "Company", Position.PROGRAMISTA, 8000);

        employeeService.addEmployee(manager);
        employeeService.addEmployee(vicePresident);
        employeeService.addEmployee(programmer1);
        employeeService.addEmployee(programmer2);
        employeeService.addEmployee(programmer3);
        employeeService.addEmployee(programmer4);
        employeeService.addEmployee(programmer5);
        employeeService.addEmployee(intern);
        employeeService.addEmployee(employee1);
    }

    @ParameterizedTest(name = "Czy stworzenie zespołu z pracowników o emailach {0} powinno być możliwe: {1}")
    @MethodSource("provideTeamCompositions")
    void createTeam_Validation(List<String> emails, boolean expected) {
        // Arrange
        String teamName = "TestTeam";

        // Act
        boolean result = teamService.createTeam(teamName, emails);

        // Assert
        assertThat(result)
                .as("Walidacja tworzenia zespołu z pracownikami %s powinna zwrócić: %s", emails, expected)
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideTeamCompositions() {
        return Stream.of(
                Arguments.of(Arrays.asList("manager@company.com", "prog1@company.com", "prog2@company.com"), true),
                Arguments.of(Arrays.asList("manager@company.com", "prog1@company.com", "intern@company.com"), true),
                Arguments.of(Arrays.asList("vicepresident@company.com", "manager@company.com", "prog1@company.com"), true),
                Arguments.of(Arrays.asList("prog1@company.com", "prog2@company.com"), false),
                Arguments.of(Arrays.asList("manager@company.com"), false),
                Arguments.of(Arrays.asList("manager@company.com", "prog1@company.com", "prog2@company.com",
                        "prog3@company.com", "prog4@company.com", "prog5@company.com"), false),
                Arguments.of(Arrays.asList("manager@company.com", "manager@company.com", "prog1@company.com"), true),
                Arguments.of(Arrays.asList("manager@company.com", "prog1@company.com", "prog2@company.com", "intern@company.com"), true)
        );
    }

    @Test
    void createTeam_ShouldAddAll() {
        // Arrange
        String teamName = "DreamTeam";
        List<String> employeeEmails = Arrays.asList("manager@company.com", "prog1@company.com", "prog2@company.com");

        // Act
        boolean result = teamService.createTeam(teamName, employeeEmails);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result)
                .as("Stworzenie zespołu powinno przebiec pomyślnie")
                .isTrue();

        softly.assertThat(teamService.teamExists(teamName))
                .as("Zespół powinien istnieć")
                .isTrue();

        softly.assertThat(teamService.getTeamMembers(teamName))
                .as("Wszyscy członkowie powinni zostać dodani do zespołu")
                .hasSize(3);

        softly.assertAll();
    }

    @Test
    void createTeam_InvalidEmployee_Fail() {
        // Arrange
        String teamName = "InvalidTeam";
        List<String> employeeEmails = Arrays.asList("manager@company.com", "nonexistent@company.com");

        // Act
        boolean result = teamService.createTeam(teamName, employeeEmails);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(result)
                .as("Stworzenie zespołu z nieistniejącym pracownikiem nie powinno być możliwe")
                .isFalse();

        softly.assertThat(teamService.teamExists(teamName))
                .as("Zespół nie powinien istnieć")
                .isFalse();

        softly.assertAll();
    }

    @Test
    void createTeam_DuplicateTeam_Fail() {
        // Arrange
        String teamName = "ExistingTeam";
        List<String> firstTeamEmails = Arrays.asList("manager@company.com", "prog1@company.com");
        List<String> secondTeamEmails = Arrays.asList("vicepresident@company.com", "prog2@company.com");

        teamService.createTeam(teamName, firstTeamEmails);

        // Act
        boolean result = teamService.createTeam(teamName, secondTeamEmails);

        // Assert
        assertThat(result)
                .as("Stworzenie dwóch zespołów z identyczną nazwą nie powinno być możliwe")
                .isFalse();
    }

    @Test
    void createTeam_EmptyList_Fail() {
        // Arrange
        String teamName = "EmptyTeam";
        List<String> employeeEmails = Arrays.asList();

        // Act
        boolean result = teamService.createTeam(teamName, employeeEmails);

        // Assert
        assertThat(result)
                .as("Stworzenie pustego zespołu nie powinno być możliwe")
                .isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideTeamTransferScenarios")
    void transferEmployee_HandleConflicts(List<String> teamA, List<String> teamB, boolean expectedSuccess) {
        // Arrange
        teamService.createTeam("TeamA", teamA);
        teamService.createTeam("TeamB", teamB);
        String employeeEmail = "prog1@company.com";

        // Act
        boolean result = teamService.transferEmployee(employeeEmail, "TeamA", "TeamB");

        // Assert
        assertThat(result).isEqualTo(expectedSuccess);
    }

    private static Stream<Arguments> provideTeamTransferScenarios() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList("manager@company.com", "prog1@company.com", "prog2@company.com"),
                        Arrays.asList("manager@company.com", "prog1@company.com"),
                        true
                ),
                Arguments.of(
                        Arrays.asList("manager@company.com", "prog1@company.com"),
                        Arrays.asList("manager@company.com", "prog1@company.com", "prog2@company.com", "prog3@company.com", "prog4@company.com"),
                        false
                )
        );
    }

    @Test
    void getTeamMembers_Correct() {
        // Arrange
        String teamName = "TestTeam";
        List<String> employeeEmails = Arrays.asList("manager@company.com", "prog1@company.com");
        teamService.createTeam(teamName, employeeEmails);

        // Act
        List<Employee> teamMembers = teamService.getTeamMembers(teamName);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(teamMembers)
                .as("Powinno zwrócić 2 członków")
                .hasSize(2);

        softly.assertThat(teamMembers)
                .as("Oczekiwane adresy email członków to: manager@company.com, prog1@company.com")
                .extracting(Employee::getEmail)
                .containsExactlyInAnyOrder("manager@company.com", "prog1@company.com");

        softly.assertAll();
    }

    @Test
    void teamExists_Correct() {
        // Arrange
        String teamName = "ExistingTeam";
        List<String> employeeEmails = Arrays.asList("manager@company.com", "prog1@company.com");
        teamService.createTeam(teamName, employeeEmails);

        // Act & Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(teamService.teamExists(teamName))
                .as("Zespół ExistingTeam powinien istnieć.")
                .isTrue();

        softly.assertThat(teamService.teamExists("NonExistentTeam"))
                .as("Zespół NonExistentTeam nie powinien istnieć.")
                .isFalse();

        softly.assertAll();
    }

    @Test
    void createTeam_NullName_Fail() {
        // Arrange
        List<String> employeeEmails = Arrays.asList("manager@company.com", "prog1@company.com");

        // Act
        boolean result = teamService.createTeam(null, employeeEmails);

        // Assert
        assertThat(result)
                .as("Stworzenie drużyny z nazwą null nie powinno się udać")
                .isFalse();
    }

    @Test
    void createTeam_EmptyName_Fail() {
        // Arrange
        List<String> employeeEmails = Arrays.asList("manager@company.com", "prog1@company.com");

        // Act
        boolean result = teamService.createTeam("", employeeEmails);

        // Assert
        assertThat(result)
                .as("Team creation should fail with empty team name")
                .isFalse();
    }

    @Test
    void createTeam_NullList_Fail() {
        // Arrange
        String teamName = "TestTeam";

        // Act
        boolean result = teamService.createTeam(teamName, null);

        // Assert
        assertThat(result)
                .as("Stworzenie drużyny z listą pracowników null nie powinno się udać")
                .isFalse();
    }

    @Test
    void getTeamMembers_NonExistentTeam_EmptyList() {
        // Act
        List<Employee> members = teamService.getTeamMembers("NonExistentTeam");

        // Assert
        assertThat(members)
                .as("Powinno zwrócić pustą listę dla nieistniejącego zespołu")
                .isEmpty();
    }

    @Test
    void removeTeam_Correct() {
        // Arrange
        String teamName = "TeamToRemove";
        List<String> employeeEmails = Arrays.asList("manager@company.com", "prog1@company.com");
        teamService.createTeam(teamName, employeeEmails);

        // Act
        boolean removalResult = teamService.removeTeam(teamName);
        boolean existsAfterRemoval = teamService.teamExists(teamName);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(removalResult)
                .as("Usunięcie zespołu powinno się udać")
                .isTrue();

        softly.assertThat(existsAfterRemoval)
                .as("Zespół nie powinien istnieć po usunięciu")
                .isFalse();

        softly.assertAll();
    }

    @Test
    void getAllTeamNames_Correct() {
        // Arrange
        teamService.createTeam("Team1", Arrays.asList("manager@company.com", "prog1@company.com"));
        teamService.createTeam("Team2", Arrays.asList("vicepresident@company.com", "prog2@company.com"));

        // Act
        Set<String> teamNames = teamService.getAllTeamNames();

        // Assert
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(teamNames.size())
                .as("Powinno zwrócić 2 zespoły")
                .isEqualTo(2);

        softly.assertThat(teamNames)
                .as("Powinno zwrócić Team1 i Team2")
                .containsExactlyInAnyOrder("Team1", "Team2");

        softly.assertAll();
    }
}