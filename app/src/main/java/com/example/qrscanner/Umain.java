package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class Umain extends AppCompatActivity {
    FrameLayout framebtn1, framebtn2, framebtn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umain);


        framebtn1=findViewById(R.id.framebtn1);
        framebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        framebtn2=findViewById(R.id.framebtn2);
        framebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        framebtn3=findViewById(R.id.framebtn3);
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