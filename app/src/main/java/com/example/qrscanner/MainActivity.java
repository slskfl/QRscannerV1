package com.example.qrscanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Button btnLogIn, btnSignUp;
    ImageButton btnUserImg, btnDeliveryImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar=getSupportActionBar();
        bar.setTitle("택배 QR코드");
        bar.setIcon(R.drawable.yellow_box);

        btnUserImg=findViewById(R.id.btnUserImg);
        btnDeliveryImg=findViewById(R.id.btnDeliveryImg);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Umain.class);
                startActivity(intent);
            }
        });

        btnDeliveryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Dmain.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Join.class);
                startActivity(intent);
            }
        });
    }
}