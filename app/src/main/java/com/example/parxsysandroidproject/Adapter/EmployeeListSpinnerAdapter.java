package com.example.parxsysandroidproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parxsysandroidproject.Model.EmployeeList;
import com.example.parxsysandroidproject.R;

import java.util.ArrayList;

public class EmployeeListSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<EmployeeList> employeeDetailList;
    LayoutInflater inflter;


    public EmployeeListSpinnerAdapter(Context applicationContext, ArrayList<EmployeeList> employeeDetailList) {
        this.context = applicationContext;
        this.employeeDetailList = employeeDetailList;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return employeeDetailList.size();
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
        if(position==0) {
            names.setText("Select Employee List");
        }
        if (position > 0) {
            names.setText(employeeDetailList.get(position).getEmpId() + "   " + employeeDetailList.get(position).getName());
            names.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        return view;
    }
}


