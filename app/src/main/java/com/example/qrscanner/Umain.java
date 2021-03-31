package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class Umain extends AppCompatActivity {
    Button framebtn1, framebtn2, framebtn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umain);

        framebtn1=findViewById(R.id.framebtn1);
        framebtn2=findViewById(R.id.framebtn2);
        framebtn3=findViewById(R.id.framebtn3);

        framebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        framebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Umain.this, QRCamUser.class);
                startActivity(intent);
            }
        });

        framebtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel="080-000-0000";
                Uri uri=Uri.parse("tel:" + tel);
                Intent intent=new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
    }
}