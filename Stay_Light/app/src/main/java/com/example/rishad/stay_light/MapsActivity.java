package com.example.rishad.stay_light;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class LatitudeAndLongitude {

    double latitude, longitude;

    public LatitudeAndLongitude() {
    }

    public LatitudeAndLongitude(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    LatitudeAndLongitude latitudeAndLongitude;
    double latitude, longitude;
    private Button buttonFinish;
    private FirebaseAuth mAuth;
    private String guestNumber,houseType,location,noOfBath,privateBath,accoType,noOfbed,shouseNo,apartmentNo,sroadNo,scityName,
            szipCode,housePrice,amenities;
    private FirebaseUser user;
    public HostPlaceInfo hostPlaceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        buttonFinish = findViewById(R.id.finishBtn);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                data send to db
                user = mAuth.getInstance().getCurrentUser();
                String id = user.getUid().trim();
                hostPlaceInfo = new HostPlaceInfo(id,guestNumber,location,houseType,accoType,privateBath,noOfbed,
                        noOfBath,apartmentNo,shouseNo,sroadNo,scityName,szipCode,amenities,housePrice,latitude,longitude);

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Do you want to submit?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Host Details");
                        DatabaseReference refDatabase = FirebaseDatabase.getInstance().getReference("Host Location");

                        String refId = databaseReference.push().getKey();

                        databaseReference.child(refId).setValue(hostPlaceInfo);
                        refDatabase.child(refId).setValue(latitudeAndLongitude);
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MapsActivity.this,rentlistsuccess.class));
                    }
                }).setNegativeButton("Cancel",null).setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
//                data send to db
            }
        });

        //        data receive
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Data Send",MODE_PRIVATE);
        guestNumber = sharedPref.getString("guestNumber", "");
        houseType = sharedPref.getString("houseType", "");
        location = sharedPref.getString("location", "");
        noOfBath = sharedPref.getString("noOfBath", "");
        privateBath = sharedPref.getString("privateBath", "");
        accoType = sharedPref.getString("accoType", "");
        noOfbed = sharedPref.getString("noOfbed", "");
        apartmentNo = sharedPref.getString("apartmentNo", "");
        shouseNo = sharedPref.getString("shouseNo", "");
        sroadNo = sharedPref.getString("sroadNo", "");
        scityName = sharedPref.getString("scityName", "");
        szipCode = sharedPref.getString("szipCode", "");
        housePrice = sharedPref.getString("housePrice", "");
        amenities = sharedPref.getString("amenities", "");
//        data receive
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                latitudeAndLongitude = new LatitudeAndLongitude(latti, longi);
                latitudeAndLongitude.setLatitude(latti);
                latitudeAndLongitude.setLongitude(longi);

            } else {
                Toast.makeText(getApplicationContext(), "Can not find current locatioin", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        latitude = latitudeAndLongitude.getLatitude();
        longitude = latitudeAndLongitude.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Marker")
                .draggable(true)
                .snippet("Hello")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng position = marker.getPosition(); //
                latitude = position.latitude;
                longitude = position.longitude;
                latitudeAndLongitude = new LatitudeAndLongitude(latitude,longitude);
                Toast.makeText(
                        MapsActivity.this,
                        "Lat " + latitude + " "
                                + "Long " + longitude,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
