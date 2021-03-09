package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReturnPage extends AppCompatActivity {
    Button btnRQRcamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_page);
        btnRQRcamera=findViewById(R.id.btnRQRcamera);
        btnRQRcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReturnPage.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });
    }
}