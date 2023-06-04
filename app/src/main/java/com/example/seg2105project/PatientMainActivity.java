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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PatientMainActivity extends AppCompatActivity {

    private ListView listViewClinics;

    private List<Clinic> clinics;

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    private DatabaseReference cRef;
    private DatabaseReference uRef;
    private DatabaseReference sRef;

    private Button btnSearch;
    private Button btnClear;

    private Calendar c = Calendar.getInstance();
    int dayInt;

    boolean filtered;

    private String serviceId; //This is needed when searching by services







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        listViewClinics = findViewById(R.id.listViewClinics);
        clinics = new ArrayList<Clinic>();
        final String userName = getIntent().getStringExtra("username");
        dayInt = c.get(Calendar.DAY_OF_WEEK);

        cRef = dbRef.child("clinics");
        uRef = dbRef.child("users").child(userName);

        btnSearch = findViewById(R.id.btnSearch);
        btnClear = findViewById(R.id.btnClear);

        TextView tVUserName = (TextView) findViewById(R.id.txtWelcome);
        tVUserName.setText("Hello " + userName + ",  you are logged in as a Patient" );

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allClinics(cRef, clinics);
                filtered = false;
                showToast("Search Cleared");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        listViewClinics.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                Clinic c = clinics.get(i);
                String name = c.getName();
                Intent intent = new Intent(view.getContext(), ViewClinicActivity.class);
                intent.putExtra("clinicName", name);
                intent.putExtra("userName", userName);
                startActivity(intent);
                return false;
            }
        });

    }


    @Override
    protected  void onStart(){
        super.onStart();

        allClinics(cRef, clinics);
        filtered = false;
    }


    public void allClinics(DatabaseReference ref, final List<Clinic> c){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                c.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    c.add(snapshot.getValue(Clinic.class));
                }
                updateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void search(){
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.clinic_search, null);
        dialogueBuilder.setView(dialogView);

        final EditText eTValue = dialogView.findViewById(R.id.eTSearch);
        final Button btnConfirmSearch = dialogView.findViewById(R.id.btnConfirmSearch);
        final Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        final RadioGroup rGCriteria = dialogView.findViewById(R.id.rGCriteria);
        final int btnId = rGCriteria.getCheckedRadioButtonId();

        final AlertDialog b = dialogueBuilder.create();
        b.show();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        btnConfirmSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eTValue.getText().toString().length() == 0){
                    showToast("Please enter a value to search for");
                    return;
                }

                if(rGCriteria.getCheckedRadioButtonId() == -1) {
                    showToast("Please select a search criteria");
                    return;
                }
                if(filtered){
                    showToast("Please clear the previous search before making a new one");
                    return;
                }

                RadioButton selectedCriteria = dialogView.findViewById(rGCriteria.getCheckedRadioButtonId());
                String searchCriteria = selectedCriteria.getText().toString();

                switch(searchCriteria){
                    case "Address":
                        clinics = filterByAddress(eTValue.getText().toString());
                        break;
                    case "Service":
                        clinics = filterByService(eTValue.getText().toString());
                        break;
                    case "Hours":
                        clinics = filterByHours(eTValue.getText().toString());
                        break;
                }
                filtered = true;
                updateList();
                b.dismiss();
            }
        });
    }


    public List<Clinic> filterByAddress(String address){
        final List<Clinic> found = new ArrayList<>();

        for(Clinic c: clinics){
            if (c.getAddress().equals(address)){
                found.add(c);
            }
        }

        return found;
    }

    public List<Clinic> filterByHours(final String hours){
        final List<Clinic> found = new ArrayList<>();

        String day = "-1";

        switch(dayInt){

            case Calendar.SUNDAY:
                day = "sun";
                break;
            case Calendar.MONDAY:
                day = "mon";
                break;
            case Calendar.TUESDAY:
                day = "tue";
                break;
            case Calendar.WEDNESDAY:
                day = "wen";
                break;
            case Calendar.THURSDAY:
                day = "thu";
                break;
            case Calendar.FRIDAY:
                day = "fri";
                break;
            case Calendar.SATURDAY:
                day = "sat";
                break;
        }
        if (day.equals("-1")){
            showToast("Somehow you have managed to break space-time by exiting the week. I'm not even mad that's amazing");
        }

        for(final Clinic c : clinics){//This is probably not a very efficient method of doing this
            String name = c.getName();
            Query q = cRef.child(name).child("hours").child(day);
            q.addListenerForSingleValueEvent(new ValueEventListener() { //We check the hours of every clinic in the list and check if it's working hours matches those we want
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        if(dataSnapshot.getValue(String.class).equals(hours)){
                            found.add(c);
                        }
                    }else if(hours.toLowerCase().equals("closed")){
                        found.add(c);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        return found;


    }

    public List<Clinic> filterByService(final String service){

        final List<Clinic> found = new ArrayList<>();


        for (final Clinic c : clinics){
            String name = c.getName();
            Query q = cRef.child(name).child("services");
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            if (d.child("name").getValue(String.class).toLowerCase().equals(service.toLowerCase())){
                                found.add(c);
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        return found;

    }


    public void updateList(){
        ClinicList clinicAdapter = new ClinicList(PatientMainActivity.this, clinics);
        listViewClinics.setAdapter(clinicAdapter);
    }
    public void showToast(String message) {   //Helper method used to create Toasts
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }



}
