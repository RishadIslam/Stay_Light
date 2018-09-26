package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetActivity extends AppCompatActivity {

    private static EditText emailId;
    private static TextView submit, back;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        emailId = findViewById(R.id.registered_emailid);
        submit = findViewById(R.id.forgot_button);
        back = findViewById(R.id.backToLoginBtn);

        mAuth = FirebaseAuth.getInstance();

        // Setting text selector over textviews
        /*@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            back.setTextColor(csl);
            submit.setTextColor(csl);

        } catch (Exception e) {
        }*/

        // Set Listeners over buttons
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.left_enter, R.anim.right_exit);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButtonTask();
            }
        });
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            ShowToast("Please enter your Email Id.");

            // Check if email id is valid or not
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches())
            ShowToast("Your Email Id is Invalid.");

            // Else submit email id and fetch passwod or do your stuff
        else {
            mAuth.sendPasswordResetEmail(getEmailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()) {
                       ShowToast("Check Your Email to Reset Password");
                   }
                   else {
                       Toast.makeText(ForgetActivity.this, "Fail to send reset Password to your Email", Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }

    }

    private void ShowToast(String s) {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_root));

// set a message
        TextView text;
        text = layout.findViewById(R.id.toast_error);
        text.setText(s);

// Toast...
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
