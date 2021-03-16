package com.example.qrscanner;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DBAdapter  extends CursorAdapter {
    public DBAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //커서가 가리키는 데이터를 보여줄 새로운 뷰를 만들어 리턴하는 기능
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listview_item, parent, false);
        return v;


    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // 뷰에 커서가 가리키는 데이터를 대입하는 기능
        final TextView tvItemCode = (TextView)view.findViewById(R.id.tvItemCode);
        final TextView tvItemAdress = (TextView)view.findViewById(R.id.tvItemAdress);
        final TextView tvItemNote = (TextView)view.findViewById(R.id.tvItemNote);

        tvItemCode.setText(cursor.getString(cursor.getColumnIndex("code")));
        tvItemAdress.setText(cursor.getString(cursor.getColumnIndex("raddress")));
        tvItemNote.setText(cursor.getString(cursor.getColumnIndex("rnote")));

    }
}
