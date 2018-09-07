package com.core.utils.dbtutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PhoneInfoUtils
{
    static String TAG = "PhoneInfoUtils";

    /**
     * 获取手机IP地址
     * @return
     */
    public static String getIpAddress()
    {
        String ipAddress = "";
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {
            ex.printStackTrace();
        }
        return ipAddress;
    }

    /***
     * 获得手机mac地址(如果从设备获取到mac地址，则将mac地址存入shared共享文件中)
     * @param wifiMgr 可为null
     * @return
     */
    @SuppressLint("DefaultLocale")
    private static String getMac(Context context, WifiManager wifiMgr)
    {
        String macAddress = "";
        try
        {
            if (wifiMgr == null)
            {
                wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            }
            WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
            if (null != info)
            {
                if (StringUtils.isNotBlank(info.getMacAddress()))
                {
                    macAddress = info.getMacAddress();
                    macAddress = macAddress.toUpperCase();
                    //                    PhoneInfoUtils.saveMacToShared(macAddress);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(macAddress))
        {
            //            macAddress = "00:11:o1:ee:ad:88";
        }
        return macAddress;
    }

    /***
     * 启动wif获取mac地址线程
     */
    public static ScheduledExecutorService macSes;
    /***
     * 获取购啊购ID线程
     */
    public static ScheduledExecutorService gooagooIdSes;

    /**
     * 后台启动wifi，获取mac地址
     */
    private static void startWifi(final Context context)
    {
        try
        {
            final WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiMgr == null)
                return;
            String mac = getMac(context, wifiMgr);
            if (StringUtils.isNotBlank(mac))
            {
                //获取到mac
            }
            else
            {
                if (!wifiMgr.isWifiEnabled())
                {
                    wifiMgr.setWifiEnabled(true);
                }
                if (macSes == null || macSes.isShutdown())
                {
                    macSes = Executors.newScheduledThreadPool(1);
                    macSes.scheduleAtFixedRate(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            String mac = getMac(context, wifiMgr);
                            //url请求
                            if (StringUtils.isNotBlank(mac))
                            {
                                //获取到mac
                                macSes.shutdown();
                                macSes = null;
                            }
                        }
                    }, 20, 200, TimeUnit.MILLISECONDS);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //    /**
    //     * 把手机的mac地址存入程序的shared配置文件
    //     * @param mac
    //     */
    //    private static void saveMacToShared(String mac)
    //    {
    //        if (StringUtils.isBlank(mac))
    //            return;
    //        SharedPreferences prefer = SharedPreferencesUtils.getMacInfoSharedPreferences();
    //        Editor edit = prefer.edit();
    //        edit.putString("mac", mac);
    //        edit.commit();
    //    }
    //
    //    /**
    //     * 从程序的shared配置文件中读取mac地址
    //     * @return
    //     */
    //    public static String getMacFromShared()
    //    {
    //        SharedPreferences prefer = SharedPreferencesUtils.getMacInfoSharedPreferences();
    //        return prefer.getString("mac", "");
    //    }
}
