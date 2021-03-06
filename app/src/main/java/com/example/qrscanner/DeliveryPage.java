package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    Button btnORCam, btnSearch;
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
        edtCode=findViewById(R.id.edtCode);
        btnSearch=findViewById(R.id.btnSearch);
        adapter=new ListViewAdapter();
        dListView.setAdapter(adapter);

        showList("d");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search=edtCode.getText().toString();
                adapter.itemClear();
                if(search==null){
                    showList("d");
                }else {
                    showList(search);
                }
            }
        });

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
                builder.setMessage(adapter.listViewItemList.get(position).getTitleStr()+"\n"
                        +adapter.listViewItemList.get(position).getContentStr());
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
            adapter.addItem(code, R.drawable.box, address );
            phone=cursor.getString(9);
            adapter.notifyDataSetChanged();
        }while(cursor.moveToNext());
    }
}