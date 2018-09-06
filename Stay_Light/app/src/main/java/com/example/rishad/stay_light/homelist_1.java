package com.example.rishad.stay_light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class homelist_1 extends AppCompatActivity {

    Spinner dropdown = findViewById(R.id.typeofhouse);
    String[] items = new String[]{"Entire Place", "Private Room", "Shared Room"};
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    private Button nextButton;

    public void setDropdown(Spinner dropdown) {
        this.dropdown = dropdown;
        dropdown.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_1);

        nextButton = findViewById(R.id.next);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homelist_1.this, homelist_2.class));
            }
        });
    }
}
