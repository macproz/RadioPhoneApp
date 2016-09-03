package com.yf.radiophoneapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;
import com.yf.phoneapp.bluetoothadapter.BluetoothDevicesAdapter;
import com.yf.phoneapp.ui.DialogUtils;
import com.yf.protocol.BtConnectStatusChange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AppSettingView extends Activity implements OnClickListener{

	Button btBack;
	View setBtName, setEq, setRadio, setVersion, setRecovery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appsettings);
		findView();
		Log.d("TAG", "bt-settingview oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void findView(){
		btBack = (Button) findViewById(R.id.setback);
		btBack.setOnClickListener(this);

		setBtName =  findViewById(R.id.set_btname);
		setEq =  findViewById(R.id.set_eq);
		setRadio =  findViewById(R.id.set_radio);
		setVersion =  findViewById(R.id.set_version);
		setRecovery =  findViewById(R.id.set_recovery);
		setBtName.setOnClickListener(this);
		setEq.setOnClickListener(this);
		setRadio.setOnClickListener(this);
		setVersion.setOnClickListener(this);
		setRecovery.setOnClickListener(this);

	}

	
	private Handler mHandler = new Handler();

	Context conntext = this;




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setback:
			finish();
			break;
		case R.id.set_btname:
			disconnectBt();
			break;
		case R.id.set_recovery:
			gotoRecovery();
			break;
		case R.id.set_version:
			gotoVersion();
			break;
		case R.id.set_radio:
			gotoRadioConfig();
			break;
		case R.id.set_eq:
			gotoEqset();
			break;

			default:
			break;
		}
	}

	private void disconnectBt(){
		View view = DialogUtils.showFirstConnectDialog(this,"SimpleRadio", R.string.judge_disconnect_bt, false);
		view.findViewById(R.id.pair_yes).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtils.hideWindow();
				finish();
			}
		});

		view.findViewById(R.id.pair_no).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideWindow();
//				finish();

			}
		});

	}

	private void gotoRecovery(){
		View view = DialogUtils.showFirstConnectDialog(this,"SimpleRadio", R.string.judge_recovery, false);
		view.findViewById(R.id.pair_yes).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogUtils.hideWindow();
				finish();
			}
		});

		view.findViewById(R.id.pair_no).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideWindow();
//				finish();

			}
		});

	}

	private void gotoVersion(){
		Intent intent = new Intent(this, AppVersionView.class);
		startActivity(intent);
	}

	private void gotoRadioConfig(){
		Intent intent = new Intent(this, RadioCfgView.class);
		startActivity(intent);
	}


	private void gotoEqset() {
		Intent intent = new Intent(this, EqSetView.class);
		startActivity(intent);
	}
}
