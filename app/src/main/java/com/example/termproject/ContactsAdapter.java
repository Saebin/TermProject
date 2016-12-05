package com.example.termproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<MyItem> mItems = new ArrayList<MyItem>();

    public ContactsAdapter(Context context, int resource, ArrayList<MyItem> items) {
        mContext = context;
        mItems = items;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String date = mItems.get(position).nName;
        final String title = mItems.get(position).nAge;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent,false);
        }

        // Set Text 01
        TextView name = (TextView) convertView.findViewById(R.id.title);
        name.setText(mItems.get(position).nName);

        // Set Text 02
        TextView age = (TextView) convertView.findViewById(R.id.contents);
        age.setText(mItems.get(position).nAge);

        Button btn = (Button) convertView.findViewById(R.id.btnDetail);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent detailIntent = new Intent(mContext, DetailActivity.class);
                detailIntent.putExtra("detaildate", date);
                detailIntent.putExtra("title", title);
                mContext.startActivity(detailIntent);
            }
        });

        return convertView;
    }
}

class MyItem {
    int mIcon; // image resource
    String nName; // text
    String nAge;  // text

    MyItem(int aIcon, String aName, String aAge) {
        mIcon = aIcon;
        nName = aName;
        nAge = aAge;
    }
}