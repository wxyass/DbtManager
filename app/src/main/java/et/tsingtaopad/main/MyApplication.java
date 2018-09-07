package et.tsingtaopad.main;

import android.app.Application;
import android.content.Context;

import com.core.app.Latte;
import com.core.icon.FontEcModule;
import com.core.net.HttpUrl;
import com.core.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import et.tsingtaopad.R;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by wxyass on 2018/8/17.
 */

public class MyApplication extends Application {

    public static final String TAG = "MyApplication";
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        MyApplication.context = getApplicationContext();


        // 建议在Application里初始化
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                //.debug(BuildConfig.DEBUG)// // 该值是true时（即debug环境），stackViewMode才会生效； 否则不显示
                .debug(false)// // 该值是true时（即debug环境），stackViewMode才会生效； 否则不显示
                // 更多查看wiki或demo
                .install();

        // 通过全局配置器,配置参数
        Latte.init(this)// 配置ApplicationContext,全局handler
                .withIcon(new FontAwesomeModule())// 配置字体图标
                .withIcon(new FontEcModule())// 配置另一种字体图标
                .withApiHost(HttpUrl.API_HOST)// 配置ApiHost
                .withInterceptor(new DebugInterceptor("test", R.raw.test))// 拦截url请求中包含test的url请求
                .withJavascriptInterface("latte")
                //添加Cookie同步拦截器
                .withWebHost("https://www.baidu.com/")
                .configure();// 修改→配置完成的标记true


        // 崩溃收集
        CrashHandler.getInstance().init(this);



    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
