package com.yf.phoneapp.aidl;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.data.BtDevice;

interface PhoneAppControl{

	void ConnectDevice(in String device);

	void setListener(PhoneAppCallback listener);
	
	void removeListener(PhoneAppCallback listener);
	
	void RadioNext();
	
	void RadioPre();
	
	void RadioSetBand();
	
	void RadioSearch();

	void RadioAddOne();

	void RadioSubOne();

	void RadioDefaultFreqSet();

	void getFMFavorite();

	void getAMFavorite();
	
	void UsbNext();
	
	void UsbPre();

    void UsbViewShow();
	void UsbPlayAndPause();

	void UsbPlayOrder();

	void UsbPlaySingle();

	void UsbPlayRandom();

	void UsbPlayList();

	void BtMusicViewShow();
	
	void BtMusicPre();
	
	void BtMusicNext();
	
	void BtMusicPlayAndPause();

	void getBtMusicStatus();

	void getBtMusicId3Info();

	void getCurrPageShow();

	void getUsbStatus();

	void getUsbId3Info();

	boolean getConnectCarDevice();
	
	void createBluetoothBond(String address);
	
	void disconnectBluetoothBond(String address);
	
	boolean getBluetoothEnabled();
	
	void startDiscovery();
	
	List<BtDevice> getBtDeviceList();
	
	void removeBond(String address);
	
	BluetoothDevice getRemoteDevice();
	
}