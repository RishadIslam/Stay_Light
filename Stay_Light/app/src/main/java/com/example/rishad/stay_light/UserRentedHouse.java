package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserRentedHouse extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<SearchModel> searchModelList;
    private bookedAdapter mbookedAdapter;
    private Query query;
    private String current_user, houseID;
    ArrayList<String> housekey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rented_house);
        searchModelList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.bview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setHasFixedSize(true);

        mbookedAdapter = new bookedAdapter(UserRentedHouse.this, searchModelList);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        current_user = user.getUid();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        housekey = new ArrayList<String>();
        query = FirebaseDatabase.getInstance().getReference("Booked House")
                .orderByChild("bookedBy")
                .equalTo(current_user);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childsnapshot : dataSnapshot.getChildren()) {
                    housekey.add(childsnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No House Found", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("No Title");

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                searchModelList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.exists()) {
                        Iterator iterator = housekey.iterator();
                        while (iterator.hasNext()) {
                            String housekey = (String) iterator.next();

                            if (postSnapshot.getKey().equals(housekey)) {
                                SearchModel housefound = postSnapshot.getValue(SearchModel.class);
                                searchModelList.add(housefound);
                            }
                        }
                    } else {
                        Toast.makeText(UserRentedHouse.this, "Database Does not exist", Toast.LENGTH_SHORT).show();
                    }

                }
                mRecyclerView.setAdapter(mbookedAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserRentedHouse.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
