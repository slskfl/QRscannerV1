package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QRContentRead extends AppCompatActivity {

    Button btnDComplete, btnDQRcamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_content_read);

        btnDComplete=findViewById(R.id.btnDComplete);
        btnDQRcamera=findViewById(R.id.btnDQRcamera);

        btnDComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(QRContentRead.this);
                builder.setTitle("배송 완료");
                builder.setMessage("배송 완료 하시겠습니까?");
                builder.setNegativeButton("취소", null);
                builder.setPositiveButton("확인", null);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        btnDQRcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(QRContentRead.this, )
            }
        });
    }
}