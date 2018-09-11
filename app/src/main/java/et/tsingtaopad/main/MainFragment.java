package et.tsingtaopad.main;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.lang.ref.SoftReference;

import et.tsingtaopad.R;
import et.tsingtaopad.base.BaseFragment;
import et.tsingtaopad.base.BaseMainFragment;
import et.tsingtaopad.version.DownApkFragment;
import et.tsingtaopad.version.VersionService;


/**
 * Fragment
 * <p>
 * Created by wxyass on 2018/8/17.
 */
public class MainFragment extends BaseMainFragment implements View.OnClickListener {

    WebView webView;
    FrameLayout fl_container;
    private VersionService versionService;

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

        //initData();

        String preurl = PrefUtils.getString(_mActivity,"tel","");
        if(!TextUtils.isEmpty(preurl)){
            WebDelegateImpl webDelegate = WebDelegateImpl.create(preurl);
            loadRootFragment(R.id.web_fl_container, webDelegate);
        }else{
            Toast.makeText(_mActivity,"不能识别用户",Toast.LENGTH_SHORT).show();
        }

        // 检查版本


    }

    // 加载原始webview
    private void initData() {
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

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<MainFragment> fragmentRef;

        public MyHandler(MainFragment fragment) {
            fragmentRef = new SoftReference<MainFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            MainFragment fragment = fragmentRef.get();
            if (fragment == null) {
                return;
            }

            // 处理UI 变化
            switch (msg.what) {
                /*case SHOWDOWNLOADDIALOG:
                    fragment.showDownloadDialog();
                    break;
                case UPDATEDOWNLOADDIALOG: // 督导输入数据后
                    fragment.showDownloading(msg);
                    break;
                case DOWNLOADFINISHED: // 督导输入数据后
                    fragment.stopDownloadDialog();
                    break;*/
                case ConstValues.WAIT5: // 升级进度弹窗
                    Bundle bundle = msg.getData();
                    String apkUrl = (String) bundle.getSerializable("apkUrl");
                    String apkName = (String) bundle.getSerializable("apkName");
                    fragment.startFrag(apkUrl,apkName);
                    break;
                case ConstValues.WAIT6: // 无需升级
                    fragment.showToa();
                    break;
            }
        }
    }

    private void showToa() {
        Toast.makeText(getActivity(), "已是最新版本,无需更新", Toast.LENGTH_SHORT).show();
    }

    private void startFrag(String apkUrl,String apkName) {

        Bundle bundle = new Bundle();
        bundle.putString("apkUrl", apkUrl);//
        bundle.putString("apkName", apkName);//
        DownApkFragment downApkFragment = new DownApkFragment();
        downApkFragment.setArguments(bundle);
        // changeHomeFragment(downApkFragment, "downapkfragment");
        start(downApkFragment);
    }

}
