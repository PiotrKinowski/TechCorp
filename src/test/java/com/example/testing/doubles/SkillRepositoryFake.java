package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.*;

public class SkillRepositoryFake implements SkillRepository {
    private Map<String, Set<String>> employeeSkills;

    // Fałszywe repozytorium. Przechowuje mapę umiejętności w pamięci zamiast bazy danych
    public SkillRepositoryFake() {
        employeeSkills = new HashMap<>();
    }

    @Override
    public void addSkills(String email, Set<String> skills) {
        employeeSkills.put(email, new HashSet<>(skills));
    }

    @Override
    public boolean hasSkills(Employee employee, Set<String> requiredSkills) {
        Set<String> employeeSkillSet = employeeSkills.get(employee.getEmail());
        if (employeeSkillSet == null) {
            return false;
        }
        return employeeSkillSet.containsAll(requiredSkills);
    }
}