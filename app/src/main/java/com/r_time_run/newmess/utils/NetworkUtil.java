package com.r_time_run.newmess.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nuochen on 2015/9/14.
 */
public class NetworkUtil {

    /**
     * 判断网络是否连接
     * @param context
     * @return
     */
    public static boolean isNetwork(Context context){
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        if(info != null && info.isAvailable()){
            return true;
        }
        return false;
    }
}
