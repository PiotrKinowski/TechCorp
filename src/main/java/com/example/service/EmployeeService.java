package com.example.service;

import com.example.model.Employee;
import com.example.model.CompanyStatistics;
import com.example.model.LastNameComparator;
import com.example.model.Position;

import java.time.LocalDate;
import java.util.*;

public class EmployeeService {
    private List<Employee> employees;

    public EmployeeService() {
        this.employees = new ArrayList<>();
    }
    public boolean addEmployee(Employee newEmployee) {
        if (newEmployee == null) return false;
        for (Employee employee : employees) {
            if (employee.getEmail().equals(newEmployee.getEmail())) {
                return false;
            }
        }
        employees.add(newEmployee);
        return true;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
    public List<Employee> findEmployeesByCompany(String company) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getCompany().equalsIgnoreCase(company)) {
                result.add(employee);
            }
        }
        return result;
    }
    public List<Employee> sortEmployeesByLastName() {
        List<Employee> sortedList = new ArrayList<>(employees);
        Collections.sort(sortedList, new LastNameComparator());
        return sortedList;
    }

    public Map<Position, List<Employee>> groupEmployeesByPosition() {
        Map<Position, List<Employee>> grouped = new HashMap<>();

        for (Employee employee : employees) {
            Position position = employee.getPosition();
            if (!grouped.containsKey(position)) {
                grouped.put(position, new ArrayList<>());
            }
            grouped.get(position).add(employee);
        }

        return grouped;
    }

    public Map<Position, Integer> countEmployeesByPosition() {
        Map<Position, Integer> counts = new HashMap<>();

        for (Employee employee : employees) {
            Position position = employee.getPosition();
            if (!counts.containsKey(position)) {
                counts.put(position, 0);
            }
            counts.put(position, counts.get(position) + 1);
        }

        return counts;
    }
    public List<Employee> validateSalaryConsistency() {
        List<Employee> inconsistentEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() < employee.getPosition().getBaseSalary()) {
                inconsistentEmployees.add(employee);
            }
        }
        return inconsistentEmployees;
    }

    public Map<String, CompanyStatistics> getCompanyStatistics() {
        Map<String, List<Employee>> employeesByCompany = new HashMap<>();

        for (Employee employee : employees) {
            String company = employee.getCompany();
            if (!employeesByCompany.containsKey(company)) {
                employeesByCompany.put(company, new ArrayList<>());
            }
            employeesByCompany.get(company).add(employee);
        }

        Map<String, CompanyStatistics> statistics = new HashMap<>();

        for (Map.Entry<String, List<Employee>> entry : employeesByCompany.entrySet()) {
            String company = entry.getKey();
            List<Employee> companyEmployees = entry.getValue();

            int employeeCount = companyEmployees.size();
            double totalSalary = 0.0;
            double maxSalary = -1;
            String highestPaidEmployee = "";

            for (Employee employee : companyEmployees) {
                totalSalary += employee.getSalary();
                if (employee.getSalary() > maxSalary) {
                    maxSalary = employee.getSalary();
                    highestPaidEmployee = employee.getFirstName() + " " + employee.getLastName();
                }
            }

            double averageSalary = employeeCount > 0 ? totalSalary / employeeCount : 0;
            statistics.put(company, new CompanyStatistics(employeeCount, averageSalary, highestPaidEmployee));
        }

        return statistics;
    }

    public int getEmployeeCount() {
        return employees.size();
    }

    public int calculateSeniority(Employee employee1, LocalDate current) {
        if (current.isAfter(employee1.getHireDate())){
            return current.getYear() - employee1.getHireDate().getYear();
        }
        return 0;
    }

    public List<Employee> filterBySeniorityRange(int min, int max, LocalDate current) {
        List<Employee> filteredEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            int seniority = calculateSeniority(employee, current);
            if (seniority >= min && seniority <= max) {
                filteredEmployees.add(employee);
            }
        }
        return filteredEmployees;
    }

    public List<Employee> findJubilees(LocalDate current) {
        List<Employee> jubilees = new ArrayList<>();
        for (Employee employee : employees) {
            int seniority = calculateSeniority(employee, current);
            if (seniority %10 == 0) {
                jubilees.add(employee);
            }
        }
        return jubilees;
    }
}