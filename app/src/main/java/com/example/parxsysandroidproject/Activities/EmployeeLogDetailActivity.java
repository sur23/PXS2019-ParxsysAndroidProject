package com.example.parxsysandroidproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parxsysandroidproject.Adapter.RecyclerViewAdapter;
import com.example.parxsysandroidproject.Config.AppLog;
import com.example.parxsysandroidproject.Config.ConstentValues;
import com.example.parxsysandroidproject.Config.Utility;
import com.example.parxsysandroidproject.Model.EmployeeLogDetails;
import com.example.parxsysandroidproject.NetworkReciver.ConnectivityReceiver;
import com.example.parxsysandroidproject.R;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EmployeeLogDetailActivity extends AppCompatActivity {
    String TAG="EmployeeLogDetailActivity";
    String emp_id,month,year,emp_name,month_name;
    String fromdate,todate;
    EmployeeLogDetails[] employeeLogdetails;
    ArrayList<EmployeeLogDetails> list=new ArrayList<>();
    HashMap<Integer,String> dayofweek=new HashMap<>();
    RecyclerView recyclerView;
    LinearLayout progressBar;
    LinearLayout no_record_layout;
    TextView hr_show,present_show,absent_show,toolbar_title;
    String date="";
    int holiday=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_log_detail);
        Intent intent=getIntent();
        if(intent!=null){
            emp_id=intent.getStringExtra("emp_id");
            emp_name=intent.getStringExtra("emp_name");
            month=intent.getStringExtra("month");
            month_name=intent.getStringExtra("month_string");
            year=intent.getStringExtra("year");
            AppLog.Log(TAG,"------>emp_id :"+emp_id+"\n month :"+month+"\n year :"+year);
            setupGui();
            setDateValues();
            if(ConnectivityReceiver.isConnected()) {
                callGetEmployeeLogDetailApi();
            }else {
                Utility.ShowNoInterneteMessage(EmployeeLogDetailActivity.this);
            }
        }

    }

    public void setupGui(){
        recyclerView=(RecyclerView)findViewById(R.id.list);
        hr_show=(TextView) findViewById(R.id.hr_show);
        present_show=(TextView) findViewById(R.id.present_show);
        absent_show=(TextView) findViewById(R.id.absent_show);
        no_record_layout=(LinearLayout) findViewById(R.id.norecord_layout);
        progressBar=(LinearLayout) findViewById(R.id.progress_layout);
        toolbar_title=(TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(emp_name+"'s"+" Attendance Report for " +month_name+" "+ year);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void callGetEmployeeLogDetailApi() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConstentValues.GET_EMPLOYEE_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);
                        showEmployeeLogDetailJsonResponse(response);
                        AppLog.Log(TAG, "---- on Success------->" + response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);
                        AppLog.Log(TAG,"response error------>"+error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(ConstentValues.EMP_ID,emp_id);
                map.put(ConstentValues.FROM_DATE,fromdate);
                map.put(ConstentValues.TO_DATE,todate);
                AppLog.Log(TAG,"request------>"+map.toString());
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void showEmployeeLogDetailJsonResponse(String response) {
        employeeLogdetails= new Gson().fromJson(response, EmployeeLogDetails[].class);
        list = new ArrayList<>(Arrays.asList(employeeLogdetails));
         if(list.size()>0) {
             getEntrydates();
         }else {
             no_record_layout.setVisibility(View.VISIBLE);
         }

    }

    public void setDateValues(){
        if(Integer.parseInt(month)<10) {
            month="0"+month;
        }
        String dateto=findDateforMonth();
        fromdate = year + "-"+ month+"-" + "01";
        todate= year+"-"+month+"-"+dateto;
        AppLog.Log(TAG,"------>fromdate :"+fromdate+"\n todate :"+todate);

    }

    public boolean findLeapYear(String leapyear){
        int year=Integer.parseInt(leapyear);
        boolean leap = false;
        if(year % 4 == 0)
        {
            if( year % 100 == 0)
            {
                // year is divisible by 400, hence the year is a leap year
                if ( year % 400 == 0) {
                    leap = true;
                }else {
                    leap = false;
                }
            }else{
                leap = true;
            }
        }else {
            leap = false;
        }
        return leap;
    }

    public String findDateforMonth(){

        if (month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")||month.equals("12")) {
            date="31";
        } else if (month.equals("02")) {
            if(findLeapYear(year)) {
                date="29";
            }else {
                date="28";
            }
        } else {
            date="30";
        }
        getDayofweek(date);
      return date;
    }

    public void getDayofweek(String todate){
        int date=Integer.parseInt(todate);

        try {
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date dt= null;
            String input_date ="";
            for(int i=1;i<=date;i++){
                if(i<10) {
                    input_date="0"+i+"/"+month+"/"+year;
                }else {
                    input_date=i+"/"+month+"/"+year;
                }
                dt = format1.parse(input_date);
                DateFormat dateFormat=new SimpleDateFormat("EEEE");
                dayofweek.put(i,dateFormat.format(dt));
                if (dateFormat.format(dt).equals("Sunday")) {
                    holiday++;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void getEntrydates(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap<Integer,String> map = new HashMap<>();
            AppLog.Log(TAG,"map :"+list.size());
            String total="";
            double time_total=0.0;
            for (EmployeeLogDetails emp : list) {
                AppLog.Log(TAG,"emp.getEntryAt() :"+emp.getEntryAt());
                AppLog.Log(TAG,"emp.getExitAt() :"+emp.getExitAt());
                String[] date = emp.getEntryAt().split(" ");
                String[] date1 = date[0].split("-");
                 AppLog.Log(TAG,"------>date :"+date[0]+"\n date1"+date1[2]+"\n emp :"+emp);
                Date endt = formatter.parse(emp.getEntryAt());
                Date exdt = formatter.parse(emp.getExitAt());
                long diff=exdt.getTime()-endt.getTime();
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffMinutes = (diff / (60 * 1000) % 60);
                total=(String.valueOf(diffHours)+'.'+diffMinutes);
                time_total=Double.parseDouble(total);
                AppLog.Log(TAG, "------>diffHours :" + diffHours+"\n diffMinutes"+diffMinutes+"\n diff:"+diff);
                if (map.containsKey(date1[2])) {
                    double add=Double.parseDouble(map.get(date1[2])) + time_total;
                    map.put(Integer.parseInt(date1[2]),String.valueOf(add) );
                } else {
                    map.put(Integer.parseInt(date1[2]), String.valueOf(time_total));
                }
                AppLog.Log(TAG, "------>map :" + map.toString());
            }
            sortbykey(map);
            showlogdeatial(map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Function to sort map by Key
    public  void sortbykey(HashMap<Integer,String> map)
    {
        ArrayList<Integer> sortedKeys =
            new ArrayList<Integer>(map.keySet());
        Collections.sort(sortedKeys);
        for (Integer x : sortedKeys) {
            if(dayofweek.containsKey(x)) {
                dayofweek.put(x, map.get(x));
            }
        }
        sortedKeys.clear();
        sortedKeys.addAll(dayofweek.keySet());
        Collections.sort(sortedKeys);

        AppLog.Log(TAG, "------>map :" + dayofweek.toString());
        RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(this,sortedKeys,dayofweek);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    public void showlogdeatial(HashMap<Integer,String> map){
        double totalhrsum=0.0;
        ArrayList<Integer> sort =
                new ArrayList<Integer>(map.keySet());
        for(Integer x:sort){
                if(!map.get(x).contains("day")) {
                    double total = Double.parseDouble(map.get(x));
                    totalhrsum = totalhrsum + total;
                }
        }
        int total_days_present=map.size();
        int total_present=Integer.parseInt(date)-holiday;
        int total_absent=total_present-total_days_present;
        hr_show.setText(String.valueOf(totalhrsum));
        present_show.setText(String.valueOf(total_days_present));
        absent_show.setText(String.valueOf(total_absent));
        AppLog.Log(TAG, "------>totalhrsum :" + totalhrsum +"\n total_days_present:"+total_present+"\n total_absent:"+total_absent);


    }

}
