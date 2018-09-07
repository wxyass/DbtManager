package com.core.utils.dbtutil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings;

/**
 * 网络状态工具类
 */
public class NetStatusUtil {
    
    /** 
     * 3G网络是否已打开
     * 
     * @param context
     * @return  true:已打开
     */
    public static boolean is3GValid(Context context) {
        
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (State.CONNECTED == mobileState) {
            return true;
        } else {
            return false;
        } 
    }
    
    /**
     * 3G无效时转向设置页面
     * 
     * @param context
     * @return
     */
    public static void is3GInvalidSetting(Context context) {
        
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (State.CONNECTED != mobileState) {
            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        } 
    }

    /** 
     * wifi是否已打开
     * 
     * @param context
     * @return  true:已打开
     */
    public static boolean isWifiValid(Context context) {
        
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifiState = cm.getNetworkInfo(
                    ConnectivityManager.TYPE_WIFI).getState();
        if (State.CONNECTED == wifiState) {
            return true;
        } else {
            return false;
        } 
    }
    
    /**
     * wifi未打开时转向设置页面
     * 
     * @param context
     * @return
     */
    public static void isWifiInvalidSetting(Context context) {
        
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (State.CONNECTED != mobileState) {
            context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        } 
    }
    
    /**
     * 网络是否可用
     * 
     * @param context
     * @return  true:可用
     */
    public static boolean isNetValid(Context context) {
        
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netStates = cm.getAllNetworkInfo();
        for (NetworkInfo item : netStates) {
            if (item.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
