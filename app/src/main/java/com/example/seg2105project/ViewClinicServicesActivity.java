package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewClinicServicesActivity extends AppCompatActivity {


    private DatabaseReference db;
    private Button buttonAddService;
    private ListView lVServices;

    private List<Service> services;

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference cRef;
    private DatabaseReference sRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clinic_services);
        final String cName = getIntent().getStringExtra("clinicName");
        cRef = dbRef.child("clinics").child(cName);



        sRef = dbRef.child("services");
        buttonAddService = findViewById(R.id.btn_addService);
        lVServices = findViewById(R.id.listViewServices);

        services = new ArrayList<>();


        lVServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                confirmDelete(service.getId(), service.getName());
                return true;
            }
        });

        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddServiceActivity.class);
                intent.putExtra("clinicName", cName);
                startActivity(intent);
            }
        });






    }

    @Override
    protected void onStart(){
        super.onStart();
        cRef.child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Service s = postSnapshot.getValue(Service.class);

                    services.add(s);
                }
                ServiceList serviceAdapter = new ServiceList(ViewClinicServicesActivity.this, services);
                lVServices.setAdapter(serviceAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void confirmDelete(final String serviceId, String serviceName){
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confirm_delete, null);
        dialogueBuilder.setView(dialogView);

        final Button btnConfirm = dialogView.findViewById(R.id.btn_confirm);
        final Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        dialogueBuilder.setTitle("Are you sure you want to delete the service " + serviceName + "?");
        final AlertDialog b = dialogueBuilder.create();
        b.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);
                showToast("Service has been deleted");
                b.dismiss();
            }
        });
    }

    private void deleteService(String serviceId){
        cRef.child("services").child(serviceId).setValue(null);
    }

    public void showToast(String message) {   //Helper method used to create Toasts
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
