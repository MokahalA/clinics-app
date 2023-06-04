package com.example.seg2105project;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CheckBox;

public class EmployeeCompleteAccActivity extends AppCompatActivity {

    //Initialize the activity variables and fetch user's account from the database
    private EditText et_cName;
    private EditText et_Address;
    private EditText et_PhoneNumber;

    private CheckBox cB_Cash;
    private CheckBox cB_Debit;
    private CheckBox cB_Credit;

    private CheckBox cB_OHIP;
    private CheckBox cB_pubIns;
    private CheckBox cB_priIns;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userDB; //This will be used to store the address of our user so we can modify the clinic value and the initialized value

    //These variables will be used to store the data
    private String cName;
    private String address;
    private String phoneNum;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_complete_acc);

        //Set all buttons and checkboxes to their appropriate references
        et_cName = findViewById(R.id.et_cName);
        et_Address = findViewById(R.id.et_address);
        et_PhoneNumber = findViewById(R.id.et_PhoneNumber);

        cB_Cash = findViewById(R.id.cB_Cash);
        cB_Debit = findViewById(R.id.cB_Debit);
        cB_Credit = findViewById(R.id.cB_Credit);

        cB_OHIP =  findViewById(R.id.cB_OHIP);
        cB_pubIns = findViewById(R.id.cB_pubIns);
        cB_priIns = findViewById(R.id.cB_priIns);

        String username = getIntent().getStringExtra("username");

        userDB = db.child("users").child(username); //set the userDB reference based on the username sent from the previous activity

    }

    //Helper method used to create Toasts
    public void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    //Function checks if any checkboxes have been pressed and if yes return True
    public boolean isClicked(CheckBox c1, CheckBox c2, CheckBox c3){
        return c1.isChecked() || c2.isChecked() || c3.isChecked();
    }


    public void onCompleteClick(View v){

        if(et_cName.getText().toString().length() == 0){
            showToast("Name field is empty");
            return;
        }

        if(et_Address.getText().toString().length() == 0){
            showToast("Address field is empty");
            return;
        }

        if(et_PhoneNumber.getText().toString().length() == 0){
            showToast("Phone number field is empty");
            return;
        }

        if(!isClicked(cB_Cash,cB_Debit,cB_Credit)){
            showToast("Please select at least one payment option");
            return;
        }

        if(!isClicked(cB_OHIP, cB_priIns,cB_pubIns)){
            showToast("Please select at least one Insurance option");
            return;
        }

        //Record the name, address and phoneNumber of the clinic
        cName = et_cName.getText().toString();
        address = et_Address.getText().toString();
        phoneNum = et_PhoneNumber.getText().toString();

        Clinic c = new Clinic(cName, address, phoneNum,
                cB_Credit.isChecked(), cB_Cash.isChecked(), cB_Debit.isChecked(), cB_OHIP.isChecked(), cB_pubIns.isChecked(), cB_priIns.isChecked(), 0, 5.0); //Be default the wait time for a clinic is 0;

        userDB.child("clinic").setValue(cName);

        userDB.child("initialized").setValue(true);

        db.child("clinics").child(cName).setValue(c);




        Intent intent = new Intent(v.getContext(), EmployeeMainActivity.class);
        intent.putExtra("username", userDB.child("username").toString());
        startActivity(intent);
    }

}
