package com.core.app;

import android.content.Context;
import android.os.Handler;

/**
 * 全局配置的工具类,通过在Application中调用配置器进行配置
 *
 * 并对外提供获取配置信息的方法
 *
 * Created by yangwenmin on 2017/10/14.
 */

public final class Latte {

    // 返回配置器对象,并配置全局上下文,全局handler
    public  static  Configurator init(Context context){
        Configurator.getInstance().getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    // 根据配置器存储的key(ConfigKeys),获取配置信息
    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }

    // 获取配置器对象
    public  static  Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    // 获取全局的一个handler
    public static Handler getHandler(){
        return getConfiguration(ConfigKeys.HANDLER);
    }

    // 获取全局上下文
    public static Context getApplicationContext(){
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

}
