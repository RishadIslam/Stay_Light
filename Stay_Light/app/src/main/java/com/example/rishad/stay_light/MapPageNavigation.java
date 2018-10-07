package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MapPageNavigation extends AppCompatActivity {

    TextView viewMap;
    Button buttonMap;
    String amenities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page_navigation);

        viewMap = findViewById(R.id.mapTextVew);
        buttonMap = findViewById(R.id.mapNextButton);

        amenities = getIntent().getStringExtra("amenities");
        viewMap.setText("Place your place in map.");
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("amenities",amenities);
                startActivity(intent);
            }
        });
    }
}
