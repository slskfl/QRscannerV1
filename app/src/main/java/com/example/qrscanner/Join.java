package com.example.qrscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Join extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonSignup;
    TextView textviewSingin;
    TextView textviewMessage;
    ProgressDialog progressDialog;
    //define firebase object
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //initializig firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //추가해 줄 ProfileActivity
        }
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textviewSingin = (TextView) findViewById(R.id.textViewSignin);
        textviewMessage = (TextView) findViewById(R.id.textviewMessage);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        textviewSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Join.this, MainActivity.class);
                startActivity(intent); //추가해 줄 로그인 액티비티
            }
        });
    }

    //Firebse creating a new user
    private void registerUser() {
        //사용자가 입력하는 email, password를 가져온다.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(email)) {
            showToast("Email을 입력해 주세요.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("Password를 입력해 주세요.");
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            //에러발생시
                            textviewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                            showToast("회원가입에 실패했습니다.");
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}

    /*EditText edtID, edtName, edtPW, edtPWchk, edtEmail, edtTel, edtAddress;
    Button btnIDCk, btnComplete;
    RadioButton rdoUser,rdoDelivery;
    TextView tvIDmsg, tvPWmsg;
    String ID, name, pwd, email, tel, address;
    int grade=1;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    static ArrayList<String> arrayIndex=new ArrayList<String>();
    static ArrayList<String> arrayData=new ArrayList<String>();
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
        rdoUser=findViewById(R.id.rdoUser);
        rdoDelivery=findViewById(R.id.rdoDelivery);

        btnIDCk=findViewById(R.id.btnIDCk);
        btnComplete=findViewById(R.id.btnComplete);

        tvPWmsg=findViewById(R.id.tvPWmsg);
        tvIDmsg=findViewById(R.id.tvIDmsg);

        rdoUser.setChecked(true);

        //아디 중복 체크 클릭
        btnIDCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID=edtID.getText().toString();
                if(isExistID()) {
                    tvIDmsg.setVisibility(View.VISIBLE);
                    tvIDmsg.setText("중복된 아이디입니다.");
                    edtID.setText(null);
                    edtID.requestFocus();
                } else if(!isExistID()) {
                    tvIDmsg.setVisibility(View.VISIBLE);
                    tvIDmsg.setText("사용 가능한 아이디 입니다.");
                    Log.e("nina", "btnIDCk>>"+ID);
                }
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID=edtID.getText().toString();
                name=edtName.getText().toString();
                pwd=edtPW.getText().toString();
                email=edtEmail.getText().toString();
                tel=edtTel.getText().toString();
                address=edtAddress.getText().toString();
                if(rdoUser.isChecked()){
                    grade=1; //사용자
                }else if(rdoDelivery.isChecked()){
                    grade=-1; //택배원
                }
                // 비밀번호 체크
                String pwChk=edtPWchk.getText().toString();
                if(!pwd.equals(pwChk)){
                    tvPWmsg.setVisibility(View.VISIBLE);
                    edtPW.setText(null);
                    edtPWchk.setText(null);
                }else {
                    tvPWmsg.setVisibility(View.INVISIBLE);
                    if(!isExistID()){
                        postFirebaseDataBase(true);
                        getFirebaseDataBase();
                        edtID.setText("");
                        edtName.setText("");
                        edtPW.setText("");
                        edtPWchk.setText("");
                        edtEmail.setText("");
                        edtTel.setText("");
                        edtAddress.setText("");
                        showToast("데이터가 저장되었습니다.");
                    } else {
                        showToast("회원가입에 실패하였습니다.");
                    }
                }


            }
        });

    }
    //아이디 중복체크
    public boolean isExistID(){
        boolean isExist=arrayIndex.contains(ID);
        return isExist;
    }
    //데이터 저장 메서드
    public void postFirebaseDataBase(boolean add){
        reference= FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates=new HashMap<>();
        Map<String, Object> postValues=null;
        if(add) {
            FireBasePost post=new FireBasePost(ID, name, pwd ,email, tel, address, grade);
            postValues=post.toMap();
        }
        childUpdates.put("/id_list/"+ID ,postValues);
        reference.updateChildren(childUpdates);
    }
    //데이터 조회 메서드
    public void getFirebaseDataBase() {
        ValueEventListener eventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayData.clear();
                arrayIndex.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key=dataSnapshot.getKey();
                    FireBasePost get=dataSnapshot.getValue(FireBasePost.class);
                    String info[]={get.id, get.name, get.email, get.tel};
                    String result=info[0]+" "+info[1]+" "+info[2]+" "+ info[3];
                    arrayData.add(result);
                    arrayIndex.add(key);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("데이터베이스 로드 실패!!");
            }
        };
        Query data=FirebaseDatabase.getInstance().getReference().
                child("id_list").orderByChild("id");
        data.addListenerForSingleValueEvent(eventListener);
    }
    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}*/