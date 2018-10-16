package com.example.rishad.stay_light;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerDetails extends AppCompatActivity {

    String OwnerID, ownerphone;

    private TextView OwnerName, OwnerEmail, OwnerPhone;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
        OwnerID = sharedPref.getString("OwnerID", "");

        OwnerName = findViewById(R.id.ownerName);
        OwnerEmail = findViewById(R.id.ownerEmail);
        OwnerPhone = findViewById(R.id.ownerPhone);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        OwnerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });
    }

    private void callPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(ownerphone));

        if (ActivityCompat.checkSelfPermission(OwnerDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(callIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().equals(OwnerID)) {
                        User user = snapshot.getValue(User.class);
                        OwnerName.setText(user.getUsername());
                        OwnerEmail.setText(user.getEmail_id());
                        OwnerPhone.setText(user.getPhoneno());
                        ownerphone = user.getPhoneno().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
