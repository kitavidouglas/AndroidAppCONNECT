package com.example.connect;



public class Appointment {
    public String doctorName;
    public String doctorSpecialty;
    public String doctorHospital;
    public String doctorRating;
    public int doctorImageResourceId;
    public String date;
    public String time;

    public Appointment() {
        // Default constructor required for Firebase
    }

    public Appointment(String doctorName, String doctorSpecialty, String doctorHospital, String doctorRating, int doctorImageResourceId, String date, String time) {
        this.doctorName = doctorName;
        this.doctorSpecialty = doctorSpecialty;
        this.doctorHospital = doctorHospital;
        this.doctorRating = doctorRating;
        this.doctorImageResourceId = doctorImageResourceId;
        this.date = date;
        this.time = time;
    }
}
