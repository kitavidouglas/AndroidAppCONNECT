package com.example.connect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        Intent intent = getIntent();
        String doctorName = intent.getStringExtra("doctorName");
        String doctorSpecialty = intent.getStringExtra("doctorSpecialty");
        String doctorHospital = intent.getStringExtra("doctorHospital");
        String doctorRating = intent.getStringExtra("doctorRating");
        int doctorImageResourceId = intent.getIntExtra("doctorImageResourceId", R.drawable.mzee);

        // Set doctor details to views
        ImageView doctorImage = findViewById(R.id.doctorImage);
        TextView doctorNameView = findViewById(R.id.doctorName);
        TextView doctorSpecialtyView = findViewById(R.id.doctorSpecialty);
        TextView doctorHospitalView = findViewById(R.id.doctorHospital);
        TextView doctorRatingView = findViewById(R.id.doctorRating);

        doctorImage.setImageResource(doctorImageResourceId);
        doctorNameView.setText(doctorName);
        doctorSpecialtyView.setText(doctorSpecialty);
        doctorHospitalView.setText(doctorHospital);
        doctorRatingView.setText(doctorRating);

        // Handle appointment form submission
        Button submitButton = findViewById(R.id.submitButton);
        EditText patientNameInput = findViewById(R.id.patientNameInput);
        EditText appointmentDateInput = findViewById(R.id.appointmentDateInput);
        EditText appointmentTimeInput = findViewById(R.id.appointmentTimeInput);

        submitButton.setOnClickListener(v -> {
            String patientName = patientNameInput.getText().toString();
            String appointmentDate = appointmentDateInput.getText().toString();
            String appointmentTime = appointmentTimeInput.getText().toString();

            // Perform validation or save the appointment details
            if (patientName.isEmpty() || appointmentDate.isEmpty() || appointmentTime.isEmpty()) {
                Toast.makeText(AppointmentDetails.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Handle successful booking (e.g., save to database, show confirmation, etc.)
                Toast.makeText(AppointmentDetails.this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Uber button click
        Button uberButton = findViewById(R.id.uberButton);
        uberButton.setOnClickListener(v -> openUberAppForRide());
    }

    private void openUberAppForRide() {
        // Uber deep link URL for requesting a ride (change the parameters as needed)
        String uberDeepLinkUrl = "uber://?action=setPickup&pickup=my_location&dropoff[latitude]=YOUR_DESTINATION_LATITUDE&dropoff[longitude]=YOUR_DESTINATION_LONGITUDE";

        // Create the intent with the Uber deep link URL
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uberDeepLinkUrl));

        // Verify if Uber app is installed
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Open the Uber app
            startActivity(intent);
        } else {
            // If Uber app is not installed, open the Uber mobile website
            String uberMobileWebUrl = "https://m.uber.com/ul/";
            intent.setData(Uri.parse(uberMobileWebUrl));
            startActivity(intent);
        }
    }
}
