package com.core.utils.exit;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class ExitAppUtils {
	private List<Activity> mActivityList = new LinkedList<Activity>();
	private static ExitAppUtils instance;
	
	private ExitAppUtils(){};
	
	public static ExitAppUtils getInstance(){
		if (null==instance) {
			instance = new ExitAppUtils();
		}
		return instance;
	}

	
	public void createActivity(Activity activity){
		mActivityList.add(activity);
	}
	
	public void destroyActivity(Activity activity){
		mActivityList.remove(activity);
	}
	
	
	public void exit(){
		
		/********退出时断开MQ连接start*********/
		/*try {
			//在常量中取得登陆成功以后存储的MQ连接，断开
    		if(ConstValues.mqttClient!=null){
    			ConstValues.mqttClient.disconnect();
    		}
		} catch (Exception e) {
			Log.e("MQConnection", "MQ断开连接失败或者已经断开", e);
			e.printStackTrace();
		}*/
		/********退出时断开MQ连接end*********/
		
		for(Activity activity : mActivityList){
			activity.finish();
		}
		
		android.os.Process.killProcess(android.os.Process.myPid());    //获取PID 
		System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
	}
	 
}

