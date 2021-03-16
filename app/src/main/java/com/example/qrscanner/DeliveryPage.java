package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DeliveryPage extends AppCompatActivity {
    ListView lvDelivery;
    SQLiteDatabase sqlDB;
    String result="";
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);

        lvDelivery=findViewById(R.id.lvDelivery);

        selectDB();



       /* sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        //SQLiteDatabase qrDB = this.openOrCreateDatabase("QRcodeDB",MODE_PRIVATE,null);
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code;", null);
        //배송코드 첫 글자 'd'로 시작하는 데이터 가져오기
        //cursor.moveToFirst(); LIKE 'd%'
        while(cursor.moveToNext()) {
            result += "배송코드 : " + cursor.getString(0) + "\n";
            result += "보내는 이 : " + cursor.getString(1) + "\n";
            result += "우편번호 : " + cursor.getInt(2) + "\n";
            result += "주소 : " + cursor.getString(3) + "\n";
            result += "연락처 : " + cursor.getString(4) + "\n";
            result += "비고 : " + cursor.getString(5) + "\n";
            result += "받는 이 : " + cursor.getString(6) + "\n";
            result += "우편번호 : " + cursor.getInt(7) + "\n";
            result += "주소 : " + cursor.getString(8) + "\n";
            result += "연락처 : " + cursor.getString(9) + "\n";
            result += "비고 : " + cursor.getString(10) + "\n";
            result += "===========================================" + "\n";

        }
        tvQRSelect.setText(result);
        cursor.close();
        sqlDB.close();
        btnQRcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryPage.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });*/

    }

    public void selectDB(){
        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'p%';", null);
        if(cursor.getCount()>0){
            startManagingCursor(cursor);
            DBAdapter dbAdapter = new DBAdapter(this, cursor);
            lvDelivery.setAdapter(dbAdapter);
        }
    }
}