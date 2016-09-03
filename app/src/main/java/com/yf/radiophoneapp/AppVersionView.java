package com.yf.radiophoneapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yf.phoneapp.ui.DialogUtils;

public class AppVersionView extends Activity implements OnClickListener{

	Button btBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appversion);
		findView();
		Log.d("TAG", "bt-settingview oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void findView(){
		btBack = (Button) findViewById(R.id.version_back);
		btBack.setOnClickListener(this);
		TextView tvUsb = (TextView) findViewById(R.id.usbver);
		tvUsb.setText("USB:" + "可用容量2.5G/总容量7.8G");
		TextView tvImagever = (TextView) findViewById(R.id.imagever);
		tvImagever.setText("固件版本:" + "4.2.2.3.6.620");
		TextView tvsoftver = (TextView) findViewById(R.id.softver);
		tvsoftver.setText("软件版本:" + "A01.1607041840");
		TextView tvBtver = (TextView) findViewById(R.id.btver);
		tvBtver.setText("蓝牙版本:" + "0.006.1006.140708");
		TextView tvMcuver = (TextView) findViewById(R.id.mcuver);
		tvMcuver.setText("MCU版本:" + "140704V13-N3240701");




	}

	
	private Handler mHandler = new Handler();

	Context conntext = this;




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.version_back:
			finish();
			break;

		default:
			break;
		}
	}

}
