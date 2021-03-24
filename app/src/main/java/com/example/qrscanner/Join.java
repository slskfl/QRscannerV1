package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Join extends AppCompatActivity {

    EditText edtID, edtName, edtPW, edtPWchk, edtEmail, edtTel, edtAddress;
    Button btnIDCk, btnComplete;
    RadioGroup rdoG;
    RadioButton rdoUser,rdoDelivery;
    TextView tvIDmsg, tvPWmsg;
    memberDB memberDB;
    SQLiteDatabase sqlUserDB;
    int grade=1;
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
        memberDB =new memberDB(this);
        tvPWmsg=findViewById(R.id.tvPWmsg);
        tvIDmsg=findViewById(R.id.tvIDmsg);
        rdoUser.setChecked(true);

        btnIDCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlUserDB= memberDB.getReadableDatabase();
                Cursor cursorU;
                cursorU=sqlUserDB.rawQuery("SELECT uID FROM memberTBL " +
                        "WHERE uID='"+edtID.getText().toString()+"';", null);
                Log.e("test", String.valueOf(cursorU));
                if(edtID.getText().toString().equals(cursorU)){
                    tvIDmsg.setVisibility(View.VISIBLE);
                } else if(!edtID.getText().toString().equals(cursorU)){
                    tvIDmsg.setVisibility(View.VISIBLE);
                    tvIDmsg.setText("사용 가능한 아이디입니다.");
                }
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw=edtPW.getText().toString();
                String pwChk=edtPWchk.getText().toString();
                if(!pw.equals(pwChk)){
                    tvPWmsg.setVisibility(View.VISIBLE);
                    edtPW.setText(null);
                    edtPWchk.setText(null);
                }else {
                    if(rdoUser.isChecked()){
                        grade=1;
                    }else if(rdoDelivery.isChecked()){
                        grade=0;
                    }
                    sqlUserDB= memberDB.getWritableDatabase();
                    sqlUserDB.execSQL("INSERT INTO memberTBL VALUES( '" + edtID.getText().toString() + "','"
                            + edtName.getText().toString() + "','" + edtPW.getText().toString() + "','"
                            + edtEmail.getText().toString()+"','"+edtTel.getText().toString()+"','"+edtAddress.getText().toString()+"',"
                            + grade+");");
                    showToast("회원가입 완료");
                }
                sqlUserDB.close();
            }
        });

    }

    //사용자 DB
    public class memberDB extends SQLiteOpenHelper {
        public memberDB(@Nullable Context context) {
            super(context, "memberDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE memberTBL(uID TEXT PRIMARY KEY, uName TEXT NOT NULL," +
                    "uPW TEXT NOT NULL , uEmail TEXT NOT NULL, " +
                    "uTel TEXT NOT NULL, uAddress TEXT NOT NULL, grade INT DEFAULT 1 );"); // 테이블 생성( 사용자>> 1, 택배원>> 0)
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS memberTBL");
            onCreate(db);
        }
    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}