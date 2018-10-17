package com.example.rishad.stay_light;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class homelist_4 extends AppCompatActivity {

    private Button finishBtn;
    private CheckBox boxPets, boxLift, boxWifi, boxParking, boxLaundry, boxBreakfast, boxTv, boxKitchen, boxAc, boxToilet,
            boxFire, boxDesk, boxFirstAid;

    String amenities;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_4);


        boxPets = findViewById(R.id.pets);
        boxLift = findViewById(R.id.lifts);
        boxWifi = findViewById(R.id.wifi);
        boxParking = findViewById(R.id.parking);
        boxLaundry = findViewById(R.id.laundry);
        boxBreakfast = findViewById(R.id.Breakfast);
        boxTv = findViewById(R.id.tv);
        boxKitchen = findViewById(R.id.kitchen);
        boxAc = findViewById(R.id.airCondition);
        boxToilet = findViewById(R.id.toiletries);
        boxFire = findViewById(R.id.fireExtinguisher);
        boxDesk = findViewById(R.id.deskWork);
        boxFirstAid = findViewById(R.id.firstAid);

        finishBtn = findViewById(R.id.finish);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder stringBuilder = new StringBuilder();

                if (boxPets.isChecked()) {
                    stringBuilder.append("pets");
                    stringBuilder.append(",");
                }
                if (boxLift.isChecked()) {
                    stringBuilder.append("lift");
                    stringBuilder.append(",");
                }
                if (boxWifi.isChecked()) {
                    stringBuilder.append("wifi");
                    stringBuilder.append(",");
                }
                if (boxParking.isChecked()) {
                    stringBuilder.append("parking");
                    stringBuilder.append(",");
                }
                if (boxLaundry.isChecked()) {
                    stringBuilder.append("laundry");
                    stringBuilder.append(",");
                }
                if (boxBreakfast.isChecked()) {
                    stringBuilder.append("breakfast");
                    stringBuilder.append(",");
                }
                if (boxTv.isChecked()) {
                    stringBuilder.append("tv");
                    stringBuilder.append(",");
                }
                if (boxKitchen.isChecked()) {
                    stringBuilder.append("kitchen");
                    stringBuilder.append(",");
                }
                if (boxAc.isChecked()) {
                    stringBuilder.append("ac");
                    stringBuilder.append(",");
                }
                if (boxToilet.isChecked()) {
                    stringBuilder.append("toiletries");
                    stringBuilder.append(",");
                }
                if (boxFire.isChecked()) {
                    stringBuilder.append("fire extinguisher");
                    stringBuilder.append(",");
                }
                if (boxDesk.isChecked()) {
                    stringBuilder.append("desk");
                    stringBuilder.append(",");
                }
                if (boxFirstAid.isChecked()) {
                    stringBuilder.append("first aid kit");
                    stringBuilder.append(",");
                }
//                data send

                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                amenities = stringBuilder.toString();

                Intent intent = new Intent(getApplicationContext(), MapPageNavigation.class);
                intent.putExtra("amenities",amenities);
                startActivity(intent);
//        data send
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_nav:
                    startActivity(new Intent(getApplicationContext(),HomePage_Map.class));
                    return true;
                case R.id.profile_nav:
                    startActivity(new Intent(getApplicationContext(),myprofile.class));
                    return true;
                case R.id.rent_nav:
                    startActivity(new Intent(getApplicationContext(),homelist_1.class));
                    return true;
                case R.id.nav_logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    return true;
                case R.id.booked_nav:
                    startActivity(new Intent(getApplicationContext(),UserRentedHouse.class));
                    return true;
            }
            return false;
        }
    };

}
