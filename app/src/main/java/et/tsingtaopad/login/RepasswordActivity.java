package et.tsingtaopad.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import et.tsingtaopad.R;
import et.tsingtaopad.base.BaseActivity;
import et.tsingtaopad.main.ConstValues;


@SuppressLint("HandlerLeak")
public class RepasswordActivity extends BaseActivity implements OnClickListener {
	// private SyssettingService service;

	private Button backBt;
	private TextView titleTv;
	private EditText currentpsdEt;
	private EditText newpsdEt;
	private EditText repeatpsdEt;
	private Button submitBt;
	
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstValues.WAIT1:
				currentpsdEt.setText("");
				newpsdEt.setText("");
				repeatpsdEt.setText("");
				// 跳到登录
				Intent intent = new Intent(RepasswordActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_setting_modifypwd);
		this.initView();
		this.initData();
	}

	private void initView() {
	    

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	private void initData() {
//		titleTv.setText(getString(R.string.modify_pwd));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		default:
			break;
		}
	}

}
