package com.example.parxsysandroidproject.Config;

import android.app.Application;
import android.content.Context;

import com.example.parxsysandroidproject.Activities.EmployeeListActivity;
import com.example.parxsysandroidproject.NetworkReciver.ConnectivityReceiver;

public class ConstentValues extends Application {

    private static final String BASE_URL = "http://parxsys.com/accounting/";
    public static final String GET_EMPOLYEE_LIST = BASE_URL + "att_rprt_api.php?e76c37b493ea168cea60b8902072387caf297979";
    public static final String GET_EMPLOYEE_DETAILS = BASE_URL + "att_rprt_api.php?e76c37b493ea168cea60b8902072387caf297979";
    public static final String[] Months = new String[] { "January", "February",
            "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };

    public static final String EMP_ID="emp_id";
    public static final String FROM_DATE="from_dt";
    public static final String TO_DATE="to_dt";

    private static ConstentValues sInstance;
    public static ConstentValues getInstance(){
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
