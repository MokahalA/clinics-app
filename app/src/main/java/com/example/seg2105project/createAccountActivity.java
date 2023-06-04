package com.example.seg2105project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.RadioButton;

public class createAccountActivity extends AppCompatActivity {

    private EditText et_FirstName;
    private EditText et_LastName;
    private EditText et_Username;
    private EditText et_Pwd;


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    //Variables used to store the inputs as strings so we can use them
    private String firstName;
    private String lastName;
    private String userName;
    private String pwd;
    private String hashedPwd;
    private String accType;
    boolean typeChecked = true;
    User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        et_FirstName = (EditText) findViewById(R.id.et_FirstName);
        et_LastName = (EditText) findViewById(R.id.et_LastName);
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Pwd = (EditText) findViewById((R.id.et_Pwd));


    }

    //Helper method used to create Toasts
    public void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }


    //OnClick method to check which RadioButton is selected for AccType
    public void checkButton(View v){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();   //Obtains the checked RadioButton id
        if(radioId != -1){
            radioButton = (RadioButton) findViewById(radioId);          // Our variable stores that id
            accType = radioButton.getText().toString();
        } else {
            typeChecked = false;
        }
    }


    //Registers the account when the Create Account button is clicked
    public void createClick(View v){

        if (et_FirstName.getText().toString().length()==0) {
            showToast("First name field is empty");
            return;
        }
        if (et_LastName.getText().toString().length()==0) {
            showToast("Last name field is empty");
            return;
        }

        if (et_Username.getText().toString().length()==0) {
            showToast("Username field is empty");
            return;
        }
        if (et_Pwd.getText().toString().length()==0) {
            showToast("Password field is empty");
            return;
        }


        if (typeChecked != true) {
            showToast("Account type has not been selected");
            return;
        }

        firstName = et_FirstName.getText().toString(); // Storing all credentials as Strings
        lastName = et_LastName.getText().toString();
        userName = et_Username.getText().toString();
        pwd = et_Pwd.getText().toString();
        accType = radioButton.getText().toString();

        //If all fields have been entered, the password is hashed
        HashingSHA stringHash = new HashingSHA();

        try {
            hashedPwd = stringHash.hashPassword(pwd);
        } catch(NoSuchAlgorithmException e){
            System.out.println(e);
        }

        if (accType.equals("Employee")){
            user = new Employee(firstName, lastName, userName, hashedPwd);
        }else{
            user = new Patient(firstName, lastName, userName, hashedPwd);
        }
        //All user credentials including hashed password are stored using a User object


        //The user Object is added to the Firebase database
        db.child("users").child(userName).setValue(user);



        showToast("Your account has been successfully created");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
