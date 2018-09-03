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


    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setListeners();
    }

    private void setListeners() {
        loginButton.setOnClickListener((View.OnClickListener) this);
        forgotPassword.setOnClickListener((View.OnClickListener) this);
        signUp.setOnClickListener((View.OnClickListener) this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                // If it is checked then show password else hide password
                if(isChecked) {
                    show_hide_password.setText(R.string.hide_pwd); //change checkbox text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //show password
                }
                else {
                    show_hide_password.setText(R.string.show_pwd); //change checkbox text
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        }
        );

    }

    private void initViews() {
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

    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.forgot_password:
                //replace forgot password activity with animation
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                overridePendingTransition(R.anim.right_enter, R.anim.left_exit);
                startActivity(intent);
                break;
            case R.id.createAccount:
                // Replace signup frgament with animation
                Intent intent1 = new Intent(LoginActivity.this, SignupActivity.class);
                overridePendingTransition(R.anim.right_enter, R.anim.left_exit);
                startActivity(intent1);
                break;
        }
    }
    private void checkValidation() {
        //Get Email Id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        //check pattern for email Id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        //check for both fields are empty or not
        if(getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getApplicationContext(),  "Enter both credentials.");
        }
        //check if email id is valid or not
        else if(!m.find()) {
            new CustomToast().Show_Toast(getApplicationContext(),  "Your Email Id is invalid");
        }
        //else do login and do your stuff
        else {
            Toast.makeText(getApplicationContext(), "Do Login", Toast.LENGTH_SHORT).show();
        }


    }

}
