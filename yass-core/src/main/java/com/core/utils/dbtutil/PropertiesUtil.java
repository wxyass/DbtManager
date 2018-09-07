package com.core.utils.dbtutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;

/**
 * 系统Properties读取工具类
 */
public class PropertiesUtil extends Properties {

    // 版本序列号
    private static final long serialVersionUID = -1471956695671101823L;
    
    // Tag
    private static final String TAG = "PropertiesUtil";
    
    private static PropertiesUtil mPro; 
    public static final String configPath = "/assets/config/fsaconfig.properties";

    private PropertiesUtil() {
        
    };

    public static PropertiesUtil getInstance() {  
        if (mPro == null) {  
            mPro = new PropertiesUtil();  
            try {  
                InputStream is = PropertiesUtil.
                        class.getResourceAsStream(configPath);  
                mPro.load(is);  
                is.close();  
            } catch (Exception e) {  
                Log.e(TAG, "加载properties时异常", e); 
            }  
        }  
        return mPro;  
    }
    
    /**
     * 获取属性
     * 
     * @param key
     * @return
     */
    public static String getProperties(String key) {
        return getInstance().getProperty(key);
    }
    
    /**
     * 获取属性
     * 
     * @param key
     * @return
     */
    public static String getProperties(String key, String defalut) {
        return getInstance().getProperty(key, defalut);
    }
    
    /**
     * 更新缓存配置数据
     * 
     * @param context
     * @param proName   属性名称
     * @param proValue  属性值
     */
    public static void updateSharedPreferences(Context context, String proName, String proValue) {
        
        SharedPreferences sp = context.getSharedPreferences("SysSharedPreferences", 0);
        if (sp != null) {
            sp.edit().putString(proName, proValue).commit();
            sp.getString(proName, "");
        }
    }
    
    /**
     * 获取缓存配置数据
     * 
     * @param context
     * @param proName   属性名称
     * @param proValue  默认值
     * @return
     */
    public static String getSharedPreferences(Context context, String proName, String proValue) {
        String value = proValue;
        SharedPreferences sp = context.getSharedPreferences("SysSharedPreferences", 0);
        if (sp != null) {
            value = sp.getString(proName, proValue);
        }
        return value;
    }
}
