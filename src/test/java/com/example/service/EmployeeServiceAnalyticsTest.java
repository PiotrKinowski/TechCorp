package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceAnalyticsTest {

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

    @Test
    @DisplayName("Wszyscy pracownicy")
    void testGetAllEmployees() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size(), "Powinien zwrócić 2 pracowników");
        assertTrue(employees.contains(employee1), "Lista powinna zawierać employee1");
        assertTrue(employees.contains(employee2), "Lista powinna zawierać employee2");
    }

    @Test
    @DisplayName("Wyszukiwanie pracowników po firmie")
    void testFindEmployeesByCompany_Correct() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        List<Employee> techcorpEmployees = employeeService.findEmployeesByCompany("TechCorp");
        List<Employee> innaFirmaEmployees = employeeService.findEmployeesByCompany("InnaFirma");
        List<Employee> unknownEmployees = employeeService.findEmployeesByCompany("Nieistniejąca");

        assertAll(
                () -> assertEquals(3, techcorpEmployees.size(), "Powinien znaleźć 3 pracowników TechCorp"),
                () -> assertEquals(1, innaFirmaEmployees.size(), "Powinien znaleźć 1 pracownika InnaFirma"),
                () -> assertEquals(0, unknownEmployees.size(), "Powinien zwrócić pustą listę dla nieistniejącej firmy"),
                () -> assertTrue(techcorpEmployees.contains(employee1), "Powinien zawierać employee1"),
                () -> assertTrue(techcorpEmployees.contains(employee2), "Powinien zawierać employee2"),
                () -> assertTrue(techcorpEmployees.contains(employee4), "Powinien zawierać employee4"),
                () -> assertTrue(innaFirmaEmployees.contains(employee3), "Powinien zawierać employee3")
        );
    }

    @Test
    @DisplayName("Wielkość liter nie powinna wpływać na wyszukiwanie po firmie")
    void testFindEmployeesByCompany_Capitals() {
        employeeService.addEmployee(employee1);

        List<Employee> employees1 = employeeService.findEmployeesByCompany("techcorp");
        List<Employee> employees2 = employeeService.findEmployeesByCompany("TECHCORP");
        List<Employee> employees3 = employeeService.findEmployeesByCompany("TechCorp");

        assertAll(
                () -> assertEquals(1, employees1.size(), "Powinien znaleźć pracownika dla 'techcorp'"),
                () -> assertEquals(1, employees2.size(), "Powinien znaleźć pracownika dla 'TECHCORP'"),
                () -> assertEquals(1, employees3.size(), "Powinien znaleźć pracownika dla 'TechCorp'")
        );
    }

    @Test
    @DisplayName("Sortowanie po nazwisku")
    void testSortEmployeesByName() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        List<Employee> expected = new ArrayList<>();
        expected.add(employee1);
        expected.add(employee4);
        expected.add(employee2);
        expected.add(employee3);

        List<Employee> result = employeeService.sortEmployeesByLastName();

        assertEquals(expected, result, "Lista powinna być posortowana alfabetycznie po nazwiskach");
    }

    @Test
    @DisplayName("Sortowanie pustej listy")
    void testSortEmployeesByName_Empty() {

        List<Employee> result = employeeService.sortEmployeesByLastName();

        assertTrue(result.isEmpty(), "Lista powinna być pusta");
    }

    @Test
    @DisplayName("Grupowanie po stanowisku")
    void testGroupEmployeesByPosition() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        Map<Position, List<Employee>> expected = new HashMap<>();
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            Position position = employee.getPosition();
            if (!expected.containsKey(position)) {
                expected.put(position, new ArrayList<>());
            }
            expected.get(position).add(employee);
        }

        Map<Position,List<Employee>> result = employeeService.groupEmployeesByPosition();

        assertEquals(expected, result, "Błąd w grupowaniu po stanowisku");
    }

    @Test
    @DisplayName("Grupowanie pustej listy")
    void testGroupEmployeesByPosition_Empty() {

        Map<Position,List<Employee>> result = employeeService.groupEmployeesByPosition();

        assertTrue(result.isEmpty(), "Mapa powinna być pusta");
    }

    @Test
    @DisplayName("Zliczanie pracowników na stanowisku")
    void testCountEmployeesByPosition() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        Map<Position, Integer> expected = new HashMap<>();
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            Position position = employee.getPosition();
            if (!expected.containsKey(position)) {
                expected.put(position, 0);
            }
            expected.put(position, expected.get(position) + 1);
        }

        Map<Position,Integer> result = employeeService.countEmployeesByPosition();

        assertEquals(expected, result, "Błąd w liczeniu na stanowisku");
    }

    @Test
    @DisplayName("Zliczanie z pustej listy")
    void testCountEmployeesByPosition_Empty() {

        Map<Position,List<Employee>> result = employeeService.groupEmployeesByPosition();

        assertTrue(result.isEmpty(), "Mapa powinna być pusta");
    }
}