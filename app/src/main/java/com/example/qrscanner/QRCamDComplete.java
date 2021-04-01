package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRCamDComplete extends AppCompatActivity {
    Button buttonScan, btnDComplete;
    TextView tvCode, tvSname, tvSpost, tvSaddress, tvStel, tvSnote,
            tvRname, tvRpost, tvRaddress, tvRtel, tvRnote;
    String updateCode, code;
    //qr code scanner object
    private IntentIntegrator qrScan;
    //DB
    DBHelper dbHelper=new DBHelper(this);
    SQLiteDatabase sqlDB;
    Cursor cursor;
    //QRscan
    JSONObject obj;
    IntentResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_cam_d_complete);

        //View Objects
        buttonScan = (Button) findViewById(R.id.btnInsertCamera);
        btnDComplete = findViewById(R.id.btnDComplete);
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
        //DB에 정보 수정하기
        /*btnDComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCode();
            }
        });*/
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                showToast("취소");
            } else {
                //qrcode 결과가 있으면
                showToast("스캔 완료");
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
                    code=obj.getString("code");
                    String str=code.substring(0,1);

                    if(str.equals("d")) {
                        updateCode=code.replace("d", "cd");
                        btnDComplete.setText("배송 완료");
                    } else if(str.equals("r")) {
                        updateCode=code.replace("r", "cr");
                        btnDComplete.setText("반품 완료");
                    } else if(str.equals("e")) {
                        updateCode=code.replace("e", "ce");
                        btnDComplete.setText("오류 완료");
                    }
                    Log.e("test", code + ">>" + str +">>" + updateCode );

                    btnDComplete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sqlDB=dbHelper.getWritableDatabase();
                            sqlDB.execSQL("UPDATE qrcodeTBL SET code='"+updateCode+"' WHERE code='"+code+"' ;", null);
                            showToast("수정 완료");
                            sqlDB.close();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(QRCamDComplete.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    void updateCode(){
        sqlDB=dbHelper.getWritableDatabase();
        sqlDB.execSQL("UPDATE qrcodeTBL SET code='"+updateCode+"' WHERE code='"+code+"' ;", null);
        showToast("수정 완료");
        sqlDB.close();
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}