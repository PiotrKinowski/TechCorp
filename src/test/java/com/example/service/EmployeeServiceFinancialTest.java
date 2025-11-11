package com.example.service;

import com.example.model.CompanyStatistics;
import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceFinancialTest {

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
    @DisplayName("Średnie wynagrodzenie")
    void testAverageSalary() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");
        CompanyStatistics innafirmaStats = statistics.get("InnaFirma");

        assertAll(
                () -> assertEquals(8000.0, techcorpStats.getAverageSalary(), 0.01,
                        "Średnia pensja w TechCorp powinna wynosić 8000"),
                () -> assertEquals(9000.0, innafirmaStats.getAverageSalary(), 0.01,
                        "Średnia pensja w InnaFirma powinna wynosić 9000")
        );
    }

    @Test
    @DisplayName("Najwyższa pensja")
    void testHighestSalary() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");
        CompanyStatistics innafirmaStats = statistics.get("InnaFirma");

        assertAll(
                () -> assertEquals("Anna Nowak", techcorpStats.getHighestPaidEmployee(),
                        "Najlepiej zarabiającym pracownikiem w firmie TechCorp powinna być Anna Nowak"),
                () -> assertEquals("Piotr Wiśniewski", innafirmaStats.getHighestPaidEmployee(),
                        "Najlepiej zarabiającym pracownikiem w firmie InnaFirma powinien być Piotr Wiśniewski")
        );
    }

    @Test
    @DisplayName("Średnie wynagrodzenie jeden pracownik")
    void testAverageSalary_OneEmployee() {
        employeeService.addEmployee(employee1);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");

        assertEquals(7500.0, techcorpStats.getAverageSalary(), 0.01,
                "Średnia pensja w TechCorp powinna wynosić 7500");
    }

    @Test
    @DisplayName("Najwyższa pensja jeden pracownik")
    void testHighestSalary_OneEmployee() {
        employeeService.addEmployee(employee1);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");

        assertEquals("Jan Kowalski", techcorpStats.getHighestPaidEmployee(),
                "Najlepiej zarabiającym pracownikiem w firmie TechCorp powinien być Jan Kowalski");
    }

    @Test
    @DisplayName("Średnie wynagrodzenie identyczna pensja")
    void testAverageSalary_SameSalary() {
        Employee samesalary = new Employee("Adam", "Małysz", "a.malysz@techcorp.com",
                "TechCorp", Position.PROGRAMISTA, 7500.0);
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(samesalary);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");

        assertEquals(7500.0, techcorpStats.getAverageSalary(), 0.01,
                "Średnia pensja w TechCorp powinna wynosić 7500");
    }

    @Test
    @DisplayName("Najwyższa pensja identyczna pensja")
    void testHighestSalary_SameSalary() {
        Employee samesalary = new Employee("Adam", "Małysz", "a.malysz@techcorp.com",
                "TechCorp", Position.PROGRAMISTA, 7500.0);
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(samesalary);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");

        assertEquals("Jan Kowalski", techcorpStats.getHighestPaidEmployee(),
                "Najlepiej zarabiającym pracownikiem w firmie TechCorp powinien być Jan Kowalski");
    }

    @Test
    @DisplayName("Powinien obsłużyć pustą listę pracowników przy statystykach firm")
    void testGetCompanyStatistics_Empty() {
        EmployeeService employeeService = new EmployeeService();

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();

        assertTrue(statistics.isEmpty(), "Powinien zwrócić pustą mapę");
    }

    @Test
    @DisplayName("Statystyki firmy bez pracowników")
    void testGetCompanyStatistics_EmptyCompany() {
        employeeService.addEmployee(employee3);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");

        assertNull(techcorpStats, "Statystyki nie istniejącej firmy nie powinny istnieć");
    }

    @Test
    @DisplayName("Statystyki firmy - toString")
    void testToString() {
        employeeService.addEmployee(employee1);
        employeeService.addEmployee(employee2);
        employeeService.addEmployee(employee3);
        employeeService.addEmployee(employee4);

        Map<String, CompanyStatistics> statistics = employeeService.getCompanyStatistics();
        CompanyStatistics techcorpStats = statistics.get("TechCorp");

        assertEquals(techcorpStats.toString(), "Pracowników: 3, Średnia pensja: 8000,00 PLN, Najlepiej zarabia: Anna Nowak", "Statystyki nie istniejącej firmy nie powinny istnieć");
    }
}
