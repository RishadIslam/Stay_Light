package com.example.rishad.stay_light;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class booking extends AppCompatActivity {

    EditText textCheckIn,textCheckOut,textAdult,textChildren,textEmail,textPhone,text;
    ImageButton buttonEdit;
    Button buttonBook;

    public int year, month, day;

    public DatePicker datePicker;
    public Calendar calendar;
    static final int dID = 0;

    String checkIn,checkOut;
    int checkDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        textCheckIn = findViewById(R.id.checkin);
        textCheckOut = findViewById(R.id.checkout);
        text = findViewById(R.id.editText9);
        text.setEnabled(false);

        textCheckIn.setInputType(InputType.TYPE_NULL);
        textCheckOut.setInputType(InputType.TYPE_NULL);


        //        date selection

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

//        date selection
        
    }

    //  date selection handler
    public void showDialogOnButtonClick() {
        textCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dID);
                checkDate = 1;
            }
        });

        textCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dID);
                checkDate = 0;
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == dID) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year = i;
            month = i1 + 1;
            day = i2;

            if (checkDate == 1){
                checkIn = year + "/" + month + "/" + day;
                textCheckIn.setText(checkIn);
                Toast.makeText(getApplicationContext(),checkIn,Toast.LENGTH_SHORT).show();
            }else if (checkDate == 0){
                checkOut = year + "/" + month + "/" + day;
                textCheckOut.setText(checkOut);
                Toast.makeText(getApplicationContext(),checkOut,Toast.LENGTH_SHORT).show();
            }
        }
    };
//  date handler
}
