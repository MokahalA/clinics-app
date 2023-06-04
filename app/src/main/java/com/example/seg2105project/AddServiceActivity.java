package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class AddServiceActivity extends AppCompatActivity {


    private ListView listViewServices;

    private List<Service> services;

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference cRef;
    private DatabaseReference sRef;


    public void showToast(String message) {  //Helper method used to create Toasts
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_list);

        listViewServices = findViewById(R.id.listViewServicesList);
        services = new ArrayList<Service>();
        final String cName = getIntent().getStringExtra("clinicName");

        cRef = dbRef.child("clinics").child(cName);

        sRef = dbRef.child("services");


        listViewServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service s = services.get(position);
                addService(s);
            }
        });

    }


    @Override
    protected  void onStart(){
        super.onStart();

        sRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    services.add(snapshot.getValue(Service.class));
                }
                ServiceList serviceAdapter = new ServiceList(AddServiceActivity.this, services);
                listViewServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void addService(final Service s){



        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_add_service, null);
        dialogueBuilder.setView(dialogView);

        final EditText eTSetRate = dialogView.findViewById(R.id.eT_enterRate);
        final Button btnConfirm = dialogView.findViewById(R.id.btn_confirmService);
        final Button btnCancel = dialogView.findViewById(R.id.btn_cancelService);

        dialogueBuilder.setTitle("What rate would you like to charge for the service " + s.getName() + "?");
        final AlertDialog b = dialogueBuilder.create();
        b.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eTSetRate.getText().toString().length() == 0){
                    showToast("Rate field is empty");
                }else{
                    double rate;
                    rate = Double.valueOf(eTSetRate.getText().toString());
                    Service service = new Service(s.getId(),s.getName(),s.getRole(),rate);
                    cRef.child("services").child(service.getId()).setValue(service);
                    showToast("Service has been added to your clinic");
                    b.dismiss();
                }
            }
        });

    }
}
