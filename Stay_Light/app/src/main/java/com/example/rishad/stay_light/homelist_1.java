package com.example.rishad.stay_light;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class homelist_1 extends AppCompatActivity  {

    private Button nextButton;
    Spinner dropdown;
    private String[] houseType = new String[]{"","Entire Place", "Private Room", "Shared Room"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_1);

        nextButton = findViewById(R.id.next);
        dropdown = findViewById(R.id.typeofhouse);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, houseType);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(typesOfhouse);
        dropdown.setSelection(0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homelist_1.this, homelist_2.class));
            }
        });
    }

    AdapterView.OnItemSelectedListener typesOfhouse = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
            String item = parent.getItemAtPosition(pos).toString();
            Toast.makeText(homelist_1.this, item, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
