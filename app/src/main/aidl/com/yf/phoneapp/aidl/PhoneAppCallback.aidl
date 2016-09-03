package com.yf.phoneapp.aidl;
interface PhoneAppCallback{
	void onFreqChanage(int freq);

	void onFavorFreqChange(out byte[] favors);
	
	void onPhoneStatusChange(String num);
	
	void onDeviceBondStatusChange(String address,boolean isConnect);
	
	void onSourceStatusChange(int type);
	
	void onPlayingStatusChange(int playStatus);
	void onPlayOrderChange(int playOrder);

    void onCurrIndexChange(int currIndex);
    void onAllIndexChange(int allIndex);

    void onCurrPlayTimeChange(long currTime);
    void onAllPlayTimeChange(long allTime);

    void onCurrUsbMusicName(String usbMusicName);
    void onCurrUsbMusicAuthor(String usbMusicAuthor);
    void onCurrUsbMusicAlbum(String usbMusicAlbum);


}