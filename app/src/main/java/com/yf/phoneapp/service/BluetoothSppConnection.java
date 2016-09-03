package com.yf.phoneapp.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

import com.yf.phoneapp.btcontrol.ClsUtils;
import com.yf.protocol.IDataRecvListener;


import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfile.ServiceListener;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

public class BluetoothSppConnection {
	private static final String TAG = "BluetoothSppConnection";

	public static final String NAME_SECURE = "BluetoothSppConnection";
	
    public static final int STATE_NONE = 0;       // we're doing nothing
    
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    
    public static final int STATE_CONNECT_LOST = 4;//now connected is lost
	
	public static final String SPP = "00001101-0000-1000-8000-00805F9B34FB";
	public static final UUID uuid = UUID.fromString(SPP);
	
//	private AcceptThread acceptThread;
	
	private DataRecvThread dataThread;
	
	private ConnectThread connectThread;
	
	private BluetoothAdapter adapter;
	
	private IDataRecvListener dataListener;
	
	public BluetoothDevice mRemoteDevice;
	
	private Context mContext;
	
	public static int status = STATE_NONE;
	private byte[] buffer;
	private int readLen;

	public BluetoothSppConnection(IDataRecvListener dataListener){
		this.dataListener = dataListener;
		adapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	private synchronized void setState(int state){
		
		this.status = state;
		if(this.status == STATE_NONE || this.status == STATE_CONNECT_LOST){
			adapter.closeProfileProxy(BluetoothProfile.A2DP, a2dp);
//			try {
//				if(a2dp != null)
//					ClsUtils.disconnectA2dp(mRemoteDevice, a2dp);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			mRemoteDevice = null;
		}
	}
	
	public synchronized BluetoothDevice getRemoteDevice(){
		return mRemoteDevice;
	}
	
	public synchronized void setRemoteDevice(BluetoothDevice device){
		mRemoteDevice = device;
	}
	
	
	public synchronized int getState(){
		
		return status;
	}
	
	public synchronized BluetoothAdapter getAdapter(){
		return adapter;
	}
	
	public synchronized String getConnectedDevice(){
		if(adapter != null)
			return adapter.getName();
		return null;
	}
	
	public synchronized boolean getBlueEnable(){
		return adapter.isEnabled();
	}
	
	public void writeInSpp(byte[] buffer, int len){
		
		if (dataThread != null)
			dataThread.writeToServ(buffer);
	}
	
	private void connectA2DP(BluetoothAdapter Adapter,Context mContext) {
		if(Adapter.getProfileConnectionState(BluetoothProfile.A2DP)!=BluetoothProfile.STATE_CONNECTED){
			Adapter.getProfileProxy(mContext, ProfileService, BluetoothProfile.A2DP);
		}
	}
	
	BluetoothA2dp a2dp;
	BluetoothProfile.ServiceListener ProfileService = new BluetoothProfile.ServiceListener() {
		@Override
		public void onServiceConnected(int profile, BluetoothProfile proxy) {
				a2dp = (BluetoothA2dp) proxy;
				Class<? extends BluetoothA2dp> clazz = a2dp.getClass();
				try {
				if (a2dp.getConnectionState(mRemoteDevice) != BluetoothProfile.STATE_CONNECTED) {
					clazz.getMethod("connect", BluetoothDevice.class).invoke(a2dp, mRemoteDevice);
				}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
		}

		@Override
		public void onServiceDisconnected(int profile) {

		}

	};
	
	
	
	public synchronized void connectInSpp(BluetoothDevice device, boolean secure,Context context){
		if (status == STATE_CONNECTED)
			return;
		mContext = context;
		if (connectThread != null){
			connectThread.cancel();
			connectThread = null;
		}
		
		if (dataThread != null){
			dataThread.cancel();
			dataThread = null;
		}
		
		connectThread = new ConnectThread(device, secure);
		connectThread.start();
		
		setState(STATE_CONNECTING);
	}
	
//	public synchronized void startService(boolean secure){
//		
//		if (connectThread != null){
//			connectThread.cancel();
//			connectThread = null;
//		}
//		
//		if (dataThread != null){
//			dataThread.cancel();
//			dataThread = null;
//		}
//		
//		if (acceptThread != null){
//			acceptThread.cancel();
//			acceptThread = null;
//		}
//		
//		setState(STATE_LISTEN);
//		
//		acceptThread = new AcceptThread(false);
//		acceptThread.start();
//		
//	}
	
	private synchronized void connectedSpp(BluetoothSocket socket, final String socketType){
		
		if (dataThread != null){
			dataThread.cancel();
			dataThread = null;
		}
		
//		if (acceptThread != null){
//			acceptThread.cancel();
//			acceptThread = null;
//		}
		
		if (connectThread != null){
			connectThread.cancel();
			connectThread = null;
			
		}
			
		dataThread = new DataRecvThread(socket, socketType);
		
		dataThread.start();
		
		setState(STATE_CONNECTED);
	}
	
	public synchronized void stop(){
		
		if (dataThread != null){
			dataThread.cancel();
			dataThread = null;
		}
		
//		if (acceptThread != null){
//			acceptThread.cancel();
//			acceptThread = null;
//		}
		
		if (connectThread != null){
			connectThread.cancel();
			connectThread = null;
			
		}
		if (dataListener != null)
			dataListener.onDeviceConnectStatusChange(mRemoteDevice,false);
		setState(STATE_NONE);
	}
	
//	class AcceptThread extends Thread{
//		
//		private final static String NAME = "AcceptThread";
//		
//		private BluetoothServerSocket socket;
//		
//		private String socketType;
//		
//		public AcceptThread(boolean secure){
//			
//			BluetoothServerSocket tmp = null;
//			
//			socketType = secure ? "Secure" : "Insecure";
//			
//			try {
//				
//				if (secure)
//					tmp = adapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, uuid);
//				else
//					tmp = adapter.listenUsingInsecureRfcommWithServiceRecord(NAME_SECURE, uuid);
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			socket = tmp;
//		}
//		
//		public void cancel(){
//			
//			try {
//				if (socket != null)
//					socket.close();
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			socket = null;
//		}
//
//		@Override
//		public void run() {
//			super.run();
//			
//			setName(NAME + socketType);
//			
//			try {
//				if (socket != null){
//					BluetoothSocket sct = socket.accept();
//					connected(sct, socketType);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	class DataRecvThread extends Thread{

		private static final String NAME = "DataRecvThread";
		
		private static final int MAX_BUFFER = 1024;
		
		private BluetoothSocket socket;
		
		private InputStream input;
		
		private OutputStream output;
		
		private String secure;
		
		public DataRecvThread(BluetoothSocket socket, String secure){
			
			this.socket = socket;
			
			this.secure = secure;
			
			try {
				
				input = socket.getInputStream();
				
				output = socket.getOutputStream();
				
				Log.d("TAG","====DataRevThread of spp conn===input- " + input + "///output- " + output + "//socket- " + socket);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}

		@Override
		public void run() {
			super.run();
			setName(NAME + secure);
			buffer = new byte[MAX_BUFFER];

//			getData();
			while(true){
				try {
					if(input.available() > 0)
						readLen = input.read(buffer);
					sleep(100);
				} catch (Exception e) {
					e.printStackTrace();

					Log.i(TAG, "run: ********IOException*******");
					setState(STATE_CONNECT_LOST);
					break;
				}
				if (dataListener != null && readLen > 0)
					dataListener.onDataRecv(socket.getRemoteDevice(), buffer, readLen);
			}
		}

//		private void getData() {
//			new Handler().postDelayed(new Runnable() {
//				@Override
//				public void run() {
//
//					try {
//						if(input.available() > 0)
//							readLen = input.read(buffer);
//						getData();
//					} catch (Exception e) {
//						e.printStackTrace();
//
//						Log.i(TAG, "run: ********IOException*******");
//						setState(STATE_CONNECT_LOST);
//
//					}
//					if (dataListener != null && readLen > 0)
//						dataListener.onDataRecv(socket.getRemoteDevice(), buffer, readLen);
//				}
//			},100);
//		}

		public void writeToServ(byte[] buffer){
			
			try {
				output.write(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void cancel(){
			
			try {
				if (socket != null)
					socket.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			socket = null;
		}
	}
	
	class ConnectThread extends Thread {
		
		private static final String NAME = "ConnectThread";
		
		private BluetoothSocket mmsocket;
		
		private String secureType;
		
		public ConnectThread(BluetoothDevice device, boolean secure){
			
			secureType = secure ? "Secure" : "Insecure";
			
			BluetoothSocket tmp = null;
			mRemoteDevice = device;
			try {
				
				if (secure)
					tmp = device.createRfcommSocketToServiceRecord(uuid);
				else
					tmp = device.createInsecureRfcommSocketToServiceRecord(uuid);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			mmsocket = tmp;
			
			Log.d("TAG","======mmsocket in spp ConnectThread======" + mmsocket);
			
		}

		@Override
		public void run() {
			super.run();
			
			setName(NAME + secureType);
			
			adapter.cancelDiscovery();
			try {
				Log.d("TAG","====== spp Connecting======" + secureType);
				mmsocket.connect();
				Log.i("TAG","====== spp Connected======" + secureType);
				
				//connectA2DP(adapter,mContext);
			} catch (IOException e) {
				e.printStackTrace();
				try {
					if(mmsocket != null)
					mmsocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				mmsocket = null;
				setState(STATE_CONNECT_LOST);
				return;
			}
			
			synchronized (BluetoothSppConnection.this) {
				connectThread = null;
			}
			if (dataListener != null)
				dataListener.onDeviceConnectStatusChange(mRemoteDevice,true);
			connectedSpp(mmsocket, secureType);
		}
		
		public void cancel(){
			
			try {
				if (mmsocket != null)
					mmsocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mmsocket = null;
		}
		
	}
}
