package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class rentlistsuccess extends AppCompatActivity implements View.OnClickListener {
    private ImageButton msuccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentlistsuccess);
        msuccess= (ImageButton) findViewById(R.id.success);

        msuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(rentlistsuccess.this, HomePage_Map.class);
                startActivity(intentLoadNewActivity);
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
