package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dmain extends AppCompatActivity {
//  택배원 >> 배송 버튼 클릭 > 지도 > QR스캔 코드 or 플러스 버튼 > 지도와 내용 > 내용 클릭 시 상세 내용 펼쳐짐
    Button btnDelivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_main);

        btnDelivery=findViewById(R.id.btnDelivery);
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dmain.this, DeliveryPage.class);
                startActivity(intent);
            }
        });
    }
}