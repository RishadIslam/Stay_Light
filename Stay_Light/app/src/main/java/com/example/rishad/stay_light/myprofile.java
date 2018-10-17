package com.example.rishad.stay_light;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class myprofile extends AppCompatActivity {

    private static final String TAG = "Myprofile";
    private EditText textName, textEmail, textPhone, textDob, textNationality, textNid;
    private ImageButton imageButtonUpload, imageButtonName, imageButtonPhone, imageButtonDob, imageButtonNationality, imageButtonNid;
    private ImageView imageViewProfile;
    Button buttonSave;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public String userName, userEmail, userPhone, userDob, userNationality, userNid, birthDate, userPass;
    public int year, month, day;

    public DatePicker datePicker;
    public Calendar calendar;
    static final int dID = 0;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    String imageUrl;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        mAuth = FirebaseAuth.getInstance();

        textName = findViewById(R.id.name);
        textEmail = findViewById(R.id.email);
        textPhone = findViewById(R.id.phone);
        textDob = findViewById(R.id.DOB);
        textNationality = findViewById(R.id.nationality);
        textNid = findViewById(R.id.NID);
        textDob.setInputType(InputType.TYPE_NULL);

        imageButtonUpload = findViewById(R.id.upload);
        imageButtonName = findViewById(R.id.editname);
        imageButtonPhone = findViewById(R.id.editphone);
        imageButtonDob = findViewById(R.id.editdob);
        imageButtonNationality = findViewById(R.id.editnationality);
        imageButtonNid = findViewById(R.id.nidedit);

        imageViewProfile = findViewById(R.id.image);
        buttonSave = findViewById(R.id.save);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {
            mAuth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser()
                    .getUid().toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    userName = user.getUsername();
                    userEmail = user.getEmail_id();
                    userPhone = user.getPhoneno();
                    userDob = user.getDob();
                    userNationality = user.getNationality();
                    userNid = user.getNidNo();
                    userPass = user.getPass();

                    textName.setText(userName);
                    textEmail.setText(userEmail);
                    textPhone.setText(userPhone);
                    textDob.setText(userDob);
                    textNationality.setText(userNationality);
                    textNid.setText(userNid);
                    Picasso.get().load(mAuth.getCurrentUser().getPhotoUrl()).into(imageViewProfile);

                    setDisable();
                    Toast.makeText(getApplicationContext(), user.getEmail_id(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
        }

//        date selection

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

//        date selection

        imageButtonDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textDob.setEnabled(true);
            }
        });

        imageButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textName.setEnabled(true);
            }
        });

        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textPhone.setEnabled(true);
            }
        });

        imageButtonNationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNationality.setEnabled(true);
            }
        });

        imageButtonNid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNid.setEnabled(true);
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        imageButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    userName = textName.getText().toString().trim();
                    textName.setText(userName);
                    userPhone = (String) textPhone.getText().toString().trim();
                    textPhone.setText(userPhone);
                    userNationality = textNationality.getText().toString().trim();
                    textNationality.setText(userNationality);
                    userNid = (String) textNid.getText().toString().trim();
                    textNid.setText(userNid);
                    setDisable();


                    User user = new User(userName, userEmail, userPass, userPhone, userDob, userNationality, userNid);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()
                            .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userProfile();
                                startActivity(new Intent(myprofile.this, HomePage_Map.class));
                            } else {
                                Toast.makeText(myprofile.this, "Something happened", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(myprofile.this);
        builder.setMessage("Are you sure to continue without updating?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startActivity(new Intent(myprofile.this, HomePage_Map.class));
            }
        }).setNegativeButton("NO", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void userProfile() {
    }

    //  Profile image
    private void openFileChooser() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imageViewProfile);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            mStorageRef = FirebaseStorage.getInstance().getReference("Profile Images/");
            final StorageReference fileReference = mStorageRef.child(user.getEmail()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(myprofile.this, "Upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d(TAG, "onSuccess: uri= " + uri.toString());
                                    imageUrl = uri.toString();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName)
                                                .setPhotoUri(Uri.parse(imageUrl))  // here you can set image link also.
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("TESTING", "User profile updated.");
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(myprofile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    //Profile image
    //  date selection handler
    public void showDialogOnButtonClick() {
        textDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dID);
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
            birthDate = year + "/" + month + "/" + day;
            userDob = birthDate;
            textDob.setText(birthDate);
            setDisable();
            Toast.makeText(getApplicationContext(), year + "/" + month + "/" + day, Toast.LENGTH_LONG).show();
        }
    };
//  date handler

    private void setDisable() {
        textName.setEnabled(false);
        textEmail.setEnabled(false);
        textPhone.setEnabled(false);
        textDob.setEnabled(false);
        textNationality.setEnabled(false);
        textNid.setEnabled(false);
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
