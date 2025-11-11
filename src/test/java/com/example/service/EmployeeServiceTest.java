package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();

        employee1 = new Employee("Jan", "Kowalski", "j.kowalski@techcorp.pl",
                "TechCorp", Position.PROGRAMISTA, 8500);
        employee2 = new Employee("Anna", "Nowak", "a.nowak@techcorp.pl",
                "TechCorp", Position.MANAGER, 13000);
        employee3 = new Employee("Janusz", "Kowalski", "j.kowalski@techcorp.pl",
                "InnaFirma", Position.MANAGER, 15000);
    }

    @Test
    @DisplayName("Dodaj unikalnych pracowników")
    void testAddEmployee_True() {
        boolean result = employeeService.addEmployee(employee1) && employeeService.addEmployee(employee2);

        assertTrue(result, "Powinien zwrócić true dla unikalnych pracowników");
        assertEquals(2, employeeService.getEmployeeCount(), "Liczba pracowników powinna wynosić 2");
    }

    @Test
    @DisplayName("Ten sam email")
    void testAddEmployee_False() {
        employeeService.addEmployee(employee1);

        boolean result = employeeService.addEmployee(employee3);

        assertFalse(result, "Powinien zwrócić false dla duplikatu emaila");
        assertEquals(1, employeeService.getEmployeeCount(), "Liczba pracowników nie powinna się zmienić");
    }

    @Test
    @DisplayName("Dodawanie null")
    void testAddEmployee_Null() {
        boolean result = employeeService.addEmployee(null);

        assertFalse(result, "Powinien zwrócić false dla null");
        assertEquals(0, employeeService.getEmployeeCount(), "Liczba pracowników nie powinna się zmienić");
    }
}
