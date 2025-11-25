package com.example.testing.doubles;

import com.example.model.Employee;
import com.example.model.Position;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryFake implements EmployeeRepository {
    private List<Employee> employees;

    // Fałszywe repozytorium. Przechowuje listę pracowników w pamięci zamiast bazy danych
    public EmployeeRepositoryFake() {
        employees = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        employees.add(new Employee("Jan", "Kowalski", "jan@example.com",
                "Company A", Position.PROGRAMISTA, 8000));
        employees.add(new Employee("Anna", "Nowak", "anna@example.com",
                "Company A", Position.MANAGER, 12000));
        employees.add(new Employee("Piotr", "Wiśniewski", "piotr@example.com",
                "Company B", Position.PROGRAMISTA, 8500));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void clear() {
        employees.clear();
    }
}