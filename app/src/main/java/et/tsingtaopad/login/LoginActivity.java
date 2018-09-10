package et.tsingtaopad.login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.core.app.Latte;
import com.core.ui.loader.LatteLoader;
import com.core.utils.dbtutil.CheckUtil;
import com.core.utils.dbtutil.PrefUtils;

import java.lang.ref.SoftReference;

import et.tsingtaopad.R;
import et.tsingtaopad.base.BaseActivity;
import et.tsingtaopad.main.ConstValues;
import et.tsingtaopad.main.MainActivity;


/**
 * 文件名：LoginActivity.java</br>
 */
@SuppressLint("HandlerLeak")
public class LoginActivity extends BaseActivity implements OnClickListener {
    private LoginService service;

    private EditText uidEt;
    private EditText pwdEt;
    private TextView login_button;
    private AlertDialog dialog;
    private RelativeLayout login_container;


    MyHandler handler;

    /**
     * 接收子线程消息的 Handler
     */
    public static class MyHandler extends Handler {

        // 软引用
        SoftReference<LoginActivity> fragmentRef;

        public MyHandler(LoginActivity fragment) {
            fragmentRef = new SoftReference<LoginActivity>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            LoginActivity fragment = fragmentRef.get();
            if (fragment == null) {
                return;
            }

            Bundle bundle = msg.getData();
            super.handleMessage(msg);
            // 处理UI 变化
            switch (msg.what) {
                // 提示信息
                case ConstValues.WAIT1:// 正常登录
                    // 关闭缓冲界面
                    LatteLoader.stopLoading();
                    fragment.startMainInfo(bundle);
                    break;
                case ConstValues.WAIT2:// TOMAINACTIVITY
                    // 关闭缓冲界面
                    LatteLoader.stopLoading();
                    Toast.makeText(Latte.getApplicationContext(), bundle.getString("msg"), Toast.LENGTH_SHORT).show();
                    fragment.startActivityTo("MainActivity");
                    break;

                default:
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initView();
        this.initData();
    }

    private void initView() {
        // 绑定界面组件
        uidEt = (EditText) findViewById(R.id.login_et_uid);
        pwdEt = (EditText) findViewById(R.id.login_et_pwd);
        login_button = (TextView) findViewById(R.id.login_button);
        login_container = (RelativeLayout) findViewById(R.id.login_container);

        // 绑定事件
        login_button.setOnClickListener(this);

        uidEt.addTextChangedListener(watcher);
        pwdEt.addTextChangedListener(watcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    private void initData() {
        handler = new MyHandler(this);
        service = new LoginService(this, handler);
        String userGongHao = PrefUtils.getString(this, "userGongHao", "");
        uidEt.setText(userGongHao);

        /*try {
            loginIn();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void startMainInfo(Bundle bundle) {
        boolean isSuccess = bundle.getBoolean("isSuccess", false);
        String msg1 = bundle.getString("msg", "登录");//登录信息
        String isrepassword = bundle.getString("isrepassword", "111111");//还有多少天需更改密码
        if (isSuccess) {
            // 跳到主界面
            Toast.makeText(getBaseContext(), bundle.getString("msg"), Toast.LENGTH_SHORT).show();
            startActivityTo("MainActivity");
        } else {
            // 登录失败 提示信息
            Toast.makeText(Latte.getApplicationContext(), bundle.getString("msg"), Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivityTo(String atyType){
        Intent intent = null;
        if("RepasswordActivity".equals(atyType)){
            intent = new Intent(LoginActivity.this, RepasswordActivity.class);
        }else if("MainActivity".equals(atyType)){
            intent = new Intent(LoginActivity.this, MainActivity.class);
        }
        ConstValues.isDayThingWarn = true;
        startActivity(intent);
        LoginActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:// 登录
                try {

                    if (hasPermission(ConstValues.WRITE_READ_EXTERNAL_PERMISSION)) {
                        // 拥有了此权限,那么直接执行业务逻辑
                        this.loginIn();
                    } else {
                        // 还没有对一个权限(请求码,权限数组)这两个参数都事先定义好
                        requestPermission(ConstValues.WRITE_READ_EXTERNAL_CODE, ConstValues.WRITE_READ_EXTERNAL_PERMISSION);
                    }
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;


            default:
                break;
        }
    }

    @Override
    public void doWriteSDCard() {
        try {
            loginIn();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     *
     * @throws NameNotFoundException
     */
    private void loginIn() throws NameNotFoundException {
        // 弹出进度框
        LatteLoader.showLoading(this);
        String version = "V" + this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        // 调用登录服务
        service.login(uidEt.getText().toString(), pwdEt.getText().toString(), "V2.4.3.7.5");
    }

    /**
     * 更新登录按钮的状态
     */
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // 检查登录名,登录密码是否为空
            if (!(CheckUtil.isBlankOrNull(uidEt.getText().toString()) || CheckUtil.isBlankOrNull(pwdEt.getText().toString()))) {
                login_button.setEnabled(true);
                login_button.setBackgroundResource(R.drawable.bt_login_zg);
            } else {
                login_button.setEnabled(false);
                login_button.setBackgroundResource(R.drawable.bt_login_zg);
            }
        }
    };
}
