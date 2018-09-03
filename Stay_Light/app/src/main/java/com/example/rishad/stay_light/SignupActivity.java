package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private  static EditText fullName, emailId, mobileNumber, password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        setListeners();
    }

    private void setListeners() {
        signUpButton.setOnClickListener((View.OnClickListener) this);
        login.setOnClickListener((View.OnClickListener) this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                //call checkValidation method
                checkValidation();
                break;
            case R.id.already_user:
                //replace login fragment
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkValidation() {
        //get all edittext texts
        String getFullName = fullName.getText().toString().trim();
        String getEmailId = emailId.getText().toString().trim();
        String getMobileNumber = mobileNumber.getText().toString().trim();
        String getPassword = password.getText().toString().trim();
        String getConfirmPassword = confirmPassword.getText().toString().trim();

        //pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {
            new CustomToast().Show_Toast(getApplicationContext(), "All fields are required.");
        }
        // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getApplicationContext(), "Your Email Id is Invalid.");
            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getApplicationContext(), "Both password doesn't match.");
            // Make sure user should check Terms and Conditions checkbox
        else if (password.length() < 8)
            new CustomToast().Show_Toast(getApplicationContext(), "Password too short. Enter Minimum 8 characters.");
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getApplicationContext(), "Please select Terms and Conditions.");
        else
        {
            //insert data into firebase
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private void initViews() {
        fullName = findViewById(R.id.fullName);
        emailId = findViewById(R.id.userEmailId);
        mobileNumber = findViewById(R.id.mobileNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.already_user);
        terms_conditions = findViewById(R.id.terms_conditions);

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }
}
