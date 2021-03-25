package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ReturnPage extends AppCompatActivity {
    Button btnORCam;
    ListView rListView;
    ListViewAdapter adapter;

    SQLiteDatabase sqlDB;
    Cursor cursor;
    String code, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_page);
        btnORCam=findViewById(R.id.btnORCam);
        rListView =findViewById(R.id.listview);

        adapter=new ListViewAdapter();
        rListView.setAdapter(adapter);

        //리스트뷰 보여주기
        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'r%';", null);
        cursor.moveToFirst();

        do{
            code = "코드 : "+cursor.getString(0);
            address = "주소 : "+cursor.getString(8)+"\n" + "비고 : "+cursor.getString(10);
            adapter.addItem(code, R.drawable.returnbox, address );
            adapter.notifyDataSetChanged();

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

        rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "리스트 클릭", Toast.LENGTH_SHORT).show();
            }
        });

    }

}