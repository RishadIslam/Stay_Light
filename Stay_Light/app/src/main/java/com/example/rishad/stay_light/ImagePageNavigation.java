package com.example.rishad.stay_light;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ImagePageNavigation extends AppCompatActivity {

    TextView view;
    Button button;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page_navigation);

        view = findViewById(R.id.textImage);
        button = findViewById(R.id.buttonGo);

        view.setText("1.Upload images of diffrent room in next page.\n"
                        + "2.To select one of the image as your house image press SELECT HOUSE IMAGE button.\n"
                        + "3.Then long press a picture and select.\n"
                        + "4.After that press FINISH button to finish your upload.");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImagePageNavigation.this, UploadImageRoom.class));
            }
        });
    }
}
