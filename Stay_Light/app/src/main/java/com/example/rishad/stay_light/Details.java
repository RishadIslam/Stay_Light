package com.example.rishad.stay_light;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Details extends AppCompatActivity {

    String HouseId;
    private static ViewPager mPager;
    private static int currentPage = 0, NUM_Pages = 0;
    private CirclePageIndicator circlePageIndicator;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<String> ImagesArray = new ArrayList<>();
    private static final ArrayList<String> IMAGES = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(ImagesArray, Details.this ));

        circlePageIndicator = findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicator.setRadius(5 * density);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Uploads");

        Intent i = this.getIntent();

        try {

            HouseId = i.getExtras().getString("HouseID");
            Toast.makeText(this, HouseId, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childsnapshot: dataSnapshot.getChildren()){
                    if (childsnapshot.getKey().equals(HouseId)) {
                        Query query = FirebaseDatabase.getInstance().getReference("Uploads").child("imageUrl");
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String url = dataSnapshot.getValue(String.class);
                                IMAGES.add(url);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        init();


    }

    private void init() {

        for (int i=0; i<IMAGES.size(); i++) {
            ImagesArray.add(IMAGES.get(i));
        }

        NUM_Pages = IMAGES.size();

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_Pages) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        //pager listener over indicator
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
