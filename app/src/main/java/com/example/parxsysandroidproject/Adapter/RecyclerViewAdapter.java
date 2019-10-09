package com.example.parxsysandroidproject.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parxsysandroidproject.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    
    Context context;
   // ArrayList<String> dayofweek;
    ArrayList<Integer> datesofmonth;
    HashMap<Integer,String> map;
    int date;
    public RecyclerViewAdapter(Context context, ArrayList<Integer> datesofmonth,HashMap<Integer,String> map) {
       this.date=date;
     //  this.dayofweek=dayofweek;
       this.datesofmonth=datesofmonth;
       this.map=map;
       this.context=context;
    }
   

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_date_item_layout, parent, false);
        return new RecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {

            holder.date_show.setText(String.valueOf(datesofmonth.get(position)));
            holder.hr_show.setText(String.valueOf(map.get(datesofmonth.get(position))));


    }

    


    @Override
    public int getItemCount() {
        return datesofmonth.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView date_show,hr_show;


        ViewHolder(View itemView) {
            super(itemView);
            date_show = (TextView) itemView.findViewById(R.id.date_show);
            hr_show = (TextView) itemView.findViewById(R.id.hr_show);

        }


    }

}

