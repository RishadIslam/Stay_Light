package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetActivity extends AppCompatActivity {

    private static EditText emailId;
    private static TextView submit, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initViews();
        setListeners();
    }
    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener((View.OnClickListener) this);
        submit.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask();
                break;

        }
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            new CustomToast().Show_Toast(getApplicationContext(), "Please enter your Email Id.");

            // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getApplicationContext(), "Your Email Id is Invalid.");

            // Else submit email id and fetch passwod or do your stuff
        else
            Toast.makeText(getApplicationContext(), "Get Forgot Password.", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        emailId = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        back = findViewById(R.id.backToLoginBtn);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            back.setTextColor(csl);
            submit.setTextColor(csl);

        } catch (Exception e) {
        }
    }
}
