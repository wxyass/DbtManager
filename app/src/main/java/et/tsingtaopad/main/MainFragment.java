package et.tsingtaopad.main;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.core.ui.loader.LatteLoader;
import com.core.utils.dbtutil.PrefUtils;
import com.core.web.WebDelegateImpl;

import et.tsingtaopad.R;
import et.tsingtaopad.base.BaseFragment;
import et.tsingtaopad.base.BaseMainFragment;


/**
 * Fragment
 * <p>
 * Created by wxyass on 2018/8/17.
 */
public class MainFragment extends BaseMainFragment implements View.OnClickListener {

    WebView webView;
    FrameLayout fl_container;

    public MainFragment() {
    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 接收传递过来的参数
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化控件
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fl_container = (FrameLayout) view.findViewById(R.id.web_fl_container);
        webView = (WebView) view.findViewById(R.id.main_webview);

    }

    // 加载数据
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        /*if(findChildFragment(LowFragment.class)==null){
            // loadRootFragment(R.id.fl_first_container, FirstHomeFragment.newInstance());
        }*/

        /*if (findChildFragment(WebDelegateImpl.class) == null) {
            initData();
        }*/

        //initData();

        String preurl = PrefUtils.getString(_mActivity,"tel","");
        //String username = PrefUtils.getString(_mActivity,"userName","");
        if(!TextUtils.isEmpty(preurl)){
            //WebDelegateImpl webDelegate = WebDelegateImpl.create(preurl+"&"+username);
            WebDelegateImpl webDelegate = WebDelegateImpl.create(preurl);
            //WebDelegateImpl webDelegate = WebDelegateImpl.create("http://wxyass.com");
            //webDelegate.setTopDelegate(this);
            loadRootFragment(R.id.web_fl_container, webDelegate);
        }else{
            Toast.makeText(_mActivity,"不能识别用户",Toast.LENGTH_SHORT).show();
        }


    }

    private void initData() {
        /*WebDelegateImpl webDelegate = WebDelegateImpl.create("http://cms.tsingtao.com.cn:8001/da/ww/index.html?areaId=1-4JF0&userId=2060471");
        webDelegate.setTopDelegate(this);
        loadRootFragment(R.id.web_fl_container, webDelegate);*/

        // 设置WebView的客户端
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 弹出进度框
                LatteLoader.showLoading(_mActivity);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 关闭进度框
                 LatteLoader.stopLoading();
            }
        });

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
        //        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);//关键点
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        webSettings.setLoadWithOverviewMode(true);

        if (webView != null) {
            webView.loadUrl("http://cms.tsingtao.com.cn:8001/da/ww/index.html?areaId=1-4JF0&userId=2060471");

            webView.reload();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.third_rl_1://
                ((MainFragment) getParentFragment()).start(new BasisViewFragment());
                break;*/
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

    }
}
