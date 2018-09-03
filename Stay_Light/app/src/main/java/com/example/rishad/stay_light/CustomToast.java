package com.example.rishad.stay_light;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_toast);
    }
    //custom toast method
    public void Show_Toast(Context context, String error) {
        // Get TextView id and set error
        TextView text = findViewById(R.id.toast_error);
        text.setText(error);

        Toast toast = new Toast(context); //Get Toast Context
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0); //set toast gravity and fill horizontal

        toast.setDuration(Toast.LENGTH_SHORT);

        toast.show();

    }
}