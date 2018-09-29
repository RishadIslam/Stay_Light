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
    public String pets,lift,wifi,Parking,laundry,brakfast,tv,kitchen,ac,toilet,fire,desk,firstAid;
    private CheckBox boxPets,boxLift,boxWifi,boxParking,boxLaundry,boxBreakfast,boxTv,boxKitchen,boxAc,boxToilet,
                        boxFire,boxDesk,boxFirstAid;
    private FirebaseAuth mAuth;
    private String guestNumber,houseType,location,noOfBath,privateBath,accoType,noOfbed,shouseNo,apartmentNo,sroadNo,scityName,
                    szipCode;
    private FirebaseUser user;
    public HostPlaceInfo hostPlaceInfo;

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

//                data send to db
                user = mAuth.getInstance().getCurrentUser();
                String id = user.getUid().trim();
                hostPlaceInfo = new HostPlaceInfo(id,guestNumber,location,houseType,accoType,privateBath,noOfbed,
                                                        noOfBath,apartmentNo,shouseNo,sroadNo,scityName,szipCode,amenities);

                AlertDialog.Builder builder = new AlertDialog.Builder(homelist_4.this);
                builder.setMessage("Do you want to submit?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Host Details");

                        String refId = databaseReference.push().getKey();

                        databaseReference.child(refId).setValue(hostPlaceInfo);
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(homelist_4.this,HomePage.class));
                    }
                }).setNegativeButton("Cancel",null).setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
//                data send to db
            }
        });

//        data receive

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send",MODE_PRIVATE);
         guestNumber = sharedPref.getString("guestNumber", "");
         houseType = sharedPref.getString("houseType", "");
         location = sharedPref.getString("location", "");
         noOfBath = sharedPref.getString("noOfBath", "");
         privateBath = sharedPref.getString("privateBath", "");
         accoType = sharedPref.getString("accoType", "");
         noOfbed = sharedPref.getString("noOfbed", "");
         apartmentNo = sharedPref.getString("apartmentNo", "");
         shouseNo = sharedPref.getString("shouseNo", "");
         sroadNo = sharedPref.getString("sroadNo", "");
         scityName = sharedPref.getString("scityName", "");
         szipCode = sharedPref.getString("szipCode", "");

//        data receive

        viewOputPut.setText(guestNumber + "\n" + houseType + "\n" + location + "\n" + noOfBath + "\n" + privateBath + "\n" + accoType
                            + "\n" + noOfbed + "\n" + apartmentNo + "\n" + shouseNo + "\n"
                            + sroadNo + "\n" + scityName + "\n" + szipCode);
    }
}
