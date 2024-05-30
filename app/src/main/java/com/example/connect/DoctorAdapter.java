package com.example.connect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    public DoctorAdapter(Context context, List<Doctor> doctors) {
        super(context, 0, doctors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor_list_item, parent, false);
        }

        ImageView doctorImage = convertView.findViewById(R.id.doctorImage);
        TextView doctorName = convertView.findViewById(R.id.doctorName);
        TextView doctorSpecialty = convertView.findViewById(R.id.doctorSpecialty);
        TextView doctorHospital = convertView.findViewById(R.id.doctorHospital);
        TextView doctorRating = convertView.findViewById(R.id.doctorRating);

        doctorImage.setImageResource(doctor.imageResourceId);
        doctorName.setText(doctor.name);
        doctorSpecialty.setText(doctor.specialty);
        doctorHospital.setText(doctor.hospital);
        doctorRating.setText(doctor.rating);
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DoctorDetailActivity.class);
            intent.putExtra("doctorName", doctor.name);
            intent.putExtra("doctorSpecialty", doctor.specialty);
            intent.putExtra("doctorHospital", doctor.hospital);
            intent.putExtra("doctorRating", doctor.rating);
            intent.putExtra("doctorImageResourceId", doctor.imageResourceId);
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
