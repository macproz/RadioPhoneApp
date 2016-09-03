package com.yf.phoneapp.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;
import com.yf.phoneapp.btcontrol.ClsUtils;
import com.yf.phoneapp.data.BtDevice;
import com.yf.phoneapp.entity.UsbMusicData;
import com.yf.protocol.AppProtocol;
import com.yf.protocol.IDataRecvListener;
import com.yf.protocol.IDispatch;

public class PhoneAppService extends Service {

	private RemoteCallbackList<PhoneAppCallback> Callback = new RemoteCallbackList<PhoneAppCallback>();
	private BluetoothSppConnection connnection;
	private AppProtocol protocol;
	private BluetoothAdapter mAdapter;
	private List<BtDevice> Devices = new ArrayList<BtDevice>();
	
	private static int CUR_PALY_STATUS = 0x00;
	private static int ISPLAYING = 0x01;
	private static int ISPAUSE = 0x02;
	@Override
	public IBinder onBind(Intent intent) {
		return control;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		connnection = new BluetoothSppConnection(dataRecvListener);
		protocol = new AppProtocol(dispather);
		mAdapter = connnection.getAdapter();
		Log.d("TAG","====oncreate in PhoneAppService=before registbroad====: " );
		registerBrocast();
	}

	private void connectBluetooth(String address) {
		BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
		connnection.connectInSpp(device, true,getApplicationContext());
	}
	
	private boolean getConnectedCarDevice(){
		return (connnection.getConnectedDevice() != null 
				&& connnection.getConnectedDevice().equalsIgnoreCase("SimpleRadio")
				&& connnection.getBlueEnable());
		 
	}
	
	private void registerBrocast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(BroadcastReceiver, filter);
	}
	
	BroadcastReceiver BroadcastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			BluetoothDevice device = null;
			Log.d("TAG","====onReceive in PhoneAppService=====curraction: " + action);
			if(action.endsWith(BluetoothDevice.ACTION_ACL_CONNECTED)){
				device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); 
				connectBluetooth(device.getAddress());
			}else if(action.endsWith(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
				connnection.setRemoteDevice(null);
			}
		}
		
	};
	
//	private void write(){
//		//connnection.startService(true);
//		byte[] buf = new byte[]{1,1,1,3,9,2,4,4,8,3,9,4,9};
//		String i = protocol.unpackArray(buf, buf.length);
//		Log.d("TA","=======I=======" + i);
//	}
	
	private Handler mHandler = new Handler();
	
	private PhoneAppControl.Stub control = new PhoneAppControl.Stub() {
		
		@Override
		public void setListener(PhoneAppCallback listener) throws RemoteException {
			Callback.register(listener);


		}



		@Override
		public void removeListener(PhoneAppCallback listener) throws RemoteException {
			Callback.unregister(listener);
		}
		
		@Override
		public void RadioPre() throws RemoteException {
//			byte[] command = new byte[]{AppProtocol.SET_RADIO_FREQ_PRE};
//			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 3);


			byte[] command = new byte[]{AppProtocol.SET_RADIO_FREQ_PRE,AppProtocol.SET_RADIO_FREQ_PRE_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 4);

			connnection.writeInSpp(pkg, pkg.length);
		}
		
		@Override
		public void RadioNext() throws RemoteException {
//			byte[] command = new byte[]{AppProtocol.SET_RADIO_FREQ_NEXT};
//			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 3);

			byte[] command = new byte[]{AppProtocol.SET_RADIO_FREQ_NEXT,AppProtocol.SET_RADIO_FREQ_NEXT_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 4);

			connnection.writeInSpp(pkg, pkg.length);
		}
		
		@Override
		public void RadioSetBand() throws RemoteException {
			//设置FM/AM频道切换
			byte[] command = new byte[]{AppProtocol.SET_RADIO_BAND, AppProtocol.SET_RADIO_BAND_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 4);

			connnection.writeInSpp(pkg, pkg.length);
		}
		
		@Override
		public void RadioSearch() throws RemoteException {
			//一键收台收藏
			byte[] command = new byte[]{AppProtocol.SET_ONEKEY_SEARCH, AppProtocol.SET_ONEKEY_SEARCH_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 4);

			connnection.writeInSpp(pkg, pkg.length);
			//点击一键搜台后，延时30S再发 读取收藏列表命令
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					try {
						getFMFavorite();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			},1000*70);
		}


		@Override
		public void RadioAddOne() throws RemoteException {
			//设置频率加0.1-fm, 9-am
			byte[] command = new byte[]{AppProtocol.SET_ADDONE_FREQ, AppProtocol.SET_ADDONE_FREQ_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 4);

			connnection.writeInSpp(pkg, pkg.length);

		}

		@Override
		public void RadioSubOne() throws RemoteException {
			//设置频率减0.1-fm, 9-am
			byte[] command = new byte[]{AppProtocol.SET_SUBONE_FREQ, AppProtocol.SET_SUBONE_FREQ_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 4);

			connnection.writeInSpp(pkg, pkg.length);
		}


		@Override
		public void RadioDefaultFreqSet() throws RemoteException {
			//设置默认电台频率
			byte[] command = new byte[]{AppProtocol.SET_RADIO_FREQ_VALUE, AppProtocol.FREQ_HIGH, AppProtocol.FREQ_LOW,AppProtocol.SET_RADIO_DEFAULT_FREQ_CHECK};
			byte[] pkg = protocol.packDefaultFreqCmd(AppProtocol.REQUEST_RADIO_CONTROL,command, 6);
			connnection.writeInSpp(pkg, pkg.length);

		}

		@Override
		public void getFMFavorite() throws RemoteException {
			//读取FM收藏列表
			byte[] command = new byte[]{AppProtocol.SET_FM_FAVOR, AppProtocol.SET_FM_FAVOR_CHECK};
			byte[] pkg = protocol.packFavorRadio(AppProtocol.SET_FM_FAVOR,command, 6);
			connnection.writeInSpp(pkg, pkg.length);
			Log.i("TAG","get fm favorite = " + pkg[0] + "#" + pkg[1] +  "#" + pkg[2] +"#" + pkg[3] + "#" + pkg[4]);
		}

		@Override
		public void getAMFavorite() throws RemoteException {
			//读取AM收藏列表
			byte[] command = new byte[]{AppProtocol.SET_AM_FAVOR, AppProtocol.SET_AM_FAVOR_CHECK};
			byte[] pkg = protocol.packFavorRadio(AppProtocol.SET_AM_FAVOR,command, 6);
			connnection.writeInSpp(pkg, pkg.length);
		}


		@Override
		public void ConnectDevice(String device) throws RemoteException {
			connectBluetooth(device);
		}

		@Override
		public boolean getConnectCarDevice() throws RemoteException {
			return getConnectedCarDevice();
		}

		@Override
		public void createBluetoothBond(String address) throws RemoteException {
			BluetoothDevice btDev = mAdapter.getRemoteDevice(address);
			try {
				if (btDev.getBondState() == BluetoothDevice.BOND_NONE) {
					ClsUtils.createBond(btDev);
				} else {
					connectBluetooth(address);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void removeBond(String address) throws RemoteException {
			BluetoothDevice btDev = mAdapter.getRemoteDevice(address);
			try {
				ClsUtils.removeBond(btDev);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean getBluetoothEnabled() throws RemoteException {
			return connnection.getBlueEnable();
		}

		@Override
		public void startDiscovery() throws RemoteException {
		}

		@Override
		public List<BtDevice> getBtDeviceList() throws RemoteException {
			return Devices;
		}


		@Override
		public BluetoothDevice getRemoteDevice() throws RemoteException {
			return connnection.getRemoteDevice();
		}

		@Override
		public void disconnectBluetoothBond(String address)
				throws RemoteException {
			connnection.stop();
		}

		@Override
		public void UsbNext() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PLAY_NEXT, AppProtocol.SET_USB_PLAY_NEXT_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
			Log.i("TAG","=======usbnext in PhoneAppservice===write=pkg[0-2]: " + pkg[0] + "-" + pkg[1]  + "-" + pkg[2]);
		}

		@Override
		public void UsbPre() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PLAY_PRE, AppProtocol.SET_USB_PLAY_PRE_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void UsbViewShow() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PALY, AppProtocol.SET_USB_PLAY_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void UsbPlayAndPause() throws RemoteException {
			byte[] command;
			byte[] pkg;

			if(CUR_PALY_STATUS == ISPLAYING){
				command = new byte[]{AppProtocol.SET_USB_PAUSE, AppProtocol.SET_USB_PAUSE_CHECK};
				pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			}else{
				command = new byte[]{AppProtocol.SET_USB_PALY, AppProtocol.SET_USB_PLAY_CHECK};
				pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			}
			connnection.writeInSpp(pkg, pkg.length);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						getUsbStatus();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}, 400);
		}

		@Override
		public void UsbPlayOrder() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PLAY_ORDER, AppProtocol.SET_USB_PLAY_ORDER_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void UsbPlaySingle() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PLAY_SINGLE, AppProtocol.SET_USB_PLAY_SINGLE_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void UsbPlayRandom() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PLAY_RANDOM, AppProtocol.SET_USB_PLAY_RANDOM_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void UsbPlayList() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PLAY_LIST, AppProtocol.SET_USB_PLAY_LIST_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_USB_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void BtMusicViewShow() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_PALY, AppProtocol.SET_BTMUSIC_PLAY_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_BTMUSIC_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}


		//手机读取当前显示数据(99 03 02 01 06)
		@Override
		public void getCurrPageShow() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_CURR_PAGE_SHOW,AppProtocol.SET_CURR_PAGE_SHOW_CHECK};
			byte[] pkg = protocol.packFirstShowCmd(AppProtocol.SET_CURR_PAGE_SHOW,command, 3);
			connnection.writeInSpp(pkg, pkg.length);
//			mHandler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						getUsbId3Info();
//					} catch (RemoteException e) {
//						e.printStackTrace();
//					}
//				}
//			},300);

		}

		//手机读取当前USB播放状态(为了获取播放状态和顺序)
		@Override
		public void getUsbStatus() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_STATUS,AppProtocol.SET_USB_STATUS_CHECK};
			byte[] pkg = protocol.packUsbPlayStatus(AppProtocol.SET_USB_STATUS,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}




		//手机读取USB ID3 信息
		@Override
		public void getUsbId3Info() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_ID3_INFO,AppProtocol.SET_USB_ID3_CHECK};
			byte[] pkg = protocol.packUsbPlayStatus(AppProtocol.SET_USB_ID3_INFO,command, 4);
			connnection.writeInSpp(pkg, pkg.length);

			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					try {
						getUsbStatus();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			},300);
		}


		@Override
		public void BtMusicPre() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_BT_MUSIC_PLAY_PRE,AppProtocol.SET_BTMUSIC_PRE_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_BTMUSIC_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void BtMusicNext() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_BT_MUSIC_PLAY_NEXT, AppProtocol.SET_BTMUSIC_NEXT_CHECK};
			byte[] pkg = protocol.packCmd(AppProtocol.REQUEST_BTMUSIC_CONTROL,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}

		@Override
		public void BtMusicPlayAndPause() throws RemoteException {
			byte[] command;
			byte[] pkg;
			
			if(CUR_PALY_STATUS == ISPLAYING){
				command = new byte[]{AppProtocol.SET_BT_MUSIC_PAUSE, AppProtocol.SET_BTMUSIC_PAUSE_CHECK};
				pkg = protocol.packCmd(AppProtocol.REQUEST_BTMUSIC_CONTROL,command, 4);
			}else{
				command = new byte[]{AppProtocol.SET_BT_MUSIC_PLAY, AppProtocol.SET_BTMUSIC_PLAY_CHECK};
				pkg = protocol.packCmd(AppProtocol.REQUEST_BTMUSIC_CONTROL,command, 4);
			}
			connnection.writeInSpp(pkg, pkg.length);
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					try {
						getBtMusicStatus();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			},1000);
		}

		//手机读取当前蓝牙音乐播放状态(为了获取播放状态和顺序)
		@Override
		public void getBtMusicStatus() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_STATUS,AppProtocol.SET_BT_STATUS_CHECK};
			byte[] pkg = protocol.packBtPlayStatus(AppProtocol.SET_USB_STATUS,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
		}


		//读取蓝牙ID3状态(99 04 02 02 05 0xd)
		@Override
		public void getBtMusicId3Info() throws RemoteException {
			byte[] command = new byte[]{AppProtocol.SET_USB_ID3_INFO,AppProtocol.SET_BT_ID3_CHECK};
			byte[] pkg = protocol.packBtPlayStatus(AppProtocol.SET_USB_ID3_INFO,command, 4);
			connnection.writeInSpp(pkg, pkg.length);
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					try {
						getBtMusicStatus();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			},300);
		}


	};
	
	private IDataRecvListener dataRecvListener =  new IDataRecvListener() {
		
		@Override
		public void onDataRecv(BluetoothDevice device, byte[] data, int len) {
			protocol.identifyPackage(device, data, len);
		}

		@Override
		public void onDeviceConnectStatusChange(BluetoothDevice device, boolean isConnect) {
			notifyBondStatusChange(null,isConnect);
		}
		
	};
	
	private IDispatch dispather = new IDispatch() {
		
		@Override
		public void onRadioFreqChange(int freq) {
			notifyFreqSignal(freq);
		}

		@Override
		public void onCurrPageFavorFreqChange(byte[] favorFreq) {
			notifyFavorFreqChange(favorFreq);
		}


		@Override
		public void onPhoneStatusChange(String num) {
			notifyPhoneStatusChange(num);
		}

		@Override
		public void onCurSourceChange(int type) {
			notifySourceStatusChange(type);
		}

		@Override
		public void onCurPlayStatusChange(int status) {
			CUR_PALY_STATUS = status;
//			Log.i("TAG","playstatus in  appserver= " + status);
			notifyPlayingStatusChange(status);
		}

		@Override
		public void onCurrPlayOrderChange(int playOrder) {
			notifyPlayingOrderChange(playOrder);
		}

		@Override
		public void onCurrPlayIndexChange(int currIndex) {
			notifyCurrPlayIndexChange(currIndex);
		}

		@Override
		public void onPlayAllIndexChange(int allIndex) {
			notifyAllIndexChange(allIndex);
		}

		@Override
		public void onCurrPlayTimeChange(long currTime) {
			notifyUsbCurrTimeChange(currTime);
		}

		@Override
		public void onAllPlayTimeChange(long allTime) {
			notifyUsbAllTimeChange(allTime);
		}

		@Override
		public void onCurrUsbMusicName(String usbMusicName) {
			notifyUsbMusicNameChange(usbMusicName);
		}

		@Override
		public void onCurrUsbMusicAuthor(String usbMusicAuthor) {
			notifyCurrUsbAuthorChange(usbMusicAuthor);
		}

		@Override
		public void onCurrUsbMusicAlbum(String usbMusicAlbum) {
			notifyUsbMusicAlbumChange(usbMusicAlbum);

		}


	};

	private void notifyFavorFreqChange(byte[] favorFreq) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onFavorFreqChange(favorFreq);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}

	private void notifyUsbMusicAlbumChange(String usbMusicAlbum) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onCurrUsbMusicAlbum(usbMusicAlbum);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}

	private void notifyCurrUsbAuthorChange(String usbMusicAuthor) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onCurrUsbMusicAuthor(usbMusicAuthor);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}

	private void notifyUsbMusicNameChange(String usbMusicName) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onCurrUsbMusicName(usbMusicName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}


	private void notifyPlayingOrderChange(int playOrder) {
		int i = Callback.beginBroadcast();
		while(i > 0){
			i--;
			try {
				Callback.getBroadcastItem(i).onPlayOrderChange(playOrder);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}

	private void notifyUsbAllTimeChange(long allTime) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onAllPlayTimeChange(allTime);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}

	private void notifyUsbCurrTimeChange(long currTime) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onCurrPlayTimeChange(currTime);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();
	}

	private void notifyAllIndexChange(int allIndex) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onAllIndexChange(allIndex);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();

	}

	private void notifyCurrPlayIndexChange(int currIndex) {
		int i = Callback.beginBroadcast();
		while (i > 0) {
			i--;
			try {
				Callback.getBroadcastItem(i).onCurrIndexChange(currIndex);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		Callback.finishBroadcast();

	}



	private synchronized void notifyFreqSignal(int freq){
		
		int i = Callback.beginBroadcast();
	    while (i > 0) {
	        i--;
	        try {
	        	Callback.getBroadcastItem(i).onFreqChanage(freq);
	        } catch (RemoteException e) {
	        	e.printStackTrace();
	        }
	    }
	    Callback.finishBroadcast();
	}
	
	private synchronized void notifyPhoneStatusChange(String num){
		int i = Callback.beginBroadcast();
		
		while(i > 0){
			i--;
			try {
				Callback.getBroadcastItem(i).onPhoneStatusChange(num);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		Callback.finishBroadcast();
	}
	
	private synchronized void notifyBondStatusChange(String address,boolean isConnect){
		int i = Callback.beginBroadcast();
		
		while(i > 0){
			i--;
			try {
				Callback.getBroadcastItem(i).onDeviceBondStatusChange(address,isConnect);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		Callback.finishBroadcast();
	}
	
	private synchronized void notifySourceStatusChange(int type){
		int i = Callback.beginBroadcast();
		
		while(i > 0){
			i--;
			try {
				Callback.getBroadcastItem(i).onSourceStatusChange(type);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		Callback.finishBroadcast();
	}
	
	private synchronized void notifyPlayingStatusChange(int type){
		int i = Callback.beginBroadcast();
		
		while(i > 0){
			i--;
			try {
				Callback.getBroadcastItem(i).onPlayingStatusChange(type);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		Callback.finishBroadcast();
	}
	
}
