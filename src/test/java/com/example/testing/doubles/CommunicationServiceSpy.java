package com.example.testing.doubles;

import com.example.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class CommunicationServiceSpy implements CommunicationService {
    // Lista zapisująca wszystkie przypomnienia wraz z odbiorcami i wiadomościami
    private List<ReminderRecord> sentReminders;

    public CommunicationServiceSpy() {
        sentReminders = new ArrayList<>();
    }

    @Override
    public void sendReminder(Employee employee, String message) {
        sentReminders.add(new ReminderRecord(employee, message));
    }

    public int getSentRemindersCount() {
        return sentReminders.size();
    }

    public List<ReminderRecord> getSentReminders() {
        return new ArrayList<>(sentReminders);
    }

    public boolean wasReminderSentTo(String email) {
        return sentReminders.stream()
                .anyMatch(record -> record.employee.getEmail().equals(email));
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