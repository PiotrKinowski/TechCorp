package org.example.service;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.CompanyStatistics;

import java.util.*;

public class EmployeeService {
    private List<Employee> employees;

    public EmployeeService() {
        this.employees = new ArrayList<>();
    }

    // Zachowujemy metody z CompanySystem, ale zmieniamy nazwę klasy
    public boolean addEmployee(Employee newEmployee) {
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

    // Zachowujemy inne metody z CompanySystem jeśli były...
    public List<Employee> findEmployeesByCompany(String company) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getCompany().equalsIgnoreCase(company)) {
                result.add(employee);
            }
        }
        return result;
    }

    // DODAJEMY NOWE METODY WYMAGANE W ROZSZERZENIU:

    /**
     * Zwraca listę pracowników z wynagrodzeniem niższym niż bazowa stawka ich stanowiska
     */
    public List<Employee> validateSalaryConsistency() {
        List<Employee> inconsistentEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() < employee.getPosition().getBaseSalary()) {
                inconsistentEmployees.add(employee);
            }
        }
        return inconsistentEmployees;
    }

    /**
     * Zwraca statystyki dla każdej firmy
     */
    public Map<String, CompanyStatistics> getCompanyStatistics() {
        Map<String, List<Employee>> employeesByCompany = new HashMap<>();

        // Grupowanie pracowników według firmy
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

    // Pomocnicze metody
    public int getEmployeeCount() {
        return employees.size();
    }
}