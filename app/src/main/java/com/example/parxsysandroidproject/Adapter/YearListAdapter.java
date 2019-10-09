package com.example.parxsysandroidproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.parxsysandroidproject.R;

import java.util.ArrayList;

public class YearListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> yearList;
    LayoutInflater inflter;


    public YearListAdapter(Context applicationContext, ArrayList<String> yearList) {
        this.context = applicationContext;
        this.yearList = yearList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return yearList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.employee_item_list_layout, null);

        TextView names = (TextView) view.findViewById(R.id.areaTitle);
            names.setText(yearList.get(position));
        if (position > 0) {

            names.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        return view;
    }
}



