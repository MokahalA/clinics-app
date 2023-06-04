package com.example.seg2105project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageAccountsActivity extends AppCompatActivity {

    private DatabaseReference db;
    private ListView listViewAccounts;
    private List<User> users;
    private EditText et_Username;
    private String userName;
    private String accType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        db = FirebaseDatabase.getInstance("https://seg2105project-ff9dd.firebaseio.com/").getReference("users");
        listViewAccounts = (ListView) findViewById(R.id.listViewAccounts);
        users = new ArrayList<>();
        et_Username = (EditText) findViewById(R.id.et_Username);

        listViewAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                User user = users.get(i);
                showDelete(user.getUserName());
                return false;
            }
        });
    }

    //Helper method used to create Toasts
    public void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    // getting the user
                    User user = postSnapshot.getValue(User.class);
                    // adding the user to our list
                    users.add(user);
                }
                AccountList accountAdapter = new AccountList(ManageAccountsActivity.this, users);
                listViewAccounts.setAdapter(accountAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showDelete(final String userName){

        //Sets necessary values for the new Event
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.confirm_delete, null);
        dialogueBuilder.setView(dialogView);

        final Button btnConfirm = dialogView.findViewById(R.id.btn_confirm);
        final Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        dialogueBuilder.setTitle("Are you sure you want to delete user " + userName + "?");
        final AlertDialog b = dialogueBuilder.create();
        b.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v){
                deleteUser(userName); //If confirm button is pressed, delete the user
                b.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss(); //If cancel button is pressed, close the window
            }
        });


    }
    //Method used for deleting accounts
    public void deleteUser(String userName){
        DatabaseReference dR = db.child(userName);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Account has been successfully deleted.", Toast.LENGTH_LONG).show();
    }

/*    public boolean onDeleteClick(View v){
        userName = et_Username.getText().toString();
        if (userName.length() == 0){
            showToast("Username field is empty");
            return true;
        } else{
            Query myQuery = db.orderByChild("userName").equalTo(userName);
            myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        postSnapshot.getRef().removeValue();
                        showToast("Account was successfully deleted");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Username is invalid");
                }
            });
        }
        return true;
    }*/ //Here lies the old method for deleting accounts may it rest in peace (2019-2019)
}
