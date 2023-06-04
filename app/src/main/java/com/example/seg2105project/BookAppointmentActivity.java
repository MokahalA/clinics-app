package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity {


    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference cRef;
    private DatabaseReference uRef;
    private DatabaseReference sRef;

    TextView tvAppClinic;
    TextView tvAppService;
    TextView tvAppDate;
    TextView tvAppTime;
    Button btnSetDate;
    Button btnSetTime;
    Button btnAppConfirm;
    Button btnAppCancel;

    Clinic clinic;
    Patient patient;
    Service service;

    private List<Appointment> appointmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        tvAppClinic = findViewById(R.id.tvAppClinic);
        tvAppService = findViewById(R.id.tvAppService);
        tvAppDate = findViewById(R.id.tvAppDate);
        tvAppTime = findViewById(R.id.tvAppTime);
        btnSetDate = findViewById(R.id.btnSetDate);
        btnSetTime = findViewById(R.id.btnSetTime);
        btnAppConfirm = findViewById(R.id.btnAppConfirm);
        btnAppCancel = findViewById(R.id.btnAppCancel);

        final String userName = getIntent().getStringExtra("userName");
        final String clinicName = getIntent().getStringExtra("clinicName");
        final String serviceName = getIntent().getStringExtra("serviceName");

        appointmentList = new ArrayList<>();

        uRef = dbRef.child("users").child(userName);
        cRef = dbRef.child("clinics").child(clinicName);
        sRef = dbRef.child("clinics").child(clinicName).child("services").child(serviceName);

        uRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    patient = dataSnapshot.getValue(Patient.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    clinic = dataSnapshot.getValue(Clinic.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                service = dataSnapshot.getValue(Service.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvAppClinic.setText(clinicName);
        tvAppService.setText(serviceName);


        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

        btnAppCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Need to add the btnAppConfirm function that validates and creates the user's appointment
        btnAppConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date =tvAppDate.getText().toString().trim();
                String time = tvAppTime.getText().toString().trim();
                Appointment appointment = new Appointment(clinic, date,time, patient, service);
                cRef.child("appoitments").child(userName).setValue(appointment);
                finish();
            }
        });
    }


    //Method that handles setting the date of an appointment
    private void handleDateButton() {
        Calendar c = Calendar.getInstance();  //Obtaining today's date so that date setter can start at that date
        int YEAR = c.get(Calendar.YEAR);
        int MONTH = c.get(Calendar.MONTH);
        int DAY = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth-1);
                int dayOfWeek= date.get(date.DAY_OF_WEEK);
                String dayString = formatIntDayToString(dayOfWeek);
                String fullDate = dayString + ", " + dayOfMonth + "/" + month + "/" + year;
                tvAppDate.setText(fullDate);
            }
        }, YEAR , MONTH, DAY);
        dpDialog.show();
    }


    //Helper method that converts Java's day integer to the equivalent string so we can display it to the user
    private String formatIntDayToString(int dayInt){
        String dayString;
        if(dayInt == 1){
            dayString = "Monday";
            return dayString;
        }
        if(dayInt == 2){
            dayString = "Tuesday";
            return dayString;
        }
        if(dayInt == 3){
            dayString = "Wednesday";
            return dayString;
        }
        if(dayInt == 4){
            dayString = "Thursday";
            return dayString;
        }
        if(dayInt == 5){
            dayString = "Friday";
            return dayString;
        }
        if(dayInt == 6){
            dayString = "Saturday";
            return dayString;
        }
        if(dayInt == 7) {
            dayString = "Sunday";
            return dayString;
        }
        return "Invalid";
    }




    //Method that handles setting the time of an appointment
    private void handleTimeButton() {

        Calendar cal = Calendar.getInstance();
        int HOUR = cal.get(Calendar.HOUR);
        int MINUTE = cal.get(Calendar.MINUTE);

        TimePickerDialog tpDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = hourOfDay+":"+minute;
                tvAppTime.setText(selectedTime);
            }
        }, HOUR, MINUTE, true);
        tpDialog.show();
    }


}
