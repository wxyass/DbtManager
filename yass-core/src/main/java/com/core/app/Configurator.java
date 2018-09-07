package com.core.app;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.core.web.event.Event;
import com.core.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 全局信息配置器,可在Application中调用配置器
 *
 * Created by yangwenmin on 2017/10/14.
 */

public class Configurator {

    // 创建存放配置信息的Map
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();

    // 初始化图标字体的使用空间
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    // 初始化存放拦截器指示器的Map
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    // 一个全局的handler
    private static final Handler HANDLER = new Handler();

    // 构造函数  开始初始化
    private Configurator(){
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER,HANDLER);
    }

    // -- ↓ 经典懒汉式的单例模式 ↓-------------------------------------------------
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    // 使用静态内部类 创建对象
    private static  class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }
    // -- ↑ 经典懒汉式的单例模式 ↑-------------------------------------------------

    // 获取配置信息
    final HashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }

    // 配置ApiHost
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    // 将图片字体添加到集合中 , 之后执行configure()会将图片字体统一初始化到项目中
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    // 配置加载第三方图片字体// https://github.com/JoanZapata/android-iconify
    private  void  initIcons(){
        if(ICONS.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            // 将ICONS集合中的图片字体配置到项目中
            for (int i = 1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    // 检测配置信息 是否完成
    private  void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady){
            throw  new RuntimeException("Configuration is not ready, call configure");
        }
    }

    // 添加单个拦截器
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    // 添加拦截器集合
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    //浏览器加载的HOST
    public Configurator withWebHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.WEB_HOST, host);
        return this;
    }

    public Configurator withJavascriptInterface(@NonNull String name) {
        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }
    public Configurator withWebEvent(@NonNull String name, @NonNull Event event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }

    // 通过key获取配置的数据
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if(value==null){
            throw new NullPointerException(key.toString()+" 不能为 NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }

    // 初始化完成
    public final void configure(){
        initIcons();//自动初始化图片字体
        // 修改配置信息完成标记, true:初始化已完成
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }

}
