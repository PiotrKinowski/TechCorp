package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.List;

public class EmployeeExportService {
    private final EmployeeRepository employeeRepository;
    private final Formatter formatter;
    private final FileSystem fileSystem;

    public EmployeeExportService(EmployeeRepository employeeRepository,
                                 Formatter formatter,
                                 FileSystem fileSystem) {
        this.employeeRepository = employeeRepository;
        this.formatter = formatter;
        this.fileSystem = fileSystem;
    }

    public boolean exportEmployees(String filePath, String format) {
        List<Employee> employees = employeeRepository.findAll();
        String formattedData = formatter.format(employees, format);
        fileSystem.write(filePath, formattedData);
        return true;
    }
}