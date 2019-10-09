package com.example.parxsysandroidproject.Config;

public class AppLog {
    public static final boolean isDebug = true;

    public static final void Log(String tag, String message) {
        if (isDebug) {
            android.util.Log.i(tag, message + "");
        }
    }

}
