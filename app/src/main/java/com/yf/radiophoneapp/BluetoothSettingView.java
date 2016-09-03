package com.yf.radiophoneapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
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
import com.yf.phoneapp.data.BtDevice;
import com.yf.phoneapp.ui.DialogUtils;
import com.yf.protocol.BtConnectStatusChange;

public class BluetoothSettingView extends Activity implements OnClickListener,OnItemClickListener,
BtConnectStatusChange,ServiceConnection{
	private static final String PHONE_APP_SERVICE = "com.yf.phoneapp.service";
	private List<BluetoothDevice> allDevices = new ArrayList<BluetoothDevice>();
	BluetoothDevicesAdapter adapter;
	ListView devList;
	private PhoneAppControl mControl = null;
	Button btBack,btSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetoothconnect);
		findView();
		startService();
		registerBrocast();
		Log.d("TAG", "bt-settingview oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(this);
		unregisterReceiver(BroadcastReceiver);
	}
	
	private void findView(){
		btBack = (Button) findViewById(R.id.back);
		btBack.setOnClickListener(this);
		btSearch = (Button) findViewById(R.id.search);
		btSearch.setOnClickListener(this);
		adapter = new BluetoothDevicesAdapter(this);
		devList = (ListView) findViewById(R.id.device_list);
		devList.setOnItemClickListener(this);
		devList.setAdapter(adapter);
		adapter.setBtStatusListener(this);
		adapter.notifyDataSetChanged();
	}
	
	private void registerBrocast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);  
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

		registerReceiver(BroadcastReceiver, filter);
	}
	
	public void startDiscovery() throws RemoteException {
		 if (BluetoothAdapter.getDefaultAdapter().isDiscovering())  
			 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
		 allDevices.clear();
		 
		 Set<BluetoothDevice> set = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	     Iterator<BluetoothDevice> i = set.iterator();
	     while (i.hasNext()) {

			BluetoothDevice device = i.next();

			allDevices.add(device);
			Log.d("TAG", "startDiscoverying...  " + device.getName());
		}
	    BluetoothAdapter.getDefaultAdapter().startDiscovery();   
	}
	
	private void startService(){
		Intent intent = new Intent(PHONE_APP_SERVICE);
		startService(intent);
		bindService(intent,this,Context.BIND_AUTO_CREATE);
	}
	
	private Handler mHandler = new Handler();
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		mControl = PhoneAppControl.Stub.asInterface(service);
		try {
			if(!mControl.getBluetoothEnabled()){
				Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(enabler);
			}
			mControl.setListener(CallbackListener);
			adapter.setRemoteDevice(mControl.getRemoteDevice());
			adapter.notifyDataSetChanged();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		mControl = null;
	}
	
	 private PhoneAppCallback.Stub CallbackListener = new PhoneAppCallback.Stub() {
			
			@Override
			public void onFreqChanage(final int freq) throws RemoteException {
				
			}

		 @Override
		 public void onFavorFreqChange(byte[] favors) throws RemoteException {

		 }



		 @Override
			public void onPhoneStatusChange(String num) throws RemoteException {
				
			}

			@Override
			public void onDeviceBondStatusChange(String address, boolean isConnect) throws RemoteException {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						try {
							DialogUtils.hideLoadingDialog();
							adapter.setRemoteDevice(mControl.getRemoteDevice());
							adapter.notifyDataSetChanged();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onSourceStatusChange(int type) throws RemoteException {
			}

			@Override
			public void onPlayingStatusChange(int type) throws RemoteException {
				
			}

		 @Override
		 public void onPlayOrderChange(int playOrder) throws RemoteException {

		 }

		 @Override
		 public void onCurrIndexChange(int currIndex) throws RemoteException {

		 }

		 @Override
		 public void onAllIndexChange(int allIndex) throws RemoteException {

		 }

		 @Override
		 public void onCurrPlayTimeChange(long currTime) throws RemoteException {

		 }

		 @Override
		 public void onAllPlayTimeChange(long allTime) throws RemoteException {

		 }

		 @Override
		 public void onCurrUsbMusicName(String usbMusicName) throws RemoteException {

		 }

		 @Override
		 public void onCurrUsbMusicAuthor(String usbMusicAuthor) throws RemoteException {

		 }

		 @Override
		 public void onCurrUsbMusicAlbum(String usbMusicAlbum) throws RemoteException {

		 }

	 };

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		adapter.updateSelectIndex(position);
	}

	Context conntext = this;
	@Override
	public void ConnectBluetooth(int position) {
		DialogUtils.showLoadingDialog(this,getResources().getString(R.string.connecting), 50000);
		final BluetoothDevice device = (BluetoothDevice) adapter.getDeviceList().get(position);
		try {
			mControl.createBluetoothBond(device.getAddress());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disConnectBluetooth(int position) {
		DialogUtils.showLoadingDialog(this,getResources().getString(R.string.disconnecting), 50000);
		final BluetoothDevice device = (BluetoothDevice) adapter.getDeviceList().get(position);
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				try {
					mControl.disconnectBluetoothBond(device.getAddress());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}, 1500);
	}

	@Override
	public void delectBluetooth(final int position) {
		final BluetoothDevice device = adapter.getDeviceList().get(position);
		View view = DialogUtils.showConnectFailedDialog(conntext,device.getName(), 500000);
		view.findViewById(R.id.pair_yes).setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	adapter.getDeviceList().remove(position);
	        		try {
	        			mControl.removeBond(device.getAddress());
	        		} catch (RemoteException e) {
	        			e.printStackTrace();
	        		}
	        		adapter.notifyDataSetChanged();
	            	DialogUtils.hideWindow();
	            }
	        });
		
		view.findViewById(R.id.pair_no).setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	            	DialogUtils.hideWindow();
	            }
	        });
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.search:
			try {
				 if (BluetoothAdapter.getDefaultAdapter().isDiscovering())  
					 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
				 else
					 startDiscovery();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	BroadcastReceiver BroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			BluetoothDevice device = null;
			if (BluetoothDevice.ACTION_FOUND.equals(action)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
				if (allDevices.indexOf(device.getAddress()) == -1){
					allDevices.add(device);
					adapter.setDeviceList(allDevices);
					adapter.notifyDataSetChanged();
					devList.setSelection(allDevices.size() - 4);
					Log.d("TAG", "bt devices searching......");
				}
				btSearch.setText(getResources().getString(R.string.search_stop));
			}else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
				btSearch.setText(getResources().getString(R.string.search_device));
			}else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				switch (device.getBondState()) {
				case BluetoothDevice.BOND_BONDING:
					Log.d("TAG", "BOND_BONDING......");
					break;
				case BluetoothDevice.BOND_BONDED:
					Log.d("TAG", "BOND_BONDED" + device.getName());
					try {
						mControl.ConnectDevice(device.getAddress());
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				case BluetoothDevice.BOND_NONE:
					Log.d("TAG", "BOND_NONE");
				default:
					break;
				}

			}else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {

				String stateExtra = BluetoothAdapter.EXTRA_STATE;

				int state = intent.getIntExtra(stateExtra, -1);
				switch (state) {
				case BluetoothAdapter.STATE_TURNING_ON:
					break;
				case BluetoothAdapter.STATE_ON:
					break;
				case BluetoothAdapter.STATE_TURNING_OFF:
					break;
				case BluetoothAdapter.STATE_OFF:
					break;
				}
			}
		}
	};
	
}
