package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DeliveryPage extends AppCompatActivity {
    TextView tvQRSelect;
    Button btnQRcamera;
    QRcamZxingInsert QRSelect;
    SQLiteDatabase sqlDB;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);
        tvQRSelect = findViewById(R.id.tvQRSelect);
        btnQRcamera = findViewById(R.id.btnORCamera);

        SQLiteDatabase qrDB = this.openOrCreateDatabase("QRcodeDB",MODE_PRIVATE,null);
        Cursor cursor = qrDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'd%'", null);
        //배송코드 첫 글자 'd'로 시작하는 데이터 가져오기
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            result = "배송코드 : " + cursor.getString(0) + "\n";
            result += "보내는 이 : " + cursor.getString(1) + "\n";
            result += "우편번호 : " + cursor.getString(2) + "\n";
            result += "주소 : " + cursor.getString(3) + "\n";
            result += "연락처 : " + cursor.getString(4) + "\n";
            result += "비고 : " + cursor.getString(4) + "\n";
            result += "받는 이 : " + cursor.getString(1) + "\n";
            result += "우편번호 : " + cursor.getString(2) + "\n";
            result += "주소 : " + cursor.getString(3) + "\n";
            result += "연락처 : " + cursor.getString(4) + "\n";
            result += "비고 : " + cursor.getString(4) + "\n";
            result += "===========================================";
            tvQRSelect.setText(result);
        }
        cursor.close();
        btnQRcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryPage.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });

    }
}