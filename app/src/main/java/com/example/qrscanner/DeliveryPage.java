package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DeliveryPage extends AppCompatActivity {
    Button btnORCam;
    ListView dListView;
    ArrayList<String> myArrayList;

    SQLiteDatabase sqlDB;
    String result="";
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);
        btnORCam=findViewById(R.id.btnORCam);
        dListView =findViewById(R.id.lvDelivery);
        myArrayList = new ArrayList<>();
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(DeliveryPage.this,
                android.R.layout.simple_list_item_1, myArrayList);
        dListView.setAdapter(myArrayAdapter);

        //리스트뷰 보여주기
        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL;", null);
        cursor.moveToFirst();
        myArrayAdapter.notifyDataSetChanged();

        do{
            String code = "코드 : "+cursor.getString(0);
            String address = "주소 : "+cursor.getString(8);
            String note = "비고 : "+cursor.getString(10);

            //Toast.makeText(DeliveryPage.this, ""+cursor.getPosition(), Toast.LENGTH_SHORT).show();
            myArrayList.add(code+"\n"+ address+"\n"+note+"\n");
            myArrayAdapter.notifyDataSetChanged();

        }while(cursor.moveToNext());
        cursor.close();
        sqlDB.close();

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
        sqlDB.close();*/
        btnORCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryPage.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });

    }

}