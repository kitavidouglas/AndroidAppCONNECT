package com.example.connect;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class BookAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sc), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Kimani Douglas", "Cardiologist", "General Hospital", "4.5", R.drawable.doc2));
        doctors.add(new Doctor("Dr. John Musembi", "Neurologist", "City Hospital", "4.4", R.drawable.doctor1));
        doctors.add(new Doctor("Dr. Juniper Lee", "Senior Doctor", "Juja Hospital", "4.7", R.drawable.junne));
        doctors.add(new Doctor("Dr. Konzollo GG", "Oncologist", "Nairobi Hospital", "4.7", R.drawable.mzee));

        doctors.add(new Doctor("Dr. Shansea Wangui", "Emergency Physician ", "Juja Hospital", "4.7", R.drawable.nurse));// Add more doctors as needed

        DoctorAdapter adapter = new DoctorAdapter(this, doctors);
        ListView listView = findViewById(R.id.listViewDoctors);
        listView.setAdapter(adapter);
    }
}