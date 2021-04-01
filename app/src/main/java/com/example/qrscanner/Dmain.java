package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class Dmain extends AppCompatActivity {
//  택배원 >> 배송 버튼 클릭 > 지도 > QR스캔 코드 or 플러스 버튼 > 지도와 내용 > 내용 클릭 시 상세 내용 펼쳐짐
    TextView countD,countR, countE;
    Button btnDelivery, btnReturn, btnError, btnInsertCamera;
    SQLiteDatabase sql;
    Cursor cursor;
    ListView listview;
    ListViewAdapter adapter;
    String code, content;
    String subStr;
    DBHelper dbHelper=new DBHelper(this);
    private FirebaseAuth firebaseAuth;
    static final int LOGOUT=1, DROPOUT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_main);

        firebaseAuth = FirebaseAuth.getInstance();
        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //textViewUserEmail의 내용을 변경해 준다.
        showToast(user.getEmail()+"으로 로그인 성공");

        ActionBar bar=getSupportActionBar();
        bar.setTitle("택배 QR코드");
        bar.setIcon(R.drawable.yellow_box);

        btnDelivery=findViewById(R.id.btnDelivery);
        btnReturn=findViewById(R.id.btnReturn);
        btnError=findViewById(R.id.btnError);
        btnInsertCamera=findViewById(R.id.btnInsertCamera);

        countD=findViewById(R.id.countD);
        countR=findViewById(R.id.countR);
        countE=findViewById(R.id.countE);

        countD.setText(count("d"));
        countR.setText(count("r"));
        countE.setText(count("e"));

        listview =findViewById(R.id.listview);
        adapter=new ListViewAdapter();
        listview.setAdapter(adapter);


        try {
            sql=dbHelper.getReadableDatabase();
            cursor = sql.rawQuery("select * from qrcodeTBL", null);
            cursor.moveToLast();
            int count=0;
            while(count<6){
                code = "코드 : " + cursor.getString(0);
                content = "주소 : " + cursor.getString(8) + "\n" + "비고 : " + cursor.getString(10);
                subStr = cursor.getString(0).substring(0, 1);
                switch (subStr) {
                    case "d":
                        adapter.addItem(code, R.drawable.box, content);
                        break;
                    case "r":
                        adapter.addItem(code, R.drawable.returnbox, content);
                        break;
                    case "e":
                        adapter.addItem(code, R.drawable.error, content);
                        break;
                }
                cursor.moveToPrevious();
                count++;
            }
                adapter.notifyDataSetChanged();

        } catch (Exception e){
            showToast("QR코드 인식하여 데이터를 입력해주세요.");
        }
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, DeliveryPage.class);
                startActivity(intent);
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, ReturnPage.class);
                startActivity(intent);
            }
        });

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, ErrorPage.class);
                startActivity(intent);
            }
        });

        btnInsertCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, QRcamZxingInsert.class);
                startActivity(intent);
            }
        });
        cursor.close();
        sql.close();

    }
    String count(String str){
        sql=dbHelper.getReadableDatabase();
        cursor = sql.rawQuery("SELECT * FROM qrcodeTBL WHERE code LIKE '"+str+"%';", null);
        cursor.moveToFirst();
        int count=cursor.getCount();
        return String.valueOf(count);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, LOGOUT, 0, "로그아웃");
        menu.add(0, DROPOUT, 0, "회원탈퇴");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case LOGOUT:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case DROPOUT:
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Dmain.this);
                alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Dmain.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                                finish();
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            }
                                        });
                            }
                        }
                );
                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Dmain.this, "취소", Toast.LENGTH_LONG).show();
                    }
                });
                alert_confirm.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}