package com.example.testing.doubles;

import com.example.model.Employee;

public interface CommunicationService {
    void sendReminder(Employee employee, String message);
}