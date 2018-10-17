package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

import com.google.firebase.auth.FirebaseAuth;

public class homelist_2 extends AppCompatActivity {

    int check_error = 0;
    private Button button;
    Spinner dropdownAccomodation;
    private EditText textBath, textBed;
    private RadioGroup radioGroup;
    private String[] accoTypeList = new String[]{"","Entire Place", "Private Room", "Shared Room"};
    public String accoType, privateBath, noOfbed, noOfBath;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        radio button
        radioGroup = findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);

                if (radioButton != null && i > -1) {
                    privateBath = (String) radioButton.getText();
                    Toast.makeText(getApplicationContext(), privateBath, Toast.LENGTH_LONG).show();
                }
                if (radioButton == null) {
                    radioButton.setError("Check one options");
                    radioButton.requestFocus();
                    check_error = 1;
                }
            }
        });
//        radio button
        button = findViewById(R.id.next2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((String) textBath.getText().toString().trim()).isEmpty()
                        || ((String) textBed.getText().toString().trim()).isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all field", Toast.LENGTH_LONG).show();
                } else {

//                            data send
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
                    //now get Editor
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //put your value
                    editor.putString("noOfBath", (String) textBath.getText().toString().trim());
                    editor.putString("privateBath", privateBath);
                    editor.putString("accoType", accoType);
                    editor.putString("noOfbed", (String) textBed.getText().toString().trim());
                    //commits your edits
                    editor.apply();
//        data send
                    startActivity(new Intent(homelist_2.this, homelist_3.class));
                }
            }
        });
    }

    //      spinner handler
    AdapterView.OnItemSelectedListener AcooType = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
            accoType = parent.getItemAtPosition(pos).toString();
            Toast.makeText(homelist_2.this, accoType, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
//    spinner handler

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_nav:
                    startActivity(new Intent(getApplicationContext(),HomePage_Map.class));
                    return true;
                case R.id.profile_nav:
                    startActivity(new Intent(getApplicationContext(),myprofile.class));
                    return true;
                case R.id.rent_nav:
                    startActivity(new Intent(getApplicationContext(),homelist_1.class));
                    return true;
                case R.id.nav_logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    return true;
                case R.id.booked_nav:
                    startActivity(new Intent(getApplicationContext(),UserRentedHouse.class));
                    return true;
            }
            return false;
        }
    };

}
