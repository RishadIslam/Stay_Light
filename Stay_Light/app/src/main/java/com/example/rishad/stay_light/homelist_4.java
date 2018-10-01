package com.example.rishad.stay_light;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class homelist_4 extends AppCompatActivity {

    private TextView viewOputPut;
    private Button finishBtn;
    public String amenities = "";
    public String pets, lift, wifi, Parking, laundry, brakfast, tv, kitchen, ac, toilet, fire, desk, firstAid;
    private CheckBox boxPets, boxLift, boxWifi, boxParking, boxLaundry, boxBreakfast, boxTv, boxKitchen, boxAc, boxToilet,
            boxFire, boxDesk, boxFirstAid;

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
        viewOputPut = findViewById(R.id.output);

        finishBtn = findViewById(R.id.finish);

        viewOputPut.setText("Proceed to next page for placing your page in maps.");

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (boxPets.isChecked())
//                    pets = "pets";
                    amenities = amenities + "pets\n";
                if (boxLift.isChecked())
                    //lift = "lift";
                    amenities = amenities + "lifts\n";
                if (boxWifi.isChecked())
//                    wifi = "wifi";
                    amenities = amenities + "wifi\n";
                if (boxParking.isChecked())
//                    Parking = "parking";
                    amenities = amenities + "parking\n";
                if (boxLaundry.isChecked())
//                    laundry = "laundry";
                    amenities = amenities + "laundry\n";
                if (boxBreakfast.isChecked())
//                    brakfast = "breakfast";
                    amenities = amenities + "breakfast\n";
                if (boxTv.isChecked())
//                    tv = "tv";
                    amenities = amenities + "tv\n";
                if (boxKitchen.isChecked())
//                    kitchen = "kitchen items";
                    amenities = amenities + "kitchen\n";
                if (boxAc.isChecked())
//                    ac = "ac";
                    amenities = amenities + "ac\n";
                if (boxToilet.isChecked())
//                    toilet = "toiletries";
                    amenities = amenities + "toiletries\n";
                if (boxFire.isChecked())
//                    fire = "fire extingusher";
                    amenities = amenities + "fire extinguisher\n";
                if (boxDesk.isChecked())
//                    desk = "desk";
                    amenities = amenities + "desk\n";
                if (boxFirstAid.isChecked())
//                    firstAid = "first aid Kit";
                    amenities = amenities + "first aid kit\n";

//                Toast.makeText(getApplicationContext(),amenities,Toast.LENGTH_LONG).show();

                //        data send

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("amenities", amenities);
                //commits your edits
                editor.apply();
//        data send

                startActivity(new Intent(homelist_4.this,MapsActivity.class));
            }
        });
    }
}
