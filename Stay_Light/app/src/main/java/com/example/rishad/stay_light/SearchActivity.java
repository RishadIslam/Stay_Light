package com.example.rishad.stay_light;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = findViewById(R.id.rview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Title Image");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<SearchModel, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<SearchModel, ViewHolder>(
                        SearchModel.class,
                        R.layout.row,
                        ViewHolder.class,
                        databaseReference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, SearchModel model, int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getId(), model.getUrl());

                    }
                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
