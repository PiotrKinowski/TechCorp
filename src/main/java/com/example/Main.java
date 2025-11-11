package com.example;

import com.example.exception.ApiException;
import com.example.model.CompanyStatistics;
import com.example.model.Employee;
import com.example.model.ImportSummary;
import com.example.service.ApiService;
import com.example.service.EmployeeService;
import com.example.service.ImportService;
import com.example.model.*;
import com.example.service.*;
import com.example.exception.*;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ROZSZERZONY SYSTEM TECHCORP - CSV + API ===\n");

        EmployeeService employeeService = new EmployeeService();
        ImportService importService = new ImportService(employeeService);
        ApiService apiService = new ApiService();

        System.out.println("1. IMPORT Z PLIKU CSV:");
        ImportSummary csvResult = importService.importFromCsv("employees.csv");
        System.out.println("Wynik importu: " + csvResult);

        if (!csvResult.getErrors().isEmpty()) {
            System.out.println("Szczegóły błędów:");
            for (String error : csvResult.getErrors()) {
                System.out.println("   - " + error);
            }
        }

        System.out.println("\n2. IMPORT Z REST API:");
        try {
            List<Employee> apiEmployees = apiService.fetchEmployeesFromApi();
            int addedCount = 0;
            for (Employee employee : apiEmployees) {
                if (employeeService.addEmployee(employee)) {
                    addedCount++;
                }
            }
            System.out.println("Dodano " + addedCount + " pracowników z API");
        } catch (ApiException e) {
            System.out.println("Błąd API: " + e.getMessage());
        }

        System.out.println("\n4. WALIDACJA WYNAGRODZEŃ:");
        List<Employee> inconsistent = employeeService.validateSalaryConsistency();
        if (inconsistent.isEmpty()) {
            System.out.println("✓ Wszystkie wynagrodzenia są poprawne");
        } else {
            System.out.println("⚠ Pracownicy z za niską pensją:");
            for (Employee emp : inconsistent) {
                System.out.println("   - " + emp);
            }
        }

        System.out.println("\n5. STATYSTYKI FIRM:");
        Map<String, CompanyStatistics> stats = employeeService.getCompanyStatistics();
        for (Map.Entry<String, CompanyStatistics> entry : stats.entrySet()) {
            System.out.println("   " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\n=== KONIEC ===");
    }
}