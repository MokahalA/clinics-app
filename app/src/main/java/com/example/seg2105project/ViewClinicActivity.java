package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewClinicActivity extends AppCompatActivity {

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference uRef;
    private DatabaseReference cRef;

    private TextView tVClinicName;
    private TextView tVCAddress;
    private TextView tVCPhone;
    private TextView tVCPay;
    private TextView tVCIns;
    private TextView tVCWait;
    private TextView tVRate;

    private String userName;
    private String clinicName;

    private Clinic clinic;
    private Patient patient;

    private ListView listViewServices;
    private List<Service> services;

    private boolean checkedIn;

    private Button btnHours;
    private Button btnCheckIn;
    private Button btnRateClinic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clinic);

        tVClinicName = findViewById(R.id.tVClinicName);
        tVCAddress = findViewById(R.id.tVClinicAddress);
        tVCPhone = findViewById(R.id.tVClinicPhone);
        tVCPay = findViewById(R.id.tVClinicPay);
        tVCIns = findViewById(R.id.tVClinicIns);
        tVCWait = findViewById(R.id.tVClinicWait);
        tVRate = findViewById(R.id.tVClinicRate);
        listViewServices = findViewById(R.id.listViewServices);

        btnHours = findViewById(R.id.btnViewHours);
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnRateClinic = findViewById(R.id.btnRate);


        checkedIn = false;
        services = new ArrayList<>();

        userName = getIntent().getStringExtra("userName");
        clinicName = getIntent().getStringExtra("clinicName");

        uRef = dbRef.child("users").child(userName);
        cRef = dbRef.child("clinics").child(clinicName);

        uRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
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
                if (dataSnapshot.exists()){
                    clinic = dataSnapshot.getValue(Clinic.class);
                    tVClinicName.setText(clinic.getName());
                    tVCAddress.setText(clinic.getAddress());
                    tVCPhone.setText(clinic.getPhoneNum());

                    if(dataSnapshot.child("checkedIn").exists()){
                        for(DataSnapshot d : dataSnapshot.child("checkedIn").getChildren()){
                            clinic.addCheckedIn(d.getKey());
                        }
                        if(clinic.getCheckedIn().contains(userName)){
                            checkedIn = true;
                            btnCheckIn.setText("Check out");
                        }
                    }


                    String paymentTypes = ""; //Initialize a string to hold our payement types


                    //We check the payement types and add them to the string as necessary
                    if(clinic.getCash()){
                        paymentTypes+= " Cash";
                        if(clinic.getDebit()){
                            paymentTypes+= ",";
                        }
                    }
                    if (clinic.getDebit()){
                        paymentTypes+= " Debit";
                        if(clinic.getCredit()){
                            paymentTypes+= ",";
                        }
                    }
                    if(clinic.getCredit()){
                        paymentTypes+= " Credit";
                    }

                    tVCPay.setText(paymentTypes);

                    String insuranceTypes = "";

                    if(clinic.getOHIP()){
                        insuranceTypes+= " OHIP";
                        if(clinic.getPriIns()){
                            insuranceTypes+= ",";
                        }
                    }
                    if(clinic.getPriIns()){
                        insuranceTypes+= " Private";
                        if(clinic.getPubIns()){
                            insuranceTypes+= ",";
                        }
                    }
                    if(clinic.getPubIns()){
                        insuranceTypes+= " Public";
                    }

                    tVCIns.setText(insuranceTypes);

                    String waitTime = clinic.getWait() + " minutes";

                    tVCWait.setText(waitTime);

                    tVRate.setText(String.format("%.2f", clinic.getRate()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                Service s = services.get(i);
                String serviceName = s.getName();
                Intent intent = new Intent(view.getContext(), BookAppointmentActivity.class);
                intent.putExtra("userName", userName);
                intent.putExtra("serviceName", serviceName);
                intent.putExtra("clinicName", clinicName);
                startActivity(intent);
                return false;
            }
        });

        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHours();
            }
        });

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkedIn){
                    showToast("You've successfully checked in");
                    clinic.addCheckedIn(userName);
                    cRef.child("checkedIn").child(patient.getUserName()).setValue(true);
                    cRef.child("wait").setValue(clinic.increaseWait());
                    btnCheckIn.setText("Check out");
                    checkedIn = true;
                }else{
                    showToast("You've successfully checked out");

                    cRef.child("checkedIn").child(patient.getUserName()).removeValue();
                    cRef.child("wait").setValue(clinic.decreaseWait());
                    btnCheckIn.setText(userName);
                    checkedIn = false;
                }
            }
        });
        btnRateClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingBar();
            }
        });





    }


    @Override
    public void onStart(){
        super.onStart();

        cRef.child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    services.add(d.getValue(Service.class));
                }
                ServiceList serviceAdapter = new ServiceList(ViewClinicActivity.this, services);
                listViewServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void viewHours(){
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_hours, null);
        dialogueBuilder.setView(dialogView);

        final TextView tVMon = dialogView.findViewById(R.id.tVMon);
        final TextView tVTue = dialogView.findViewById(R.id.tVTue);
        final TextView tVWen = dialogView.findViewById(R.id.tVWen);
        final TextView tVThu = dialogView.findViewById(R.id.tVThu);
        final TextView tVFri = dialogView.findViewById(R.id.tVFri);
        final TextView tVSat = dialogView.findViewById(R.id.tVSat);
        final TextView tVSun = dialogView.findViewById(R.id.tVSun);

        final Button btnBack = dialogView.findViewById(R.id.btnBack);


        dialogueBuilder.setTitle("Hours for clinic:" + clinic.getName());
        final AlertDialog b = dialogueBuilder.create();
        b.show();

        cRef.child("hours").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    if(dataSnapshot.child("mon").exists()){
                        tVMon.setText(dataSnapshot.child("mon").getValue().toString());
                    }else{
                        tVMon.setText("Closed");
                    }

                    if(dataSnapshot.child("tue").exists()){
                        tVTue.setText(dataSnapshot.child("tue").getValue().toString());
                    }else{
                        tVTue.setText("Closed");
                    }

                    if(dataSnapshot.child("wen").exists()){
                        tVWen.setText(dataSnapshot.child("wen").getValue().toString());
                    }else{
                        tVWen.setText("Closed");
                    }

                    if(dataSnapshot.child("thu").exists()){
                        tVThu.setText(dataSnapshot.child("thu").getValue().toString());
                    }else{
                        tVThu.setText("Closed");
                    }

                    if(dataSnapshot.child("fri").exists()){
                        tVFri.setText(dataSnapshot.child("fri").getValue().toString());
                    }else{
                        tVFri.setText("Closed");
                    }

                    if(dataSnapshot.child("sat").exists()){
                        tVSat.setText(dataSnapshot.child("sat").getValue().toString());
                    }else{
                        tVSat.setText("Closed");
                    }

                    if(dataSnapshot.child("sun").exists()){
                        tVSun.setText(dataSnapshot.child("sun").getValue().toString());
                    }else{
                        tVSun.setText("Closed");
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }
    private void showRatingBar(){
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rating_bar, null);
        dialogueBuilder.setView(dialogView);

        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.rating_bar);
        final Button submitBtn = (Button) dialogView.findViewById(R.id.sub_button);

        dialogueBuilder.setTitle("You are rating " + clinic.getName() + " clinic");
        final AlertDialog ratingDialog = dialogueBuilder.create();
        ratingDialog.show();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cRef.child("num").setValue(clinic.getNum());
                showToast("Rating: " + ratingBar.getRating());
                float new_rate = ratingBar.getRating();
                cRef.child("rate").setValue(clinic.averageRate(new_rate));
                ratingDialog.dismiss();
            }
        });
    }



    public void showToast(String message) {   //Helper method used to create Toasts
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
