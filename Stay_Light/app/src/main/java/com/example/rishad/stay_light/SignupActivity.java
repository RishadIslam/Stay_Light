package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

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

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
       signUpButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               checkValidation();
           }
       });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.left_enter, R.anim.right_exit);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(SignupActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        }
    }


    private void checkValidation() {
        //get all edittext texts
        final String getFullName = fullName.getText().toString().trim();
        final String getEmailId = emailId.getText().toString().trim();
        final String getMobileNumber = mobileNumber.getText().toString().trim();
        final String getPassword = password.getText().toString().trim();
        String getConfirmPassword = confirmPassword.getText().toString().trim();

        if(getFullName.isEmpty()) {
            fullName.setError(getString(R.string.input_error_name));
            fullName.requestFocus();
            return;
        }

        if (getEmailId.isEmpty()) {
            emailId.setError(getString(R.string.input_error_email));
            emailId.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {
            emailId.setError(getString(R.string.input_error_email_invalid));
            emailId.requestFocus();
            return;
        }

        if (getPassword.isEmpty()) {
            password.setError(getString(R.string.input_error_password));
            password.requestFocus();
            return;
        }

        if (getPassword.length() < 6) {
            password.setError(getString(R.string.input_error_password_length));
            password.requestFocus();
            return;
        }

        if (getMobileNumber.isEmpty()) {
            mobileNumber.setError(getString(R.string.input_error_phone));
            mobileNumber.requestFocus();
            return;
        }

        if (getMobileNumber.length() != 11) {
            mobileNumber.setError(getString(R.string.input_error_phone_invalid));
            mobileNumber.requestFocus();
            return;
        }

        if (!getConfirmPassword.equals(getPassword)) {
            confirmPassword.setError(getString(R.string.input_error_password_unmatched));
            confirmPassword.requestFocus();
            return;
        }

        //insert data into firebase
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(getEmailId,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(
                            getFullName,
                            getEmailId,
                            getPassword,
                            getMobileNumber
                    );

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SignupActivity.this, "Registration not Successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



    }

}
