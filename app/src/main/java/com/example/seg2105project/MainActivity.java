package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;


import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import androidx.annotation.Nullable;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText et_username;  //These are the variables for the user credential inputs
    private EditText et_password;  // Use getText().toString() to obtain the string value
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference(); //Retrieve the login database
    private String hashedPwd;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

    }


    //Login button is clicked, ...
    public void loginClick(final View v){

        //These if-statements ensure that the user is not leaving input fields blank
        if (et_username.getText().toString().length()==0) {
            showToast("Username field is empty");
            return ;
        }
        if (et_password.getText().toString().length()==0) {
            showToast("Password field is empty");
            return ;
        }

        //Translate needed info from EditText to Strings
        String username = et_username.getText().toString();
        String inputPwd = et_password.getText().toString();

        //Gets the database for the user
        final DatabaseReference user = database.child("users").child(username);



        HashingSHA stringHash = new HashingSHA(); //initialize the hashing class

        //Hash the password so that we can compare it with the password in our database
        try{
            hashedPwd = stringHash.hashPassword(inputPwd);
        }catch (NoSuchAlgorithmException e){
            //This shouldn't happen
            System.out.println(e);
            System.out.println("If you're seeing this, something went very wrong");
        }

        //Once the password given by the user has been hashed we can then go and get the password stored for that user from within our database
        //For this, we need to use a ValueEventListener
        user.child("password").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String dbPwd = dataSnapshot.getValue(String.class);
                //Once we have the password in the database we can compare it with hashedPwd
                if(hashedPwd.equals(dbPwd)){

                    //if the two passwords are equal then we can move on to checking the type of account
                    user.child("type").addValueEventListener(new ValueEventListener() {
                        @Override
                        //Checks the type of account and takes the user to the appropriate activity
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            final String type = dataSnapshot.getValue(String.class);
                            final EditText txtUserName = (EditText) findViewById(R.id.et_username);
                            final String strUserName = txtUserName.getText().toString();


                            if (type.equals("Employee")){

                                user.child("initialized").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        boolean init = (boolean) dataSnapshot.getValue(); //If the account is of type Employee, we want to check wether or not the account is complete


                                        if(init){ //If the account has been initialized then we can just send the user to the employeeMainActivity
                                            Intent intent = new Intent(v.getContext(), EmployeeMainActivity.class);
                                            intent.putExtra("username", strUserName);
                                            startActivity(intent);
                                        }else{ //If the account hasn't been completed then we send them to employeeCompleteActivity so that they can complete it.
                                            Intent intent = new Intent(v.getContext(), EmployeeCompleteAccActivity.class);
                                            intent.putExtra("username", strUserName);
                                            startActivity(intent);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }else if(type.equals("Patient")){
                                Intent intent = new Intent(v.getContext(), PatientMainActivity.class);
                                intent.putExtra("username", strUserName);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(v.getContext(),AdminMainActivity.class);
                                intent.putExtra("username", strUserName);
                                startActivity(intent);
                            }
                        }

                        //DO NOT REMOVE : NEEDED FOR CLASS TO WORK
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //If the two passwords are not equal we let the user know and return
                }else{
                    showToast("Username or Password is invalid. Please try again");
                    return;
                }
            }


            //DO NOT REMOVE : not used within class but is necessary to use ValueEventListener()
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    // When the register button is clicked, this takes the user to the createAccountActivity UI
    public void registerClick(View v){
        Intent intent = new Intent(v.getContext(), createAccountActivity.class);
        startActivity(intent);
    }


    public void showToast(String message) {   //Helper method used to create Toasts
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }



}
