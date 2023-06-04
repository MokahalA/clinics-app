package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageServicesActivity extends AppCompatActivity {

    private EditText editTextService;
    private Button buttonAddBtn;
    private ListView listViewServices;

    private List<Service> services;

    private DatabaseReference databaseServices;

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private String roleType;
    boolean typeChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        databaseServices = FirebaseDatabase.getInstance("https://seg2105project-ff9dd.firebaseio.com/").getReference("services");

        editTextService = (EditText) findViewById(R.id.editTextService);
        listViewServices = (ListView) findViewById(R.id.listViewServices);
        buttonAddBtn = (Button) findViewById(R.id.addButton);

        services = new ArrayList<>();

        buttonAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService();
            }
        });

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getName());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //getting product
                    Service product = postSnapshot.getValue(Service.class);
                    //adding product to list
                    services.add(product);
                }
                ServiceList serviceAdapter = new ServiceList(ManageServicesActivity.this, services);
                listViewServices.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void checkButton(View v){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();   //Obtains the checked RadioButton id
        if(radioId != -1){
            radioButton = (RadioButton) findViewById(radioId);          // Our variable stores that id
            roleType = radioButton.getText().toString();
        } else {
            typeChecked = false;
        }
    }
    private void showUpdateDeleteDialog(final String serviceId, final String serviceName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextService = (EditText) dialogView.findViewById(R.id.editTextService);
        final EditText editTextRole = (EditText) dialogView.findViewById(R.id.editTextRole);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);


        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextService.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(role)) {
                    if(validRole(role)){
                        updateService(serviceId, toTitle(name), toTitle(role));
                        b.dismiss();
                    }else {
                        editTextRole.setText("");
                        Toast.makeText(ManageServicesActivity.this, "Please enter a valid role", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ManageServicesActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(serviceId, serviceName);
                b.dismiss();
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

        dialogueBuilder.setTitle("Are you sure you want to delete service " + serviceName + "?");
        final AlertDialog b = dialogueBuilder.create();
        b.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v){
                deleteService(serviceId);
                b.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });


    }



    private boolean validRole(String role){
        if(role.toLowerCase().equals("doctor")){
            return true;
        }else if(role.toLowerCase().equals("nurse")){
            return true;
        }else if(role.toLowerCase().equals("staff")) {
            return true;
        }
        return false;
    }
    private String toTitle(String str){
        if(str.length()>0){
            str = str.toLowerCase();
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return "";
    }
    private void updateService(String id, String name, String role){
        DatabaseReference dR= FirebaseDatabase.getInstance().getReference("services").child(id);
        Service service = new Service( id, name, role, 0.0);
        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "Service has been successfully updated", Toast.LENGTH_LONG).show();
    }
    private void deleteService(String id){
        DatabaseReference dR=FirebaseDatabase.getInstance().getReference("services").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service has been successfully deleted", Toast.LENGTH_LONG).show();
    }
    private void addService(){
        String sName = editTextService.getText().toString();
        String sRole = roleType;
        if(TextUtils.isEmpty(sName) || TextUtils.isEmpty(sRole)){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        }else{
            String id = databaseServices.push().getKey();
            sName = toTitle(sName);
            Service service = new Service(id ,sName,sRole, 0.0);
            databaseServices.child(id).setValue(service);
            editTextService.setText("");
            radioGroup.clearCheck();
            // reset roleType to default value;
            roleType = null;
            Toast.makeText(getApplicationContext(), "Service has been successfully added", Toast.LENGTH_LONG).show();
        }
    }



}
