package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.ImportSummary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportService {
    private EmployeeService employeeService;

    public ImportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ImportSummary importFromCsv(String filePath) {
        List<String> errors = new ArrayList<>();
        int importedCount = 0;
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            lineNumber++;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    String[] fields = line.split(",");
                    if (fields.length != 6) {
                        errors.add("Linia " + lineNumber + ": Nieprawidłowa liczba pól");
                        continue;
                    }

                    String firstName = fields[0].trim();
                    String lastName = fields[1].trim();
                    String email = fields[2].trim();
                    String company = fields[3].trim();
                    String positionStr = fields[4].trim().toUpperCase();
                    String salaryStr = fields[5].trim();

                    Position position;
                    try {
                        position = Position.valueOf(positionStr);
                    } catch (IllegalArgumentException e) {
                        errors.add("Linia " + lineNumber + ": Nieprawidłowe stanowisko: " + positionStr);
                        continue;
                    }

                    double salary;
                    try {
                        salary = Double.parseDouble(salaryStr);
                    } catch (NumberFormatException e) {
                        errors.add("Linia " + lineNumber + ": Nieprawidłowy format wynagrodzenia: " + salaryStr);
                        continue;
                    }

                    if (salary <= 0) {
                        errors.add("Linia " + lineNumber + ": Wynagrodzenie musi być dodatnie: " + salary);
                        continue;
                    }

                    Employee employee = new Employee(firstName, lastName, email, company, position, salary);
                    boolean added = employeeService.addEmployee(employee);

                    if (added) {
                        importedCount++;
                    } else {
                        errors.add("Linia " + lineNumber + ": Pracownik z emailem " + email + " już istnieje");
                    }

                } catch (Exception e) {
                    errors.add("Linia " + lineNumber + ": Nieoczekiwany błąd: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            errors.add("Błąd odczytu pliku: " + e.getMessage());
        }

        return new ImportSummary(importedCount, errors);
    }
}