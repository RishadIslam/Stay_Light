package com.example.rishad.stay_light;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Details extends AppCompatActivity {

    private String HouseID;
    private TextView houseTitle, amenities, facilities, address, houseType, apartmentType, price, GuestNo;
    private Button Bookbtn, ownerBtn, galleryBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Query query;
    private String OwnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        HouseID = intent.getStringExtra("HouseID");

        houseTitle = findViewById(R.id.Title);
        amenities = findViewById(R.id.amenities);
        facilities = findViewById(R.id.facilities);
        address = findViewById(R.id.address);
        houseType = findViewById(R.id.houseType);
        apartmentType = findViewById(R.id.aptType);
        price = findViewById(R.id.Price);
        GuestNo = findViewById(R.id.guestNO);
        Bookbtn = findViewById(R.id.book);
        ownerBtn = findViewById(R.id.owner);
        galleryBtn = findViewById(R.id.gallery);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Host Details");
        query = firebaseDatabase.getReference("Title Image");

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("HouseID", HouseID);
                //commits your edits
                editor.apply();

                startActivity(new Intent(getApplicationContext(),ImageGallary.class));
            }
        });

        ownerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("OwnerID", OwnerID);
                editor.apply();

                startActivity(new Intent(getApplicationContext(), OwnerDetails.class));
            }
        });

        Bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("HouseID", HouseID);
                //commits your edits
                editor.apply();

                startActivity(new Intent(getApplicationContext(),booking.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    if (postsnapshot.getKey().equals(HouseID)) {
                        HostPlaceInfo hostPlaceInfo = postsnapshot.getValue(HostPlaceInfo.class);
                        String privateBath;
                        if (hostPlaceInfo.getPrivateBath() == "YES")
                            privateBath = "Private Bathroom";
                        else privateBath = "Shared Bathroom";
                        amenities.setText(hostPlaceInfo.getAmenities());
                        facilities.setText("1." + hostPlaceInfo.getNoOfbed() + "Bedrooms\n" + "2." + hostPlaceInfo.getNoOfBath() + "Bathrooms\n" + "3."
                                + privateBath + "\n");
                        address.setText("Apartment No: " + hostPlaceInfo.getApartmentNo() + ", House No: " + hostPlaceInfo.getShouseNo() + ", Road No: " +
                                hostPlaceInfo.getSroadNo() + ", " + hostPlaceInfo.getLocation() + ", " + hostPlaceInfo.getScityName() + ", " + hostPlaceInfo.getScityName() + "-" + hostPlaceInfo.getSzipCode());
                        houseType.setText(hostPlaceInfo.getHouseTypeItem());
                        apartmentType.setText(hostPlaceInfo.getAccoType());
                        price.setText(hostPlaceInfo.getHousePrice());
                        GuestNo.setText(hostPlaceInfo.getGuestNumber());
                        OwnerID = postsnapshot.child("userId").getValue().toString();

                    } else {
//                        Toast.makeText(Details.this, "Error!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(HouseID)) {
                        String title = snapshot.child("id").getValue().toString();
                        houseTitle.setText(title);
                    } else {
//                        Toast.makeText(Details.this, "Title Error in Database!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            }
            return false;
        }
    };
}
