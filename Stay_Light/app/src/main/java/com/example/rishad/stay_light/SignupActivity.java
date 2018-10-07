package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    int check_error = 0;
    private EditText fullName, emailId, mobileNumber, password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    protected CheckBox terms_conditions;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullName);
        emailId = findViewById(R.id.userEmailId);
        mobileNumber = findViewById(R.id.mobileNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.already_user);
        terms_conditions = findViewById(R.id.terms_conditions);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        //sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (terms_conditions.isChecked()) {
                    checkValidation();
                } else {
                    Toast.makeText(getApplicationContext(), "Check terms and condition", Toast.LENGTH_LONG).show();
                }
            }
        });
        //user already exists
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.left_enter, R.anim.right_exit);
                startActivity(intent);
            }
        });

    }

    private void checkValidation() {
        //get all edittext texts
        final String getFullName = fullName.getText().toString().trim();
        final String getEmailId = emailId.getText().toString().trim();
        final String getMobileNumber = mobileNumber.getText().toString().trim();
        final String getPassword = password.getText().toString().trim();
        String getConfirmPassword = confirmPassword.getText().toString().trim();

        if (getFullName.isEmpty()) {
            fullName.setError(getString(R.string.input_error_name));
            fullName.requestFocus();
            check_error = 1;
        }

        if (getEmailId.isEmpty()) {
            emailId.setError(getString(R.string.input_error_email));
            emailId.requestFocus();
            check_error = 1;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {
            emailId.setError(getString(R.string.input_error_email_invalid));
            emailId.requestFocus();
            check_error = 1;
        }

        if (getPassword.isEmpty()) {
            password.setError(getString(R.string.input_error_password));
            password.requestFocus();
            check_error = 1;
        }

        if (getPassword.length() < 6) {
            password.setError(getString(R.string.input_error_password_length));
            password.requestFocus();
            check_error = 1;
        }

        if (getMobileNumber.isEmpty()) {
            mobileNumber.setError(getString(R.string.input_error_phone));
            mobileNumber.requestFocus();
            check_error = 1;
        }

        if (getMobileNumber.length() != 11) {
            mobileNumber.setError(getString(R.string.input_error_phone_invalid));
            mobileNumber.requestFocus();
            check_error = 1;
        }

        if (!getConfirmPassword.equals(getPassword)) {
            confirmPassword.setError(getString(R.string.input_error_password_unmatched));
            confirmPassword.requestFocus();
            check_error = 1;
        }


        if (check_error == 0) {
            //insert data into firebase
            try {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(getEmailId, getPassword).addOnCompleteListener
                        (new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    String dob = null, nationality = null, nidNo = null;

                                    User user = new User(
                                            getFullName,
                                            getEmailId,
                                            getPassword,
                                            getMobileNumber,
                                            dob, nationality, nidNo
                                    );

                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()
                                            .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {
                                                userProfile();
                                                sendEmailVerification();//Will redirect to a browser page
                                            } else {
                                                Toast.makeText(SignupActivity.this, "Registration not Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                                } else {
                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, SignupActivity.class));
                                }
                            }

                            private void sendEmailVerification() {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (firebaseUser != null) {
                                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                showCustomToast("Check Your Email To Verify");
                                                FirebaseAuth.getInstance().signOut();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName.getText().toString().trim())
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
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

    private void showCustomToast(String s) {
        View toastview = getLayoutInflater().inflate(R.layout.email_custom_toast, null);

        Toast toast = new Toast(getApplicationContext());
        TextView textView = toastview.findViewById(R.id.customToastText);

        toast.setView(toastview);
        textView.setText(s);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

}
