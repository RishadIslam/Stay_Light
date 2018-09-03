package com.example.rishad.converter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText userinput;
    private Button convertButton;
    private CheckBox unit1,unit2,unit3,unit4,unit5,unit6;
    private TextView showResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userinput = findViewById(R.id.Input);
        unit1 = findViewById(R.id.c1);
        unit2 = findViewById(R.id.c2);
        unit3 = findViewById(R.id.c3);
        unit4 = findViewById(R.id.c4);
        unit5 = findViewById(R.id.c5);
        unit6 = findViewById(R.id.c6);
        showResult = findViewById(R.id.result);


        convertButton = findViewById(R.id.convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String value = userinput.getText().toString();
                if(value.equals("")) {
                    showResult.setText("Please enter an input");
                } else {
                    ArrayList<String> result = new ArrayList<String>();
                    float numValue = Float.parseFloat(value);
                        if (unit1.isChecked()) {
                            double val1 = numValue * 9 / 5 + 32;
                            result.add(val1 + "\n");
                        }
                        if (unit2.isChecked()) {
                            double val2 = numValue * 3;
                            result.add(val2 + "\n");
                        }
                        if (unit3.isChecked()) {
                            double val3 = numValue * 0.45;
                            result.add(val3 + "\n");
                        }
                        if (unit4.isChecked()) {
                            double val4 = numValue * 4.546;
                            result.add(val4 + "\n");
                        }
                        if (unit5.isChecked()) {
                            double val5 = numValue * 0.868976;
                            result.add(val5 + "\n");
                        }
                        if (unit6.isChecked()) {
                            double val6 = numValue * 0.404686;
                            result.add(val6 + "\n");
                        }
                        if (unit1.isChecked() == false && unit2.isChecked() == false && unit3.isChecked() == false && unit4.isChecked() == false && unit5.isChecked() == false && unit6.isChecked() == false) {
                            result.add("Please select a unit\n");
                        }
                        String finalShow = "";
                        for (String Selection : result) {
                            finalShow = finalShow+Selection;
                        }
                        showResult.setText(finalShow);
                        showResult.setEnabled(true);
                }
            }
        });
    }
}

