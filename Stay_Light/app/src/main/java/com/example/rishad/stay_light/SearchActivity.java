package com.example.rishad.stay_light;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<SearchModel> searchModelList;
    private houseAdapter mhouseAdapter;
    private Query query;
    private String housekey;
    private String houseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchModelList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.rview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setHasFixedSize(true);

        mhouseAdapter = new houseAdapter(SearchActivity.this, searchModelList);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Title Image");
    }

    @Override
    protected void onStart() {
        super.onStart();

        query = (Query) FirebaseDatabase.getInstance().getReference("Host Details")
                .orderByChild("location")
                .equalTo(HomePage_Map.UserRequest)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childsnapshot:dataSnapshot.getChildren()) {
                            housekey = childsnapshot.getKey();
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    searchModelList.clear();

                                    //iterating through all the nodes
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        if (postSnapshot.getKey().equals(housekey)) {
                                            SearchModel housefound = postSnapshot.getValue(SearchModel.class);
                                            searchModelList.add(housefound);
                                            houseID = postSnapshot.getKey();
                                            housefound.setHouseID(houseID);
                                        }
                                    }
                                    mRecyclerView.setAdapter(mhouseAdapter);

                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(SearchActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "No House Found", Toast.LENGTH_SHORT).show();
                    }
                });



    }
}
