package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
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

public class DeliveryPage extends AppCompatActivity {
    Button btnORCam;
    ListView dListView;
    ListViewAdapter adapter;

    SQLiteDatabase sqlDB;
    Cursor cursor;
    String code, address, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);
        btnORCam=findViewById(R.id.btnORCam);
        dListView =findViewById(R.id.listview);

        adapter=new ListViewAdapter();
        dListView.setAdapter(adapter);

        //리스트뷰 보여주기
        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'd%';", null);
        cursor.moveToFirst();

        do{
            code = "코드 : "+cursor.getString(0);
            address = "주소 : "+cursor.getString(8)+"\n" + "비고 : "+cursor.getString(10);
            adapter.addItem(code, R.drawable.box, address );
            adapter.notifyDataSetChanged();

        }while(cursor.moveToNext());


        btnORCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryPage.this, QRCamDComplete.class);
                startActivity(intent);
            }
        });

        dListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(DeliveryPage.this);
                builder.setTitle("배송 물품 정보");
                builder.setIcon(R.drawable.info);
                builder.setPositiveButton("확인", null);
                builder.setMessage(code+"\n"+address);
                builder.show();

            }
        });
        cursor.close();
        sqlDB.close();
    }
}