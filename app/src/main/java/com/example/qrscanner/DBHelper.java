package com.example.qrscanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "QRcodeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE qrcodeTBL(code TEXT PRIMARY KEY, sname TEXT NOT NULL," +
                "spost INTEGER NOT NULL , saddress TEXT NOT NULL, " +
                "stel TEXT NOT NULL, snote TEXT, rname TEXT, " +
                "rpost INTEGER, raddress TEXT, " +
                "rtel TEXT, rnote TEXT);"); // 테이블 생성
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS qrcodeTBL");
        onCreate(db);
    }
}