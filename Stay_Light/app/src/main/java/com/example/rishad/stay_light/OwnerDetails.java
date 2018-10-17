package com.example.rishad.stay_light;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class OwnerDetails extends AppCompatActivity {

    String OwnerID, ownerphone, ownerEmail;

    private TextView OwnerName, OwnerEmail, OwnerPhone, OwnerNid;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ImageView imageView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send", MODE_PRIVATE);
        OwnerID = sharedPref.getString("OwnerID", "");

        OwnerName = findViewById(R.id.ownerName);
        OwnerEmail = findViewById(R.id.ownerEmail);
        OwnerPhone = findViewById(R.id.ownerPhone);
        OwnerNid = findViewById(R.id.ownerNid);
        imageView = findViewById(R.id.imageViewUser);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(OwnerID);
        OwnerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });

    }

    private void callPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + ownerphone));

        if (ActivityCompat.checkSelfPermission(OwnerDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(callIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                OwnerName.setText(user.getUsername());
                OwnerEmail.setText(user.getEmail_id());
                OwnerPhone.setText(user.getPhoneno());
                OwnerNid.setText(user.getNidNo());
                ownerphone = user.getPhoneno();
                ownerEmail = user.getEmail_id();

                String id = ownerEmail.concat(".jpg");
                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("Profile Images").child(id);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
}
