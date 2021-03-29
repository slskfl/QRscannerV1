package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dmain extends AppCompatActivity {
//  택배원 >> 배송 버튼 클릭 > 지도 > QR스캔 코드 or 플러스 버튼 > 지도와 내용 > 내용 클릭 시 상세 내용 펼쳐짐
    TextView countD,countR, countE;
    Button btnDelivery, btnReturn, btnError, btnInsertCamera;
    SQLiteDatabase sql;
    Cursor cursor;
    ListView listview;
    ListViewAdapter adapter;
    String code, content;
    String subStr;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_main);
        context=this;
        btnDelivery=findViewById(R.id.btnDelivery);
        countD=findViewById(R.id.countD);
        countR=findViewById(R.id.countR);
        countE=findViewById(R.id.countE);

        countD.setText(countDelivery());
        countR.setText(countReturn());
        countE.setText(countError());

        listview =findViewById(R.id.listview);
        adapter=new ListViewAdapter();
        listview.setAdapter(adapter);

        sql=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sql.rawQuery("SELECT * FROM qrcodeTBL;", null);
        cursor.moveToLast();

        for(int i=0; i<3; i++){
            code = "코드 : "+cursor.getString(0);
            content = "주소 : "+cursor.getString(8)+"\n" + "비고 : "+cursor.getString(10);
            subStr=cursor.getString(0).substring(0,1);
            adapter.addItem(code, R.drawable.box, content);
            switch (subStr){
                case "d":
                    adapter.addItem(code, R.drawable.box, content);
                    break;
                case "r":
                    adapter.addItem(code, R.drawable.returnbox, content);
                    break;
                case "e":
                    adapter.addItem(code, R.drawable.error, content);
                    break;
            }
            adapter.notifyDataSetChanged();
            cursor.moveToPrevious();
        }
        cursor.close();
        sql.close();

        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, DeliveryPage.class);
                startActivity(intent);
            }
        });
        btnReturn=findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, ReturnPage.class);
                startActivity(intent);
            }
        });
        btnError=findViewById(R.id.btnError);
        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, ErrorPage.class);
                startActivity(intent);
            }
        });
        btnInsertCamera=findViewById(R.id.btnInsertCamera);
        btnInsertCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });
    }

    String countDelivery(){
        sql=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sql.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'd%';", null);
        cursor.moveToFirst();
        int count=cursor.getCount();

        return String.valueOf(count);
    }
    String countReturn(){
        sql=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sql.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'r%';", null);
        cursor.moveToFirst();
        int count=cursor.getCount();

        return String.valueOf(count);
    }
    String countError(){
        sql=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sql.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'e%';", null);
        cursor.moveToFirst();
        int count=cursor.getCount();

        return String.valueOf(count);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}