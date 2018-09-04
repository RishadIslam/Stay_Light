package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements AppLoginActivity {


    private  EditText emailid, password;
    private  Button loginButton;
    private  TextView forgotPassword, signUp;
    private  CheckBox show_hide_password;
    private  LinearLayout loginLayout;
    private static Animation shakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailid = findViewById(R.id.login_emailid);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.createAccount);
        show_hide_password = findViewById(R.id.show_hide_password);
        loginLayout = findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.shake);

        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
        //login button pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        //new User button pressed
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, SignupActivity.class);
                overridePendingTransition(R.anim.right_enter, R.anim.left_exit);
                startActivity(intent1);
            }
        });

        //forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //replace forgot password activity with animation
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                overridePendingTransition(R.anim.right_enter, R.anim.left_exit);
                startActivity(intent);
            }
        });

        // Set check listener over checkbox for showing and hiding password
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // If it is checked then show password else hide password
                if(isChecked) {
                    show_hide_password.setText("Hide Password"); //change check box text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //show password
                }
                else {
                    show_hide_password.setText(R.string.show_pwd); //change checkbox text
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void checkValidation() {
        //Get Email Id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        //check for both fields are empty or not
        if(getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getApplicationContext(),  "Enter both credentials.");
        }
        //check if email id is valid or not
        else if(!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {
            new CustomToast().Show_Toast(getApplicationContext(),  "Your Email Id is invalid");
        }
        //else do login and do your stuff
        else {
            Toast.makeText(getApplicationContext(), "Do Login", Toast.LENGTH_SHORT).show();
        }


    }

}
