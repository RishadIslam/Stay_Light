package com.example.rishad.stay_light;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class homelist_3 extends AppCompatActivity {

    int check_error = 0;
    private Button nextBtn;
    private EditText textApartmentNo,textHouseno,textRoadNo,textZipCode;
    public String apartmentNo,shouseNo,sroadNo,szipCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_3);

        textApartmentNo = findViewById(R.id.apartment);
        textHouseno = findViewById(R.id.houseNo);
        textRoadNo = findViewById(R.id.roadNo);
        textZipCode = findViewById(R.id.zipCode);
        nextBtn = findViewById(R.id.nextPage);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apartmentNo = textApartmentNo.getText().toString().trim();
                shouseNo = textHouseno.getText().toString().trim();
                sroadNo = textRoadNo.getText().toString().trim();
                szipCode = (String) textZipCode.getText().toString().trim();

                if (apartmentNo.isEmpty() || shouseNo.isEmpty() || sroadNo.isEmpty() || szipCode.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter all field",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //        data send

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send",MODE_PRIVATE);
                    //now get Editor
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //put your value
                    editor.putString("apartmentNo",textApartmentNo.getText().toString().trim());
                    editor.putString("shouseNo", textHouseno.getText().toString().trim());
                    editor.putString("sroadNo", textRoadNo.getText().toString().trim());
                    editor.putString("szipCode", (String) textZipCode.getText().toString().trim());

                    //commits your edits
                    editor.apply();
//        data send
                    startActivity(new Intent(homelist_3.this,homelist_4.class));

                }

            }
        });
    }

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
