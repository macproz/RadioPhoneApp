package com.yf.radiophoneapp;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;
import com.yf.phoneapp.data.BtDevice;
import com.yf.phoneapp.service.BluetoothSppConnection;
import com.yf.phoneapp.ui.DialogUtils;
import com.yf.phoneapp.util.UrrSourceType;
import com.yf.protocol.AppProtocol;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,ServiceConnection{
	private static final String TAG = "MainActivity";
	private static final String PHONE_APP_SERVICE = "com.yf.phoneapp.service";
	private PhoneAppControl mControl = null;
	public  FragmentTransaction mFragmentTransaction;
	private RadioFragment mRadioFragment;
	private UsbFragment mUsbFragment;
	private BtMusicFragment mBtMusicFragment;
	private Button btRadio,btUsb,btBlueMusic,btPhone;
	private int id_layout_content=R.id.id_layout_content;
	private List<Integer> mButtonIds = Arrays.asList(
			R.id.bt_raido,
			R.id.bt_music,
			R.id.bt_bluetoothmusic,
			R.id.bt_phone);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
		getDisplayInfomation();
//		initState();
    }

	private void initState() {
		UrrSourceType.setOnUrrSourceType(new UrrSourceType.OnUrrSourceType() {
			@Override
			public void OnurrSourceTypeListener(int type) {
				setViewState(type);
			}
		});
	}

	@Override
    protected void onDestroy() {
    	super.onDestroy();
		Toast.makeText(this,"------onDestroy----",Toast.LENGTH_SHORT).show();
    	unbindService(this);
		if(DialogUtils.isDialogShowing()){
			DialogUtils.destory();
		}

    }


	private void findView() {
		btRadio = (Button) findViewById(R.id.bt_raido);
		btRadio.setOnClickListener(this);
		btUsb = (Button) findViewById(R.id.bt_music);
		btUsb.setOnClickListener(this);
		btBlueMusic = (Button) findViewById(R.id.bt_bluetoothmusic);
		btBlueMusic.setOnClickListener(this);
		btPhone = (Button) findViewById(R.id.bt_phone);
		btPhone.setOnClickListener(this);
		
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mRadioFragment = new RadioFragment();
		mUsbFragment = new UsbFragment();
		mBtMusicFragment = new BtMusicFragment();
		
		mFragmentTransaction.add(id_layout_content, mRadioFragment);
		mFragmentTransaction.add(id_layout_content, mUsbFragment);
		mFragmentTransaction.add(id_layout_content, mBtMusicFragment);

		mFragmentTransaction.hide(mUsbFragment);
		mFragmentTransaction.hide(mBtMusicFragment);
		mFragmentTransaction.show(mRadioFragment);
		mFragmentTransaction.commit();
        startService();

	}
	
	public void showRadioFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mUsbFragment);
		mFragmentTransaction.hide(mBtMusicFragment);
		mFragmentTransaction.show(mRadioFragment);
		mFragmentTransaction.commit();
	}
	
	public void showUsbFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mRadioFragment);
		mFragmentTransaction.hide(mBtMusicFragment);
		mFragmentTransaction.show(mUsbFragment);
		mFragmentTransaction.commit();
	}
	
	public void showBtMusicFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mRadioFragment);
		mFragmentTransaction.hide(mUsbFragment);
		mFragmentTransaction.show(mBtMusicFragment);
		mFragmentTransaction.commit();
	}
	
	
	private void startService(){
		Intent intent = new Intent(PHONE_APP_SERVICE).setPackage("com.yf.radiophoneapp");
		startService(intent);
		bindService(intent,this,Context.BIND_AUTO_CREATE);
		Log.i("TAG","====startService in MainAct=====curaction: " + intent.getAction());
	}
    
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		if (mControl==null)
		mControl = PhoneAppControl.Stub.asInterface(service);
		try {
			Log.i(TAG, "onServiceConnected: getBluetoothEnabled==="+mControl.getBluetoothEnabled()+"  getRemoteDevice==="+mControl.getRemoteDevice());
			if(!mControl.getBluetoothEnabled() || mControl.getRemoteDevice() == null){
				showFirstBtConnDialog();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onServiceDisconnected(ComponentName name) {
		mControl = null;
	}
	
	public void enterBluetoothList() throws RemoteException {
		Intent intent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

//		selectButton(R.id.bt_phone);
		initState();

	}



	public void showFirstBtConnDialog(){
		View view = DialogUtils.showFirstConnectDialog(this, "SimpleRadio" , R.string.first_connect_bt, true);

		view.findViewById(R.id.pair_yes).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("TAG","====servconn in MainAct=====pairyes: ");
				try {
					enterBluetoothList();

				} catch (RemoteException e) {
					e.printStackTrace();
				}
				DialogUtils.hideWindow();
			}
		});

		view.findViewById(R.id.pair_no).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideWindow();
				finish();
				Log.i("TAG","====servconn in MainAct=====pairno: ");
			}
		});

	}


	private static final int bt_fm=2;
	private static final int bt_am = 3;
	private static final int bt_music=4;
	private static final int bt_bluetoothmusic=5;
	private static final int bt_phone=1;

	public void selectButton(int id){
		for (int btnId : mButtonIds) {
			findViewById(btnId).setSelected(id == btnId);
		}
	}
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_raido:
			try {
				//手动切换到电台页面发送命令
				mControl.RadioDefaultFreqSet();
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			break;
		case R.id.bt_music:
			try {
				//手动切换到USB界面发送命令
				mControl.UsbViewShow();
			} catch (RemoteException e) {
				e.printStackTrace();
			}

//			DialogUtils.showOnlyTextDialog(this, R.string.first_conn_music_dialog);
//			DialogUtils.showLoadingDialog(this, getResources().getString(R.string.onloading), 1000*10);
			break;
		case R.id.bt_bluetoothmusic:
			try {
				//手动切换到蓝牙音乐界面命令
				mControl.BtMusicViewShow();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.bt_phone:
			gotoPhone();
			break;
		default:
			break;
		}
	}

	public void gotoPhone(){
//		Intent intent = new Intent(Intent.ACTION_DIAL);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//		selectButton(R.id.bt_phone);
		Intent intent = new Intent(this, PhoneActivity.class);
		startActivity(intent);
	}

	public void setViewState(int viewState) {
		switch (viewState){
			case bt_fm:
			case bt_am:
				showRadioFragment();
				selectButton(R.id.bt_raido);
				try {
					mControl.getCurrPageShow();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case bt_music:
				showUsbFragment();
				selectButton(R.id.bt_music);
				try {
					//获取当前界面显示数据发送命令
					mControl.getCurrPageShow();
					//读取USB－ID3信息
					mControl.getUsbId3Info();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			case bt_bluetoothmusic:
				showBtMusicFragment();
				selectButton(R.id.bt_bluetoothmusic);
				try {
					mControl.getCurrPageShow();
					mControl.getBtMusicId3Info();
				} catch (RemoteException e) {
					e.printStackTrace();
				}

//			DialogUtils.showLoadingDialog(this, getResources().getString(R.string.btconnecting), 1000*10);
				break;
			case bt_phone:
				gotoPhone();
				break;
		}
	}

	private void getDisplayInfomation() {
		Point point = new Point();
		getWindowManager().getDefaultDisplay().getSize(point);
		Log.i(TAG,"the screen size is "+point.toString());
		getWindowManager().getDefaultDisplay().getRealSize(point);
		Log.i(TAG,"the screen real size is "+point.toString());
	}


}
