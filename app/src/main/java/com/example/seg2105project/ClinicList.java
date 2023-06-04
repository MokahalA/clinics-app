package com.example.seg2105project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClinicList extends ArrayAdapter<Clinic> {
    private Activity context;
    List<Clinic> clinics;

    public ClinicList(Activity context, List<Clinic> clinics){
        super(context, R.layout.activity_clinic_list, clinics);
        this.context = context;
        this.clinics = clinics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_clinic_list, null, true);

        TextView tvClinicName = listViewItem.findViewById(R.id.tv_ClinicName);
        TextView tvAdress = listViewItem.findViewById(R.id.tv_address);


        Clinic clinic = clinics.get(position);
        tvClinicName.setText(clinic.getName());
        tvAdress.setText(clinic.getAddress());

        return listViewItem;
    }

}
