package com.example.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDetailActivity extends AppCompatActivity {

    private Button bookAppointmentButton;
    private String selectedDate = "";
    private String selectedTime = "";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        // Retrieve data from intent
        Intent intent = getIntent();
        String doctorName = intent.getStringExtra("doctorName");
        String doctorSpecialty = intent.getStringExtra("doctorSpecialty");
        String doctorHospital = intent.getStringExtra("doctorHospital");
        String doctorRating = intent.getStringExtra("doctorRating");
        int doctorImageResourceId = intent.getIntExtra("doctorImageResourceId", R.drawable.mzee);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("appointments");

        // Set data to views
        ImageView doctorImage = findViewById(R.id.image_of_doctor);
        TextView doctorNameView = findViewById(R.id.doctor_name);
        TextView doctorSpecialtyView = findViewById(R.id.doctor_speciality);
        TextView doctorHospitalView = findViewById(R.id.doctor_location);
        TextView doctorRatingView = findViewById(R.id.doctor_rating);

        doctorImage.setImageResource(doctorImageResourceId);
        doctorNameView.setText(doctorName);
        doctorSpecialtyView.setText(doctorSpecialty);
        doctorHospitalView.setText(doctorHospital);
        doctorRatingView.setText(doctorRating);

        // Initialize Book Appointment button
        bookAppointmentButton = findViewById(R.id.book_appointment_button);
        bookAppointmentButton.setOnClickListener(v -> bookAppointment(doctorName, doctorSpecialty, doctorHospital, doctorRating, doctorImageResourceId));

        // Set click listeners for date and time selection
        setDateAndTimeSelection();
    }

    private void setDateAndTimeSelection() {
        int[] dateIds = {R.id.date_item_21, R.id.date_item_22, R.id.date_item_23, R.id.date_item_24, R.id.date_item_25, R.id.date_item_26};
        int[] timeIds = {R.id.time_slot_1, R.id.time_slot_2, R.id.time_slot_3, R.id.time_slot_4, R.id.time_slot_5, R.id.time_slot_6};

        for (int id : dateIds) {
            findViewById(id).setOnClickListener(v -> {
                resetSelections(dateIds);
                v.setBackground(getResources().getDrawable(R.drawable.date_item_background_selected));
                ((TextView) v).setTextColor(getResources().getColor(R.color.white));
                selectedDate = ((TextView) v).getText().toString();
                enableBookButton();
            });
        }

        for (int id : timeIds) {
            findViewById(id).setOnClickListener(v -> {
                resetSelections(timeIds);
                v.setBackground(getResources().getDrawable(R.drawable.time_slot_background_selected));
                ((TextView) v).setTextColor(getResources().getColor(R.color.white));
                selectedTime = ((TextView) v).getText().toString();
                enableBookButton();
            });
        }
    }

    private void resetSelections(int[] ids) {
        for (int id : ids) {
            TextView view = findViewById(id);
            view.setBackground(getResources().getDrawable(R.drawable.date_item_background));
            view.setTextColor(getResources().getColor(R.color.date_text_color));
        }
    }

    private void enableBookButton() {
        if (!selectedDate.isEmpty() && !selectedTime.isEmpty()) {
            bookAppointmentButton.setEnabled(true);
        }
    }

    private void bookAppointment(String doctorName, String doctorSpecialty, String doctorHospital, String doctorRating, int doctorImageResourceId) {
        String appointmentId = databaseReference.push().getKey();
        Appointment appointment = new Appointment(doctorName, doctorSpecialty, doctorHospital, doctorRating, doctorImageResourceId, selectedDate, selectedTime);
        if (appointmentId != null) {
            databaseReference.child(appointmentId).setValue(appointment).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Successfully booked appointment
                    Intent intent = new Intent(DoctorDetailActivity.this, AppointmentDetails.class);
                    startActivity(intent);
                } else {
                    // Handle failure
                }
            });
        }
    }

    static class Appointment {
        public String doctorName;
        public String doctorSpecialty;
        public String doctorHospital;
        public String doctorRating;
        public int doctorImageResourceId;
        public String date;
        public String time;

        public Appointment() {
            // Default constructor required for calls to DataSnapshot.getValue(Appointment.class)
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
}
