package com.yf.protocol;

import android.bluetooth.BluetoothDevice;

import com.yf.phoneapp.entity.UsbMusicData;

public interface IDispatch {

	void onRadioFreqChange(int freq);

	void onCurrPageFavorFreqChange(byte[] favorFreq);
	
	void onPhoneStatusChange(String num);
	
	void onCurSourceChange(int type);
	
	void onCurPlayStatusChange(int playStatus);


	void onCurrPlayOrderChange(int playOrder);

	void onCurrPlayIndexChange(int currIndex);
	void onPlayAllIndexChange(int allIndex);

	void onCurrPlayTimeChange(long currTime);
	void onAllPlayTimeChange(long allTime);

	void onCurrUsbMusicName(String usbMusicName);
	void onCurrUsbMusicAuthor(String usbMusicAuthor);
	void onCurrUsbMusicAlbum(String usbMusicAlbum);








}
