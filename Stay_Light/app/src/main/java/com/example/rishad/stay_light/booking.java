package com.example.rishad.stay_light;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class booking extends AppCompatActivity {

    EditText textCheckIn, textCheckOut, textAdult, textChildren, textEmail, textPhone, text;
    Button buttonBook;
    TextView viewPrice;


    public int year, month, day;

    public DatePicker datePicker;
    public Calendar calendar;
    static final int dID = 0;

    String checkIn, checkOut;
    int checkDate;
    Date dateCheckIn, dateCheckout;

    String id;
    int price, totalGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        textCheckIn = findViewById(R.id.checkin);
        textCheckOut = findViewById(R.id.checkout);
        text = findViewById(R.id.editText9);
        text.setEnabled(false);
        textEmail = findViewById(R.id.email);
        textEmail.setEnabled(false);
        textPhone = findViewById(R.id.mobile);
        textAdult = findViewById(R.id.adults);
        textChildren = findViewById(R.id.children);

        viewPrice = findViewById(R.id.textPrice);

        textCheckIn.setInputType(InputType.TYPE_NULL);
        textCheckOut.setInputType(InputType.TYPE_NULL);


        //        date selection

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

//        date selection

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            String uid = user.getUid();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            if (emailVerified) {
                textEmail.setText(email);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).
                        addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user1 = dataSnapshot.getValue(User.class);
                                textPhone.setText(user1.getPhoneno());
                                textPhone.setEnabled(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
        }

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_nav:
                    startActivity(new Intent(getApplicationContext(), HomePage_Map.class));
                    return true;
                case R.id.profile_nav:
                    startActivity(new Intent(getApplicationContext(), myprofile.class));
                    return true;
                case R.id.rent_nav:
                    startActivity(new Intent(getApplicationContext(), homelist_1.class));
                    return true;
                case R.id.nav_logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    return true;
            }
            return false;
        }
    };


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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");

            if (checkDate == 1) {
                checkIn = day + "/" + month + "/" + year;
                try {
                    dateCheckIn = dateFormat.parse(checkIn);
                } catch (Exception e) {

                }
                textCheckIn.setText(checkIn);
                Toast.makeText(getApplicationContext(), dateFormat.format(dateCheckIn), Toast.LENGTH_SHORT).show();
            } else if (checkDate == 0) {
                checkOut = day + "/" + month + "/" + year;
                try {
                    dateCheckout = dateFormat.parse(checkOut);
                } catch (Exception e) {

                }
                textCheckOut.setText(checkOut);

                Long mil1 = dateCheckIn.getTime();
                Long mil2 = dateCheckout.getTime();

                Long diff = (mil2 - mil1) / (24 * 60 * 60 * 1000);

                viewPrice.setText("Total fare for " + diff + " days is " + diff * price + "taka");

                Toast.makeText(getApplicationContext(), dateFormat.format(dateCheckout), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), price + "", Toast.LENGTH_SHORT).show();
            }
        }
    };
//  date handler


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
        id = sharedPref.getString("HouseID", "");

        FirebaseDatabase.getInstance().getReference("Host Details").child(id).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HostPlaceInfo hostPlaceInfo = dataSnapshot.getValue(HostPlaceInfo.class);
                        price = Integer.parseInt(hostPlaceInfo.getHousePrice());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
