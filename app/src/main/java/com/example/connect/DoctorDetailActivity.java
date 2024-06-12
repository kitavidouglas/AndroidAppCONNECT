package com.example.connect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorDetailActivity extends AppCompatActivity {

    private Button bookAppointmentButton;
    private String selectedDate = "";
    private String selectedTime = "";

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
        bookAppointmentButton.setEnabled(false); // Disable button initially
        bookAppointmentButton.setOnClickListener(v -> bookAppointment());

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

    private void bookAppointment() {
        // Directly open the new activity without booking
        Toast.makeText(DoctorDetailActivity.this, "Appointment details: Date - " + selectedDate + ", Time - " + selectedTime, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DoctorDetailActivity.this, AppointmentDetails.class);

        startActivity(intent);
    }
}
