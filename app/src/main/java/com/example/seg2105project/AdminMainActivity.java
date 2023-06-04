package com.example.seg2105project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

import org.w3c.dom.Text;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        String userName = getIntent().getStringExtra("username");

        TextView txtV = (TextView) findViewById(R.id.txtUserName);
        txtV.setText("Welcome " + userName + "! You are logged in as admin" );
    }

    //Method called when btnManageAcc is clicked
    public void accClick(final View v){
        Intent intent = new Intent(v.getContext(), ManageAccountsActivity.class); //Creates activity used to manage accounts, needs to be implemented
        startActivity(intent);
    }

    //Method called when btnManageServices is pressed
    public void servicesClick(final View v){
        Intent intent = new Intent(v.getContext(), ManageServicesActivity.class); //Creates activity used to manage Services, needs to be implemented
        startActivity(intent);
    }
}
