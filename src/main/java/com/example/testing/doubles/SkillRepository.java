package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.Set;

public interface SkillRepository {
    void addSkills(String email, Set<String> skills);
    boolean hasSkills(Employee employee, Set<String> requiredSkills);
}