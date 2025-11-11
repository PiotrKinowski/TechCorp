package com.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    @DisplayName("Bazowe pensje")
    void testBaseSalaries() {
        assertAll(
                () -> assertEquals(25000, Position.PREZES.getBaseSalary(), "Bazowa pensja Prezesa powinna wynosić 25000"),
                () -> assertEquals(18000, Position.WICEPREZES.getBaseSalary(), "Bazowa pensja Wiceprezesa powinna wynosić 18000"),
                () -> assertEquals(12000, Position.MANAGER.getBaseSalary(), "Bazowa pensja Managera powinna wynosić 12000"),
                () -> assertEquals(8000, Position.PROGRAMISTA.getBaseSalary(), "Bazowa pensja Programisty powinna wynosić 8000"),
                () -> assertEquals(3000, Position.STAZYSTA.getBaseSalary(), "Bazowa pensja Stażysty powinna wynosić 3000")
        );
    }

    @Test
    @DisplayName("Poziomy hierarchii")
    void testHierarchyLevels() {
        assertAll(
                () -> assertEquals(1, Position.PREZES.getHierarchyLevel(), "Prezes powinien mieć poziom hierarchii 1"),
                () -> assertEquals(2, Position.WICEPREZES.getHierarchyLevel(), "Wiceprezes powinien mieć poziom hierarchii 2"),
                () -> assertEquals(3, Position.MANAGER.getHierarchyLevel(), "Manager powinien mieć poziom hierarchii 3"),
                () -> assertEquals(4, Position.PROGRAMISTA.getHierarchyLevel(), "Programista powinien mieć poziom hierarchii 4"),
                () -> assertEquals(5, Position.STAZYSTA.getHierarchyLevel(), "Stażysta powinien mieć poziom hierarchii 5")
        );
    }
}