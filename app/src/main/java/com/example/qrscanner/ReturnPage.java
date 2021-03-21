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

public class ReturnPage extends AppCompatActivity {
    Button btnORCam;
    ListView dListView;
    ArrayList<String> myArrayList;

    SQLiteDatabase sqlDB;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_page);
        btnORCam=findViewById(R.id.btnORCam);
        dListView =findViewById(R.id.lvDelivery);
        myArrayList = new ArrayList<>();
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(ReturnPage.this,
                android.R.layout.simple_list_item_1, myArrayList);
        dListView.setAdapter(myArrayAdapter);

        //리스트뷰 보여주기
        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'r%';", null);
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

        btnORCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReturnPage.this, QRCamDComplete.class);
                startActivity(intent);
            }
        });

    }

}