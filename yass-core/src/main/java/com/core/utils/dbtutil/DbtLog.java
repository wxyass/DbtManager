package com.core.utils.dbtutil;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.core.app.Latte;
import com.core.initbase.InitValues;

import java.text.SimpleDateFormat;
import java.util.Date;


/***
 * 
 *
 */
public class DbtLog
{
    static String TAG = "DbtLog";
    private static String filename = "log.txt";

    public static void logUtils(String tag, String msg)
    {
        String s = tag + "-->" + msg;
        d(TAG, s);
        write(s);
    }

    /***
     * 打印日志
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg)
    {
        if (InitValues.isOnline)
        {
            Log.d(tag, msg);
        }
    }

    /***
     * 打印日志
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg)
    {
        if (InitValues.isOnline)
        {
            Log.i(tag, msg);
        }
    }

    /***
     * 打印日志
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg)
    {
        if (InitValues.isOnline)
        {
            Log.e(tag, msg);
        }
    }

    /***
     * 打印日志
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg, Throwable tr)
    {
        if (InitValues.isOnline)
        {
            Log.e(tag, msg, tr);
        }
    }

    /***
     * 打印日志
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg)
    {
        if (InitValues.isOnline)
        {
            Log.w(tag, msg);
        }
    }

    /***
     * 写入文本到日志文件中 
     * 在内存卡 mnt/shell/emulated/0/dbt/et.tsingtaopad/log/log.txt 写入日志
     * @param value
     */
    public static void write(String value)
    {
        if (InitValues.isOnline)
        {
            Log.e("DbtLog", value);
        	try{
        		//currentTimeMillis--取当前系统时间的毫秒, long 类型,记住!
        		String newValue = getDataFormat(System.currentTimeMillis()) + " "+getVersion() + " "+value;
        		String sdcardPath = Environment.getExternalStorageDirectory() + "";
        		String DbtPATH = sdcardPath + "/dbt/et.tsingtaopad";
        		String LOGPATH = DbtPATH + "/log/";
        		LogUtils.getInstances().write(LOGPATH, filename, newValue);
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
    }
    
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = Latte.getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(Latte.getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "未获取版本号";
        }
    }

	/***
	 * 写入文本到日志文件中
	 * 在内存卡 mnt/shell/emulated/0/dbt/et.tsingtaopad/log/filename.txt 写入日志
	 * @param filename
	 * @param value
	 */
    public static void write(String filename,String value)
    {
        if (InitValues.isOnline)
        {
        	try{
                String newValue = getDataFormat(System.currentTimeMillis()) + " "+getVersion() + " "+value;
                String sdcardPath = Environment.getExternalStorageDirectory() + "";
                String DbtPATH = sdcardPath + "/dbt/et.tsingtaopad";
                String LOGPATH = DbtPATH + "/log/";
                LogUtils.getInstances().write(LOGPATH, filename, newValue);
            }catch(Exception e){
        		e.printStackTrace();
        	}
        }
    }
    
    /***
     * 获取当前时间
     * @param timeInMillis
     * @return
     */
    private static String getDataFormat(long timeInMillis)
    {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dataFormat.format(new Date(timeInMillis));
    }
}
