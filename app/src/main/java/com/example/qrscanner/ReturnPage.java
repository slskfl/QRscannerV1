package com.example.qrscanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ReturnPage extends AppCompatActivity {
    Button btnORCam, btnSearch;
    ListView rListView;
    ListViewAdapter adapter;
    EditText edtCode;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    String code, address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_page);
        btnORCam=findViewById(R.id.btnORCam);
        rListView =findViewById(R.id.listview);
        edtCode=findViewById(R.id.edtCode);
        btnSearch=findViewById(R.id.btnSearch);
        adapter=new ListViewAdapter();
        rListView.setAdapter(adapter);

        showList("r");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search=edtCode.getText().toString();
                adapter.itemClear();
                if(search==null){
                    showList("r");
                }else {
                    showList(search);
                }
            }
        });

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
                AlertDialog.Builder builder=new AlertDialog.Builder(ReturnPage.this);
                builder.setTitle("?????? ?????? ??????");
                builder.setIcon(R.drawable.info);
                builder.setPositiveButton("??????", null);
                builder.setNegativeButton("?????? ??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri=Uri.parse("tel:" + phone);
                        Intent intent=new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                    }
                });
                builder.setMessage(code+"\n"+address+"\n"+"????????? : " + phone);
                builder.show();
            }
        });
        cursor.close();
        sqlDB.close();
    }
    void showList(String str){
        adapter.itemClear();
        sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.qrscanner/databases/QRcodeDB",
                null, SQLiteDatabase.OPEN_READONLY);
        cursor = sqlDB.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE '"+str+"%';", null);
        cursor.moveToFirst();
        do{
            code = "?????? : "+cursor.getString(0);
            address = "?????? : "+cursor.getString(8)+"\n" + "?????? : "+cursor.getString(10);
            adapter.addItem(code, R.drawable.returnbox, address );
            phone=cursor.getString(9);
            adapter.notifyDataSetChanged();
        }while(cursor.moveToNext());
    }

}