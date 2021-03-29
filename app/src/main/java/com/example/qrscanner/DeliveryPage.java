package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class DeliveryPage extends AppCompatActivity {
    Button btnORCam;
    ListView dListView;
    ListViewAdapter adapter;
    EditText edtCode;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    String code, address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_page);
        btnORCam=findViewById(R.id.btnORCam);
        dListView =findViewById(R.id.listview);

        adapter=new ListViewAdapter();
        dListView.setAdapter(adapter);

        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);

        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전
                cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'd%';", null);
                cursor.moveToFirst();
                do{
                    code = "코드 : "+cursor.getString(0);
                    address = "주소 : "+cursor.getString(8)+"\n" + "비고 : "+cursor.getString(10);
                    adapter.addItem(code, R.drawable.box, address );
                    phone=cursor.getString(9);
                    adapter.notifyDataSetChanged();
                }while(cursor.moveToNext());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 변화가 있을 경우
                cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE '"
                        +s+"%';", null);
                cursor.moveToFirst();
                do{
                    code = "코드 : "+cursor.getString(0);
                    address = "주소 : "+cursor.getString(8)+"\n" + "비고 : "+cursor.getString(10);
                    adapter.addItem(code, R.drawable.box, address );
                    phone=cursor.getString(9);
                    adapter.notifyDataSetChanged();
                }while(cursor.moveToNext());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 입력이 끝났을 경우
            }
        });


        //리스트뷰 보여주기
        /*sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE 'd%';", null);
        cursor.moveToFirst();
        do{
            code = "코드 : "+cursor.getString(0);
            address = "주소 : "+cursor.getString(8)+"\n" + "비고 : "+cursor.getString(10);
            adapter.addItem(code, R.drawable.box, address );
            phone=cursor.getString(9);
            adapter.notifyDataSetChanged();
        }while(cursor.moveToNext());*/

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
                builder.setNegativeButton("전화 걸기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri=Uri.parse("tel:" + phone);
                        Intent intent=new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                    }
                });
                builder.setMessage(code+"\n"+address+"\n"+"연락처 : " + phone);
                builder.show();
            }
        });
        cursor.close();
        sqlDB.close();
    }
}