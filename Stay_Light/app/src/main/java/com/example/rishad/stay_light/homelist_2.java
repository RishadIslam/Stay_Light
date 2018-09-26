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

public class homelist_2 extends AppCompatActivity {

    private Button button;
    Spinner dropdownAccomodation;
    private String[] accoTypeList = new String[]{"","Entire Place", "Private Room", "Shared Room"};
    public String accoType = new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_2);

        dropdownAccomodation = findViewById(R.id.typeofproperty);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accoTypeList);
        dropdownAccomodation.setAdapter(adapter);
        dropdownAccomodation.setOnItemSelectedListener(AcooType);
        dropdownAccomodation.setSelection(0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        button = findViewById(R.id.next);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homelist_2.this,homelist_3.class));
            }
        });
    }

    AdapterView.OnItemSelectedListener AcooType = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
            accoType = parent.getItemAtPosition(pos).toString();
            Toast.makeText(homelist_2.this, accoType, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
