package com.example.qrscanner;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ImageView iconImgView;
    TextView tvtitle, tvContent;

    ArrayList<ListViewItem> listViewItemList=new ArrayList<ListViewItem>();

    public ListViewAdapter() {

    }
    public void itemClear(){
        listViewItemList.clear();
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos=position;
        Context context=parent.getContext();

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_item, parent, false);
        }

        iconImgView=convertView.findViewById(R.id.icon);
        tvtitle=convertView.findViewById(R.id.tvtitle);
        tvContent=convertView.findViewById(R.id.content);

        ListViewItem listViewItem=listViewItemList.get(position);

        iconImgView.setImageResource(listViewItem.getIconDrawable());
        tvtitle.setText(listViewItem.getTitleStr());
        tvContent.setText(listViewItem.getContentStr());

        return convertView;
    }

    public void addItem(String title, int icon, String content){
        ListViewItem item=new ListViewItem();

        item.setTitleStr(title);
        item.setIconDrawable(icon);
        item.setContentStr(content);

        listViewItemList.add(item);
    }

}
