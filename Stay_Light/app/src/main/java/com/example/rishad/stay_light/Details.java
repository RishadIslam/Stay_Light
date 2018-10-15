package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Details extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = this.getIntent();
        String HouseId = i.getExtras().getString("HouseID");
        Toast.makeText(this, HouseId, Toast.LENGTH_SHORT).show();
    }
}
