package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRcamZxingInsert extends AppCompatActivity {
    Button buttonScan, btnDBInsert;
    TextView tvCode, tvSname, tvSpost, tvSaddress, tvStel, tvSnote,
            tvRname, tvRpost, tvRaddress, tvRtel, tvRnote;
    String code, sname, spost, saddress, stel, snote, rname, rpost, raddress, rtel, rnote;
    //qr code scanner object
    private IntentIntegrator qrScan;
    //DB
    QRcodeDB qrcodedb;
    SQLiteDatabase sqlDB;
    //QRscan
    JSONObject obj;
    IntentResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcam_zxing);

        //View Objects
        buttonScan = (Button) findViewById(R.id.btnInsertCamera);
        btnDBInsert=findViewById(R.id.btnDBInsert);
        tvCode = (TextView) findViewById(R.id.tvCode);
        tvSname = (TextView) findViewById(R.id.tvSname);
        tvSpost = (TextView) findViewById(R.id.tvSpost);
        tvSaddress = (TextView) findViewById(R.id.tvSaddress);
        tvStel = (TextView) findViewById(R.id.tvStel);
        tvSnote = (TextView) findViewById(R.id.tvSnote);
        tvRname = (TextView) findViewById(R.id.tvRname);
        tvRpost = (TextView) findViewById(R.id.tvRpost);
        tvRaddress = (TextView) findViewById(R.id.tvRaddress);
        tvRtel = (TextView) findViewById(R.id.tvRtel);
        tvRnote = (TextView) findViewById(R.id.tvRnote);
        //DB생성
        qrcodedb=new QRcodeDB(this);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //button onClick
        buttonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
        btnDBInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB= qrcodedb.getWritableDatabase();
                try {
                    sqlDB.execSQL("INSERT INTO qrcodeTBL VALUES( '" + obj.getString("code") + "','"
                            + obj.getString("sname") + "','" + obj.getString("spost") + "','"
                            + obj.getString("saddress") +"','"+ obj.getString("stel")+"','"
                            + obj.getString("snote") +"','"+ obj.getString("rname") +"','"
                            + obj.getString("rpost") +"','"+ obj.getString("raddress") +"','"
                            + obj.getString("rtel") +"','"+ obj.getString("rnote") +"');");
                    showToast("레코드 입력 완료");
                    /*sqlDB.execSQL("INSERT INTO qrcodeTBL VALUES( '" + obj.getString("code") + "','"
                             + tvSname.getText().toString() + "','" + tvSpost.getText().toString() + "','"
                             + tvSaddress.getText().toString()+"','"+ tvStel.getText().toString()+"','"
                             + tvSnote.getText().toString()+"','"+ tvRname.getText().toString()+"','"
                             + tvRpost.getText().toString()+"','"+tvRaddress.getText().toString()+"','"
                             + tvRtel.getText().toString()+"','"+ tvRnote.getText().toString()+"');");*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sqlDB.close();
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(QRcamZxingInsert.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(QRcamZxingInsert.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    obj = new JSONObject(result.getContents());
                    tvCode.setText(obj.getString("code"));
                    tvSname.setText(obj.getString("sname"));
                    tvSpost.setText(obj.getString("spost"));
                    tvSaddress.setText(obj.getString("saddress"));
                    tvStel.setText(obj.getString("stel"));
                    tvSnote.setText(obj.getString("snote"));
                    tvRname.setText(obj.getString("rname"));
                    tvRpost.setText(obj.getString("rpost"));
                    tvRaddress.setText(obj.getString("raddress"));
                    tvRtel.setText(obj.getString("rtel"));
                    tvRnote.setText(obj.getString("rnote"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(QRcamZxingInsert.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public class QRcodeDB extends SQLiteOpenHelper {
        public QRcodeDB(@Nullable Context context) {
            super(context, "QRcodeDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE qrcodeTBL(code TEXT PRIMARY KEY, sname TEXT NOT NULL," +
                    "spost INTEGER NOT NULL , saddress TEXT NOT NULL, " +
                    "stel TEXT NOT NULL, snote TEXT, rname TEXT NOT NULL, " +
                    "rpost INTEGER NOT NULL, raddress TEXT NOT NULL, " +
                    "rtel TEXT NOT NULL, rnote TEXT);"); // 테이블 생성
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS qrcodeTBL");
            onCreate(db);
        }
    }
}