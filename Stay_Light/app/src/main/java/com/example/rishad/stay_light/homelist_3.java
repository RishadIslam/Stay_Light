package com.example.rishad.stay_light;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class homelist_3 extends AppCompatActivity {

    int check_error = 0;
    private Button nextBtn;
    private EditText textApartmentNo,textHouseno,textRoadNo,textCity,textZipCode;
    public String apartmentNo,shouseNo,sroadNo,scityName,szipCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelist_3);

        textApartmentNo = findViewById(R.id.apartment);
        textHouseno = findViewById(R.id.houseNo);
        textRoadNo = findViewById(R.id.roadNo);
        textCity = findViewById(R.id.cityName);
        textZipCode = findViewById(R.id.zipCode);
        nextBtn = findViewById(R.id.nextPage);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apartmentNo = textApartmentNo.getText().toString().trim();
                shouseNo = textHouseno.getText().toString().trim();
                sroadNo = textRoadNo.getText().toString().trim();
                scityName = textCity.getText().toString().trim();
                szipCode = (String) textZipCode.getText().toString().trim();

                if (apartmentNo.isEmpty() || shouseNo.isEmpty() || sroadNo.isEmpty() || scityName.isEmpty() || szipCode.isEmpty())
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
                    editor.putString("scityName", textCity.getText().toString().trim());
                    editor.putString("szipCode", (String) textZipCode.getText().toString().trim());

                    //commits your edits
                    editor.apply();
//        data send
                    startActivity(new Intent(homelist_3.this,homelist_4.class));

                }

            }
        });
    }
}
