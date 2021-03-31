package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

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
    DBHelper dbHelper=new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_main);

        btnDelivery=findViewById(R.id.btnDelivery);
        btnReturn=findViewById(R.id.btnReturn);
        btnError=findViewById(R.id.btnError);
        btnInsertCamera=findViewById(R.id.btnInsertCamera);

        countD=findViewById(R.id.countD);
        countR=findViewById(R.id.countR);
        countE=findViewById(R.id.countE);

        countD.setText(count("d"));
        countR.setText(count("r"));
        countE.setText(count("e"));

        listview =findViewById(R.id.listview);
        adapter=new ListViewAdapter();
        listview.setAdapter(adapter);

        sql=dbHelper.getReadableDatabase();
        cursor = sql.rawQuery("select * from (select * from qrcodeTBL order by code ASC limit 7)", null);
        cursor.moveToLast();
        try {
            do {
                code = "코드 : " + cursor.getString(0);
                content = "주소 : " + cursor.getString(8) + "\n" + "비고 : " + cursor.getString(10);
                adapter.addItem(code, R.drawable.box, content);
                subStr = cursor.getString(0).substring(0, 1);
                switch (subStr) {
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
            }while (cursor.moveToPrevious()) ;
                adapter.notifyDataSetChanged();

        } catch (Exception e){
            showToast("QR코드 인식하여 데이터를 입력해주세요.");
        }
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, DeliveryPage.class);
                startActivity(intent);
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, ReturnPage.class);
                startActivity(intent);
            }
        });

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, ErrorPage.class);
                startActivity(intent);
            }
        });

        btnInsertCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });
        cursor.close();
        sql.close();

    }

    String count(String str){
        sql=dbHelper.getReadableDatabase();
        cursor = sql.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE '"+str+"%';", null);
        cursor.moveToFirst();
        int count=cursor.getCount();
        return String.valueOf(count);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}