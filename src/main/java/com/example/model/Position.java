package com.example.model;

public enum Position {
    PREZES("Prezes", 25000, 1),
    WICEPREZES("Wiceprezes", 18000, 2),
    MANAGER("Manager", 12000, 3),
    PROGRAMISTA("Programista", 8000, 4),
    STAZYSTA("Stażysta", 3000, 5);

    private final String name;
    private final double baseSalary;
    private final int hierarchyLevel;

    Position(String name, double baseSalary, int hierarchyLevel) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.hierarchyLevel = hierarchyLevel;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    @Override
    public String toString() {
        return name;
    }
}