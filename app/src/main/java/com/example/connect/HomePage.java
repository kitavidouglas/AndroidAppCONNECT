package com.example.connect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        Toast.makeText(getApplicationContext(),"Welcome "+ username, Toast.LENGTH_SHORT).show();

        // Initialize cardHospitals
        CardView cardHospitals = findViewById(R.id.card);
        CardView cardLabTest = findViewById(R.id.cardLabTest);
        CardView cardFindDoctor = findViewById(R.id.cardFindDoctor);
        CardView cardREMINDERS= findViewById(R.id.cardREMINDERS);



        // Check if cardHospitals is not null
        if (cardHospitals != null) {
            // Set OnClickListener only if cardHospitals is not null
            cardHospitals.setOnClickListener(v -> {

                // Start MapActivity when cardHospitals is clicked
                Intent intent = new Intent(HomePage.this, MapsActivityGoogle.class);
                startActivity(intent);
            });
        }
        if (cardLabTest!= null) {
            // Set OnClickListener only if cardHospitals is not null
            cardLabTest.setOnClickListener(v -> {

                // Start MapActivity when cardHospitals is clicked
                Intent intent = new Intent(HomePage.this, UploadActivity.class);
                startActivity(intent);
            });
        }
        if (cardFindDoctor!= null) {
            // Set OnClickListener only if cardHospitals is not null
            assert cardLabTest != null;
            cardFindDoctor.setOnClickListener(v -> {

                // Start MapActivity when cardHospitals is clicked
                Intent intent = new Intent(HomePage.this, BookAppointment.class);
                startActivity(intent);
            });
        }
        if (cardREMINDERS!= null) {
            // Set OnClickListener only if cardHospitals is not null
            assert cardREMINDERS != null;
            cardFindDoctor.setOnClickListener(v -> {

                // Start MapActivity when cardHospitals is clicked
                Intent intent = new Intent(HomePage.this, BookAppointment.class);
                startActivity(intent);
            });
        }


        // Set up edge-to-edge
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
