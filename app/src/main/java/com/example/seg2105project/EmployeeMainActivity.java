package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EmployeeMainActivity extends AppCompatActivity {


    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef;
    DatabaseReference cRef;

    TextView tv_ClinicName;
    TextView tv_Address;
    TextView tv_PhoneNum;
    TextView tv_PaymentMethods;
    TextView tv_InsuranceTypes;

    private String cName;

    Button btnViewServices;

    //All the time buttons
    private Button btnMon;
    private Button btnTue;
    private Button btnWen;
    private Button btnThu;
    private Button btnFri;
    private Button btnSat;
    private Button btnSun;

    //All the texts for times
    private TextView tvMon;
    private TextView tvTue;
    private TextView tvWen;
    private TextView tvThu;
    private TextView tvFri;
    private TextView tvSat;
    private TextView tvSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        tv_ClinicName = (TextView) findViewById(R.id.tv_clinicName);
        tv_Address = (TextView) findViewById(R.id.tv_clinicAddress);
        tv_PhoneNum = (TextView) findViewById(R.id.tv_phoneNum);
        tv_PaymentMethods = (TextView) findViewById(R.id.tV_payMethods);
        tv_InsuranceTypes = (TextView) findViewById(R.id.tv_insMethods);


        //Setting all the buttons and TextViews for the working hours

        tvMon = findViewById(R.id.tv_monHours);
        tvTue = findViewById(R.id.tv_tueHours);
        tvWen = findViewById(R.id.tv_wenHours);
        tvThu = findViewById(R.id.tv_thuHours);
        tvFri = findViewById(R.id.tv_friHours);
        tvSat = findViewById(R.id.tv_satHours);
        tvSun = findViewById(R.id.tv_sunHours);

        btnMon = findViewById(R.id.btn_monday);
        btnTue = findViewById(R.id.btn_tuesday);
        btnWen = findViewById(R.id.btn_wednesday);
        btnThu = findViewById(R.id.btn_thursday);
        btnFri = findViewById(R.id.btn_friday);
        btnSat = findViewById(R.id.btn_saturday);
        btnSun = findViewById(R.id.btn_sunday);

        btnViewServices = findViewById(R.id.btn_viewServices);

        String username = getIntent().getStringExtra("username");

        TextView tv_greeting = findViewById(R.id.tv_greeting);
        tv_greeting.setText("Hello " + username + " you are logged in as an employee");

        userRef = dbRef.child("users").child(username);
        //username of the employee obtained from EmployeeCompleteAccActivity

        cRef = dbRef.child("clinics");

        //To obtain the Clinic Name from Database and set it in the layout
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cName = dataSnapshot.child("clinic").getValue(String.class);
                System.out.println(cName);
                String data = dataSnapshot.child("clinic").getValue(String.class);
                tv_ClinicName.setText(data);
                cRef.child(cName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String address = dataSnapshot.child("address").getValue(String.class);
                        String phoneNum = dataSnapshot.child("phoneNum").getValue(String.class);

                        Boolean takesCash = dataSnapshot.child("cash").getValue(Boolean.class);
                        Boolean takesDebit = dataSnapshot.child("debit").getValue(Boolean.class);
                        Boolean takesCredit = dataSnapshot.child("credit").getValue(Boolean.class);

                        Boolean takesOHIP = dataSnapshot.child("ohip").getValue(Boolean.class);
                        Boolean takespriIns = dataSnapshot.child("priIns").getValue(Boolean.class);
                        Boolean takesPubIns = dataSnapshot.child("pubIns").getValue(Boolean.class);

                        DataSnapshot hours = dataSnapshot.child("hours");

                        //Set the working hours if they exist, if they don't then we just set them as closed
                        if (hours.child("mon").exists()){ //This checks if the hours have been put in yet, if not then we skip this step
                                tvMon.setText(hours.child("mon").getValue(String.class));
                        }else{
                            tvMon.setText("Closed");
                        }

                        if (hours.child("tue").exists()){ //This checks if the hours have been put in yet, if not then we skip this step
                            tvTue.setText(hours.child("tue").getValue(String.class));
                        }else{
                            tvTue.setText("Closed");
                        }

                        if (hours.child("wen").exists()){ //This checks if the hours have been put in yet, if not then we skip this step
                            tvWen.setText(hours.child("wen").getValue(String.class));
                        }else{
                            tvWen.setText("Closed");
                        }

                        if (hours.child("thu").exists()){ //This checks if the hours have been put in yet, if not then we skip this step
                            tvThu.setText(hours.child("thu").getValue(String.class));
                        }else{
                            tvThu.setText("Closed");
                        }

                        if (hours.child("fri").exists()){ //This checks if the hours have been put in yet, if not then we skip this step
                            tvFri.setText(hours.child("fri").getValue(String.class));
                        }else{
                            tvFri.setText("Closed");
                        }


                        if (hours.child("sat").exists()) { //This checks if the hours have been put in yet, if not then we skip this step
                            tvSat.setText(hours.child("sat").getValue(String.class));
                        }else{
                            tvSat.setText("Closed");
                        }


                        if (hours.child("sun").exists()){ //This checks if the hours have been put in yet, if not then we skip this step
                                tvSun.setText(hours.child("sun").getValue(String.class));
                        }else{
                            tvSun.setText("Closed");
                        }


                        System.out.println(address);
                        tv_Address.setText(address);

                        System.out.println(phoneNum);
                        tv_PhoneNum.setText(phoneNum);

                        System.out.println(takesCash);
                        System.out.println(takesDebit);
                        System.out.println(takesCredit);

                        String paymentTypes = ""; //Initialize a string to hold our payement types


                        //We check the payement types and add them to the string as necessary
                        if(takesCash){
                            paymentTypes+= " Cash";
                            if(takesDebit){
                                paymentTypes+= ",";
                            }
                        }
                        if (takesDebit){
                            paymentTypes+= " Debit";
                            if(takesCredit){
                                paymentTypes+= ",";
                            }
                        }
                        if(takesCredit){
                            paymentTypes+= " Credit";
                        }

                        tv_PaymentMethods.setText(paymentTypes);


                        //Repeat the same procedure but with insuranceTypes
                        String insuranceTypes = "";

                        if(takesOHIP){
                            insuranceTypes+= " OHIP";
                            if(takespriIns){
                                insuranceTypes+= ",";
                            }
                        }
                        if(takespriIns){
                            insuranceTypes+= " Private";
                            if(takesPubIns){
                                insuranceTypes+= ",";
                            }
                        }
                        if(takesPubIns){
                            insuranceTypes+= " Public";
                        }

                        tv_InsuranceTypes.setText(insuranceTypes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnViewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewClinicServicesActivity.class);
                intent.putExtra("clinicName", cName);
                startActivity(intent);
            }
        });


        //Hours edit buttons

        btnMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("mon");
            }
        });

        btnTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("tue");
            }
        });

        btnWen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("wen");
            }
        });

        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("thu");
            }
        });

        btnFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("fri");
            }
        });

        btnSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("sat");
            }
        });

        btnSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHours("sun");
            }
        });

    }


    public void setHours(final String s){
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_change_time, null);
        dialogueBuilder.setView(dialogView);


        String day = "";

        switch(s)
        {
            case "mon":
                day = "Monday";
                break;
            case "tue":
                day = "Tuesday";
                break;
            case "wen" :
                day = "Wednesday";
                break;
            case "thu" :
                day = "Thursday";
                break;
            case "fri":
                day = "Friday";
                break;
            case "sat":
                day = "Saturday";
                break;
            case "sun":
                day = "Sunday";
                break;
        }


        final Button btnConfirm = dialogView.findViewById(R.id.btn_confirmHours);
        final Button btnCancel = dialogView.findViewById(R.id.btn_cancelHours);
        final EditText etOpenTime = dialogView.findViewById(R.id.et_openingTime);
        final EditText etCloseTime = dialogView.findViewById(R.id.et_closingTime);
        final CheckBox cbOpen = dialogView.findViewById(R.id.cB_open);

        cbOpen.setChecked(true); //Initially we want the open checkbox to be on, since it is more likely that that's how they want to place the time

        dialogueBuilder.setTitle("Change working hours for " + day);
        final AlertDialog b = dialogueBuilder.create();
        b.show();


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //First of all if the check box is unchecked we can ignore everything else since the clinic is closed anyways
                if(!cbOpen.isChecked()){
                    cRef.child(cName).child("hours").child(s).setValue("closed");
                }else{
                    //If the store is open we first check if the values are inputed
                    if(etOpenTime.getText().toString().length() == 0) {
                        showToast("Opening Time field is empty");

                    }else if(etCloseTime.getText().toString().length() == 0){
                        showToast("Closing Time field is empty");
                    }else{
                        String workingHours = etOpenTime.getText().toString() + "-" + etCloseTime.getText().toString();
                        cRef.child(cName).child("hours").child(s).setValue(workingHours);
                        showToast("Working hours have been set");
                        b.dismiss();

                    }
                }

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
