package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Join extends AppCompatActivity {

    EditText edtID, edtName, edtPW, edtPWchk, edtEmail, edtTel, edtAddress;
    Button btnIDCk, btnComplete;
    RadioGroup rdoG;
    RadioButton rdoUser,rdoDelivery;
    TextView tvIDmsg, tvPWmsg;
    UserDB userdb;
    DeliveryDB deliverydb;
    SQLiteDatabase sqlUserDB, sqlDeliveryDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edtID=findViewById(R.id.edtID);
        edtName=findViewById(R.id.edtName);
        edtPW=findViewById(R.id.edtPW);
        edtPWchk=findViewById(R.id.edtPWchk);
        edtEmail=findViewById(R.id.edtEmail);
        edtTel=findViewById(R.id.edtTel);
        edtAddress=findViewById(R.id.edtAddress);
        btnIDCk=findViewById(R.id.btnIDCk);
        btnComplete=findViewById(R.id.btnComplete);
        rdoG=findViewById(R.id.rdoG);
        rdoUser=findViewById(R.id.rdoUser);
        rdoDelivery=findViewById(R.id.rdoDelivery);
        userdb=new UserDB(this);
        deliverydb=new DeliveryDB(this);

//        if(!edtPW.equals(edtPWchk)){
//            tvPWmsg.setVisibility(View.VISIBLE);
//            edtPW.setText(null);
//            edtPWchk.setText(null);
//        }
        btnIDCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlUserDB=userdb.getReadableDatabase();
                sqlDeliveryDB=deliverydb.getReadableDatabase();
                Cursor cursorU, cursorD;
                cursorU=sqlUserDB.rawQuery("SELECT uID FROM userTBL " +
                        "WHERE uID='"+edtID.getText().toString()+"';", null);
                String id;
                id=cursorU.getString(1);
                if(edtID.getText().toString().equals(id)){
                    tvIDmsg.setVisibility(View.VISIBLE);
                }

            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtPW.getText().toString().equals(edtPWchk)){
//                    tvPWmsg.setVisibility(View.VISIBLE);
                    edtPW.setText(null);
                    edtPWchk.setText(null);
                }

                rdoUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        sqlUserDB=userdb.getWritableDatabase();
                        sqlUserDB.execSQL("INSERT INTO userTBL VALUES( '" + edtID.getText().toString() + "','"
                                + edtName.getText().toString() + "','" + edtPW.getText().toString() + "','"
                        + edtEmail.getText().toString()+"','"+edtTel.getText().toString()+"','"+edtAddress.getText().toString()+"');");
                        sqlUserDB.close();
                    }
                });

                rdoDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        sqlDeliveryDB=deliverydb.getWritableDatabase();
                        sqlDeliveryDB.execSQL("INSERT INTO deliveryTBL VALUES( '" + edtID.getText().toString() + "','"
                                + edtName.getText().toString() + "','" + edtPW.getText().toString() + "','"
                                + edtEmail.getText().toString()+"','"+edtTel.getText().toString()+"','"+edtAddress.getText().toString()+"');");
                        sqlDeliveryDB.close();
                    }
                });
            }
        });

    }

    //사용자 DB
    public class UserDB extends SQLiteOpenHelper {
        public UserDB(@Nullable Context context) {
            super(context, "userDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE userTBL(uID TEXT PRIMARY KEY, uName TEXT NOT NULL," +
                    "uPW TEXT NOT NULL , uEmail TEXT NOT NULL, " +
                    "uTel INTEGER NOT NULL, uAddress TEXT NOT NULL );"); // 테이블 생성
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS userTBL");
            onCreate(db);
        }
    }
    // 택배원 DB
    public class DeliveryDB extends SQLiteOpenHelper {
        public DeliveryDB(@Nullable Context context) {
            super(context, "deliveryDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE deliveryTBL(dID TEXT PRIMARY KEY, dName TEXT NOT NULL," +
                    "dPW TEXT NOT NULL , dEmail TEXT NOT NULL, " +
                    "dTel INTEGER NOT NULL, dAddress TEXT NOT NULL );"); // 테이블 생성
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS deliveryTBL");
            onCreate(db);
        }
    }
}