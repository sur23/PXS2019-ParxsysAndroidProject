package com.example.parxsysandroidproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parxsysandroidproject.Adapter.EmployeeListSpinnerAdapter;
import com.example.parxsysandroidproject.Adapter.MonthListAdapter;
import com.example.parxsysandroidproject.Adapter.YearListAdapter;
import com.example.parxsysandroidproject.Config.AppLog;
import com.example.parxsysandroidproject.Config.ConstentValues;
import com.example.parxsysandroidproject.Config.Utility;
import com.example.parxsysandroidproject.Model.EmployeeList;
import com.example.parxsysandroidproject.NetworkReciver.ConnectivityReceiver;
import com.example.parxsysandroidproject.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EmployeeListActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener
    {
        String TAG="EmployeeListActivity";
    private Spinner emp_list_spinner,date_spinner,year_spinner;
    private Button attendance_report_button;
    private LinearLayout id_required;
    private ProgressBar progressBar;
    EmployeeList[] employeeLists;
    ArrayList<EmployeeList> list;
    ArrayList<String> monthlist;
    ArrayList<EmployeeList> employeeNameList;
    ArrayList<String> yearList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeelist);
        attendance_report_button=(Button)findViewById(R.id.attendance_report_button);
        emp_list_spinner=(Spinner)findViewById(R.id.emp_list_spinner);
        id_required=(LinearLayout)findViewById(R.id.id_required);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        date_spinner=(Spinner)findViewById(R.id.date_spinner);
        year_spinner=(Spinner)findViewById(R.id.year_spinner);

        if(ConnectivityReceiver.isConnected()) {
            callGetEmployeeListApi();
        }else {
            Utility.ShowNoInterneteMessage(EmployeeListActivity.this);
        }

        attendance_report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emp_list_spinner.getSelectedItemPosition()!=0) {
                    id_required.setVisibility(View.GONE);
                    int id=emp_list_spinner.getSelectedItemPosition();
                    int m=date_spinner.getSelectedItemPosition();
                    int y=year_spinner.getSelectedItemPosition();
                    String emp_id = employeeNameList.get(id).getEmpId();
                    String emp_name=employeeNameList.get(id).getName();
                    String month = String.valueOf(m+1);
                    String month_string=monthlist.get(m);
                    String year = yearList.get(y);
                    AppLog.Log(TAG,"------>emp_id :"+emp_id+"\n month :"+month+"\n year :"+year);
                    Intent intent=new Intent(EmployeeListActivity.this,EmployeeLogDetailActivity.class);
                    intent.putExtra("emp_id",emp_id);
                    intent.putExtra("emp_name",emp_name);
                    intent.putExtra("month",month);
                    intent.putExtra("month_string",month_string);
                    intent.putExtra("year",year);
                    startActivity(intent);


                }else {
                    id_required.setVisibility(View.VISIBLE);
                }

            }
        });


    }



        public void callGetEmployeeListApi() {

            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ConstentValues.GET_EMPOLYEE_LIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressBar.setVisibility(View.GONE);
                            showEmployeeListJsonResponse(response);
                            AppLog.Log("showEmployeeListJsonResponse", "---- on Success" + response);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            progressBar.setVisibility(View.GONE);
                            AppLog.Log("responce error", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    return map;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        private void showEmployeeListJsonResponse(String response) {

            employeeLists= new Gson().fromJson(response,EmployeeList[].class);
            list = new ArrayList<>(Arrays.asList(employeeLists));
            if(list.size()>0){
                employeeNameList=new ArrayList<>(list);
                for(EmployeeList employeeList:list){
                    if(employeeList.getEmpId()==null){
                        employeeNameList.remove(employeeList);
                    }
                }
               setEmployeeName();
                setMonthList();
                setYearsList();
            }
        }

        private void setEmployeeName(){
            EmployeeListSpinnerAdapter employeeListSpinnerAdapter=new EmployeeListSpinnerAdapter(this,employeeNameList);
            emp_list_spinner.setAdapter(employeeListSpinnerAdapter);
            emp_list_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    AppLog.Log(TAG,"employeeNameList----------->"+employeeNameList.get(i).getEmpId());
                    emp_list_spinner.setSelection(i);
                    if(i>0){
                        id_required.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        private void setMonthList(){
            monthlist = new ArrayList<>(Arrays.asList(ConstentValues.Months));
            MonthListAdapter monthListAdapter=new MonthListAdapter(this,monthlist);
            date_spinner.setAdapter(monthListAdapter);
            date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Log.e("answer_get","----------->"+pause_list.get(i));
                    date_spinner.setSelection(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        private void setYearsList(){
            final int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = 1990; i <= thisYear; i++) {
                yearList.add(String.valueOf(i));
            }
            YearListAdapter yearListAdapter=new YearListAdapter(this,yearList);
            year_spinner.setAdapter(yearListAdapter);
            year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Log.e("answer_get","----------->"+pause_list.get(i));
                    year_spinner.setSelection(i);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        @Override
        protected void onResume() {
            super.onResume();
            // register connection status listener
            ConstentValues.getInstance().setConnectivityListener(this);
        }

        @Override
        public void onNetworkConnectionChanged(boolean isConnected) {
            if(ConnectivityReceiver.isConnected()) {
                callGetEmployeeListApi();
            }else {
                Utility.ShowNoInterneteMessage(EmployeeListActivity.this);
            }

        }
    }
