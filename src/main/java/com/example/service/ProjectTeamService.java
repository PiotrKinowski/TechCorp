package com.example.service;

import com.example.model.Employee;
import com.example.model.Position;

import java.util.*;

public class ProjectTeamService {
    private EmployeeService employeeService;
    private Map<String, List<String>> teams;

    public ProjectTeamService(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.teams = new HashMap<>();
    }

    public boolean createTeam(String teamName, List<String> employeeEmails) {
        if (teamName == null || teamName.trim().isEmpty() || employeeEmails == null) {
            return false;
        }

        if (teams.containsKey(teamName)) {
            return false;
        }

        if (employeeEmails.size() < 2 || employeeEmails.size() > 5) {
            return false;
        }

        List<Employee> teamEmployees = new ArrayList<>();
        for (String email : employeeEmails) {
            Employee employee = findEmployeeByEmail(email);
            if (employee == null) {
                return false;
            }
            teamEmployees.add(employee);
        }

        if (!isValidTeamComposition(teamEmployees)) {
            return false;
        }

        teams.put(teamName, new ArrayList<>(employeeEmails));
        return true;
    }

    private boolean isValidTeamComposition(List<Employee> teamEmployees) {
        boolean hasManager = false;
        Set<Position> positions = new HashSet<>();

        for (Employee employee : teamEmployees) {
            Position position = employee.getPosition();
            positions.add(position);

            if (position == Position.MANAGER ||
                    position == Position.WICEPREZES ||
                    position == Position.PREZES) {
                hasManager = true;
            }
        }

        return hasManager && positions.size() >= 2;
    }

    public boolean transferEmployee(String employeeEmail, String fromTeam, String toTeam) {
        if (!teams.containsKey(fromTeam) || !teams.containsKey(toTeam)) {
            return false;
        }

        List<String> sourceTeam = teams.get(fromTeam);
        List<String> targetTeam = teams.get(toTeam);

        if (!sourceTeam.contains(employeeEmail) || targetTeam.size() >= 5) {
            return false;
        }

        sourceTeam.remove(employeeEmail);
        targetTeam.add(employeeEmail);

        return isValidTeamComposition(getTeamMembers(fromTeam)) &&
                isValidTeamComposition(getTeamMembers(toTeam));
    }

    public boolean teamExists(String teamName) {
        return teams.containsKey(teamName);
    }

    public List<Employee> getTeamMembers(String teamName) {
        if (!teams.containsKey(teamName)) {
            return Collections.emptyList();
        }

        List<Employee> members = new ArrayList<>();
        for (String email : teams.get(teamName)) {
            Employee employee = findEmployeeByEmail(email);
            if (employee != null) {
                members.add(employee);
            }
        }
        return members;
    }

    public Set<String> getAllTeamNames() {
        return new HashSet<>(teams.keySet());
    }

    public boolean removeTeam(String teamName) {
        if (teams.containsKey(teamName)) {
            teams.remove(teamName);
            return true;
        }
        return false;
    }

    private Employee findEmployeeByEmail(String email) {
        for (Employee employee : employeeService.getAllEmployees()) {
            if (employee.getEmail().equals(email)) {
                return employee;
            }
        }
        return null;
    }
}