package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class homelist_2 extends AppCompatActivity {

    private Button button;
    Spinner dropdownAccomodation;
    private EditText textBath,textBed;
    private RadioGroup radioGroup;
    private String[] accoTypeList = new String[]{"","Entire Place", "Private Room", "Shared Room"};
    public String accoType,privateBath,noOfbed,noOfBath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_2);
// spinner
        dropdownAccomodation = findViewById(R.id.guestaccomodation2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, accoTypeList);
        dropdownAccomodation.setAdapter(adapter);
        dropdownAccomodation.setOnItemSelectedListener(AcooType);
        dropdownAccomodation.setSelection(0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//  spinner

        textBath = findViewById(R.id.noofbath);
        textBed = findViewById(R.id.noofbedroom);


//        radio button
        radioGroup = findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);

                if (radioButton != null && i > -1)
                {
                    privateBath = (String) radioButton.getText();
                    Toast.makeText(getApplicationContext(),privateBath,Toast.LENGTH_LONG).show();
                }
            }
        });
//        radio button
        button = findViewById(R.id.next2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //        data send

                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send",MODE_PRIVATE);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("noOfBath",(String)textBath.getText().toString().trim());
                editor.putString("privateBath", privateBath);
                editor.putString("accoType", accoType);
                editor.putString("noOfbed", (String) textBed.getText().toString().trim());

                //commits your edits
                editor.apply();

//        data send

                startActivity(new Intent(homelist_2.this,homelist_3.class));
            }
        });
    }

    //      spinner handler
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
//    spinner handler
}
