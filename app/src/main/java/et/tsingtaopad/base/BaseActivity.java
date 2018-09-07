package et.tsingtaopad.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.core.initbase.InitActivity;
import com.core.initbase.InitValues;
import com.core.utils.exit.ExitAppUtils;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by wxyass on 2018/8/23.
 */

public class BaseActivity extends InitActivity {

    private final static String TAG = "BaseActivity";

    protected static final int LOCKMESSAGEID = 13;

    private long lockTaskDelay = 120 * 60 * 1000;//锁屏时间

    protected static Timer lockTimer = new Timer();
    protected static LockTask lockTask;
    public static boolean isTimeToLock = false;

    final class LockTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "time down show dialog lock");
            handler.sendEmptyMessage(LOCKMESSAGEID);
        }
    }

    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<BaseActivity> fragmentRef;

        public MyHandler(BaseActivity fragment) {
            fragmentRef = new SoftReference<BaseActivity>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity fragment = fragmentRef.get();
            if (fragment == null) {
                return;
            }

            // 处理UI 变化
            switch (msg.what) {
                case LOCKMESSAGEID:
                    isTimeToLock = true;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ExitAppUtils.getInstance().createActivity(this);
        lockTask = new LockTask();
        handler = new MyHandler(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        unLockScreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lockScreen();// 把之前的锁销毁,创建新锁
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = this.getCurrentFocus();
            if (currentFocus != null) {
                IBinder windowToken = currentFocus.getWindowToken();
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        lockTask.cancel();
        //lockTimer.cancel();
        super.onDestroy();
        ExitAppUtils.getInstance().destroyActivity(this);
    }

    public void unLockScreen() {
        if (isTimeToLock) {
            //isTimeToLock = false;

//            Intent intent = new Intent(this, LockScreenActivity.class);
//            startActivity(intent);

           // Toast.makeText(MyApplication.getAppContext(),"关屏1分钟",Toast.LENGTH_SHORT).show();

            // System.exit(0);
        }
        lockTask.cancel();
        lockTimer.cancel();
        Log.i(TAG, "unlockScreen  将原任务从队列中移除");

    }

    public void lockScreen() {

        if (lockTask != null) {
            Log.i(TAG, "lockScreen 将原任务从队列中移除");
            lockTask.cancel(); //将原任务从队列中移除
        }
        Log.i(TAG, "lockScreen");
        lockTask = new LockTask();
        lockTimer = new Timer();
        lockTimer.schedule(lockTask, lockTaskDelay);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        // 正在运行的程序
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断程序是否在前台运行
     *
     * @param packageName
     * @return
     */
    public boolean isOpen(String packageName) {
        if (packageName.equals("") | packageName == null)
            return false;
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        for (RunningAppProcessInfo runinfo : runningAppProcesses) {
            String pn = runinfo.processName;
            if (pn.equals(packageName) && runinfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                return true;
        }
        return false;
    }


    // 权限相关 ↓--------------------------------------------------------------------------

    /**
     * 判断是否有指定的权限
     */
    public boolean hasPermission(String... permissions) {

        for (String permisson : permissions) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), permisson)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请指定的权限.
     */
    public void requestPermission(int code, String... permissions) {

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, code);
            // startAppSettings();
        }
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    // 定义几个常量

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case InitValues.HARDWEAR_CAMERA_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOpenCamera();
                }else{
                    Toast.makeText(getApplicationContext(),"请先开启相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.WRITE_READ_EXTERNAL_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doWriteSDCard();
                }else{
                    Toast.makeText(getApplicationContext(),"请先开启读写存储权限",Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.LOCAL_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doLocation();
                }else{
                    Toast.makeText(getApplicationContext(),"请先开启定位权限",Toast.LENGTH_SHORT).show();
                }
                break;
            case InitValues.WRITE_LOCAL_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCameraWriteSD();
                }else{
                    Toast.makeText(getApplicationContext(),"请先开启读写存储权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void doCameraWriteSD() {

    }

    // 定位
    public void doLocation() {
    }


    // 拍照
    public void doOpenCamera() {

    }

    // 读写SD卡业务逻辑,由具体的子类实现
    public void doWriteSDCard() {

    }

    // 权限相关 ↑--------------------------------------------------------------------------


}
