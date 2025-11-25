package com.example.testing.doubles;

import com.example.model.Employee;
import com.example.model.Position;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskAssignmentServiceTest {

    @Test
    void shouldAssignTaskToEmployee() {
        // Arrange
        CalendarService calendarStub = new CalendarServiceStub();
        SkillRepositoryFake skillFake = new SkillRepositoryFake();
        AllocationSystemSpy allocationSpy = new AllocationSystemSpy();
        ConfigDummy configDummy = new ConfigDummy(); // Dummy - nieużywany w teście

        TaskAssignmentService service = new TaskAssignmentService(calendarStub, skillFake, allocationSpy);

        Employee expectedEmployee = new Employee("Jan", "Kowalski", "jan@example.com",
                "Company A", Position.PROGRAMISTA, 8000);

        Set<String> requiredSkills = Set.of("Java", "Spring");
        skillFake.addSkills("jan@example.com", requiredSkills);

        // Act
        Employee assignedEmployee = service.assignTask("TASK_001", requiredSkills,
                LocalDate.now(), LocalDate.now().plusDays(7));

        // Assert
        assertAll(
                () -> assertNotNull(assignedEmployee),
                () -> assertEquals(expectedEmployee.getEmail(), assignedEmployee.getEmail()),
                () -> assertEquals(1, allocationSpy.getAllocationCount())
        );
    }

    @Test
    void shouldNotAssignTaskNoRequiredSkills() {
        // Arrange
        CalendarServiceStub calendarStub = new CalendarServiceStub();
        SkillRepositoryFake skillFake = new SkillRepositoryFake();
        AllocationSystemSpy allocationSpy = new AllocationSystemSpy();

        TaskAssignmentService service = new TaskAssignmentService(calendarStub, skillFake, allocationSpy);

        Set<String> requiredSkills = Set.of("Python", "Java");

        // Act
        Employee assignedEmployee = service.assignTask("TASK_001", requiredSkills,
                LocalDate.now(), LocalDate.now().plusDays(7));

        // Assert
        assertAll(
                () -> assertNull(assignedEmployee),
                () -> assertEquals(0, allocationSpy.getAllocationCount())
        );
    }
}