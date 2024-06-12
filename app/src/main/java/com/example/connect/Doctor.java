package com.example.connect;

public class Doctor {
    public String name;
    public String specialty;
    public String hospital;
    public String rating;
    public int imageResourceId;

    public Doctor(String name, String specialty, String hospital, String rating, int imageResourceId) {
        this.name = name;
        this.specialty = specialty;
        this.hospital = hospital;
        this.rating = rating;
        this.imageResourceId = imageResourceId;
    }
}
