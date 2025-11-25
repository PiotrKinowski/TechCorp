package com.example.testing.doubles;

import com.example.model.Employee;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TaskAssignmentService {
    private final CalendarService calendarService;
    private final SkillRepository skillRepository;
    private final AllocationSystem allocationSystem;

    public TaskAssignmentService(CalendarService calendarService,
                                 SkillRepository skillRepository,
                                 AllocationSystem allocationSystem) {
        this.calendarService = calendarService;
        this.skillRepository = skillRepository;
        this.allocationSystem = allocationSystem;
    }

    public Employee assignTask(String taskId, Set<String> requiredSkills,
                               LocalDate startDate, LocalDate endDate) {
        List<Employee> availableEmployees = calendarService.getAvailableEmployees(startDate, endDate);

        for (Employee employee : availableEmployees) {
            if (skillRepository.hasSkills(employee, requiredSkills)) {
                allocationSystem.allocate(employee, taskId, startDate, endDate);
                return employee;
            }
        }
        return null;
    }
}