package com.example.connect;

import com.example.connect.Appointment;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String password;
    private String additionalField;
    private List<Appointment> appointments;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appointments = new ArrayList<>();
    }

    public User(String username, String email, String password, String additionalField) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.additionalField = additionalField;
        this.appointments = new ArrayList<>();
    }

    // Getters and setters for all fields
    // (Omitted for brevity)

    public String getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
}
