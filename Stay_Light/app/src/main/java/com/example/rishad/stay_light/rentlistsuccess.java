package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class rentlistsuccess extends AppCompatActivity implements View.OnClickListener {
    private ImageButton success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentlistsuccess);
        success=(ImageButton) findViewById(R.id.success);

    }

    @Override
    public void onClick(View v) {
        Intent intentLoadNewActivity = new Intent(rentlistsuccess.this, HomePage.class);
        startActivity(intentLoadNewActivity);
    }
}
