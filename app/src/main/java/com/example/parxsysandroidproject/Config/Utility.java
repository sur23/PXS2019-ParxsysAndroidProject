package com.example.parxsysandroidproject.Config;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.parxsysandroidproject.R;

public class Utility {
    private static Dialog valid_dialog;
    private static Dialog internet_dailog;
    private static Context _context;
    //Show No Internet Message Dialog box Method
    public static void ShowNoInterneteMessage(Context context) {
        if(internet_dailog!=null){
            return;
        }
        if(context!=null) {
            internet_dailog = new Dialog(context);
            internet_dailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            internet_dailog.setContentView(R.layout.net_status_layout);
            internet_dailog.setCanceledOnTouchOutside(false);
            LinearLayout lin_ok = (LinearLayout) internet_dailog.findViewById(R.id.lin_ok);
            // if button is clicked, close the custom dialog
            lin_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    internet_dailog.dismiss();
                    internet_dailog = null;
                }
            });
            try {
                internet_dailog.show();
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
        }


    }
}
