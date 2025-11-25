package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class CommunicationServiceMock implements CommunicationService {
    // Udaje prawdziwą usługę
    private List<ReminderRecord> sentReminders;
    private int expectedCallCount;

    public CommunicationServiceMock(int expectedCallCount) {
        this.sentReminders = new ArrayList<>();
        this.expectedCallCount = expectedCallCount;
    }

    @Override
    public void sendReminder(Employee employee, String message) {
        sentReminders.add(new ReminderRecord(employee, message));
    }

    // Weryfikacja czy metoda została wywołana oczekiwaną liczbę razy
    public void verify() {
        if (sentReminders.size() != expectedCallCount) {
            throw new AssertionError(
                    String.format("Expected %d calls, but got %d", expectedCallCount, sentReminders.size())
            );
        }
    }

    public static class ReminderRecord {
        public final Employee employee;
        public final String message;

        public ReminderRecord(Employee employee, String message) {
            this.employee = employee;
            this.message = message;
        }
    }
}