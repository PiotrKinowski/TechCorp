package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.List;

public class FormatterStub implements Formatter {
    private String predefinedOutput;

    // Zawsze zwraca ten sam predefiniowany tekst
    public FormatterStub() {
        this.predefinedOutput = "PREDEFINED_DATA";
    }

    @Override
    public String format(List<Employee> employees, String format) {
        return predefinedOutput;
    }
}