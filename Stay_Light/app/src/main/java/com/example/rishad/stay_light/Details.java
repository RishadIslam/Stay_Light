package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    String HouseId;

    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = this.getIntent();

        view = findViewById(R.id.textView7);

        try {

            HouseId = i.getExtras().getString("HouseID");
            view.setText(HouseId);
            Toast.makeText(this, HouseId, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
        }
    }
}
