package com.yf.radiophoneapp;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;
import com.yf.phoneapp.ui.AllVolumeView;
import com.yf.phoneapp.ui.HideViewAnimListener;
import com.yf.phoneapp.ui.MusicSeekBar;
import com.yf.phoneapp.util.UrrSourceType;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class UsbFragment extends Fragment implements OnClickListener,SeekBar.OnSeekBarChangeListener {
	private static final String PHONE_APP_SERVICE = "com.yf.phoneapp.service";
	private PhoneAppControl mControl = null;
	Button btPre,btNext,btPlay, btUsbSetting,btVol;
	TextView tvMusicIndex, tvCurrTime, tvAllTime,showVolume;
	TextView tvUsbTitle,tvUsbAlbum,tvUsbAuthor;

	private View ctlFmVolume, fmVolume,muteFm;

	public final static  int WHAT_HIDE_VOLUME_BAR = 0x100;
	private static int PLAYING = 0x01;

	private int currVolume = 0;
	private long HIDE_VOLUME_BAR_TIME = 5000;

	private Timer timerVolume;
	private long TIMER_DELAY = 50;
	private long TIMER_PERIOD = 400;
	private long ANIM_TIME = 300;
	private final static long diffTime = 57600000;

	private static int currPos;
	private static int allPos;
	private static String mCurrTime;
	private static String mAllTime;

	MusicSeekBar musicSeekBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup mRootView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.usb_play_layout, container,false);
		findView(mRootView);
		startService();
		return mRootView;
	}


	
/*	@Override
	public void onPause() {
		super.onPause();
		getActivity().unbindService(connection);
	}*/
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeMessages(WHAT_HIDE_VOLUME_BAR);
		mHandler.removeCallbacksAndMessages(null);
		getActivity().unbindService(connection);
	}

	private void findView(ViewGroup mRootView) {

		musicSeekBar = (MusicSeekBar) mRootView.findViewById(R.id.id_seekbar_music);
		musicSeekBar.setOnSeekBarChangeListener(this);
		btPre = (Button) mRootView.findViewById(R.id.usb_pre);
		btPre.setOnClickListener(this);
		btNext = (Button) mRootView.findViewById(R.id.usb_next);
		btNext.setOnClickListener(this);
		btPlay = (Button) mRootView.findViewById(R.id.usb_play_pause);
		btPlay.setOnClickListener(this);
		btUsbSetting = (Button) mRootView.findViewById(R.id.usb_setting);
		btUsbSetting.setOnClickListener(this);
		btVol = (Button) mRootView.findViewById(R.id.usb_volume);
		btVol.setOnClickListener(this);

		ctlFmVolume = mRootView.findViewById(R.id.usb_contrl_volume);
		fmVolume = mRootView.findViewById(R.id.id_volume_view);
		showVolume = (TextView) mRootView.findViewById(R.id.id_volume_show);
		View addFmVol = mRootView.findViewById(R.id.all_volume_add_fm);
		View subFmVol = mRootView.findViewById(R.id.all_volume_sub_fm);
		addFmVol.setOnClickListener(this);
		subFmVol.setOnClickListener(this);
		muteFm = mRootView.findViewById(R.id.all_volume_mute);
		muteFm.setOnClickListener(this);



		tvMusicIndex = (TextView) mRootView.findViewById(R.id.musicIndex);
		tvCurrTime = (TextView) mRootView.findViewById(R.id.usb_currtime);
		tvAllTime = (TextView) mRootView.findViewById(R.id.usb_alltime);

		tvUsbAlbum = (TextView) mRootView.findViewById(R.id.album);
		tvUsbAuthor = (TextView) mRootView.findViewById(R.id.author);
		tvUsbTitle = (TextView) mRootView.findViewById(R.id.title);
	}
	
	private void startService(){
		Intent intent = new Intent(PHONE_APP_SERVICE).setPackage("com.yf.radiophoneapp");
		getActivity().startService(intent);
		getActivity().bindService(intent,connection,Context.BIND_AUTO_CREATE);
	}
	
	private MyHandler mHandler = new MyHandler((MainActivity) getActivity());

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	private class MyHandler extends  Handler{

		private WeakReference<MainActivity> mWeakReference = null;
		private MainActivity mainActivity;

		public MyHandler(MainActivity mActivity) {
			mWeakReference = new WeakReference<MainActivity>(mActivity);
			if (mWeakReference != null)
				mainActivity = mWeakReference.get();
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

				case WHAT_HIDE_VOLUME_BAR:
					refreshFmVolume(false);
					break;
				default:
					break;

			}
		}
	}
	
	ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mControl = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mControl = PhoneAppControl.Stub.asInterface(service);
			try {
				mControl.setListener(CallbackListener);

			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	
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
		public void onDeviceBondStatusChange(String address, boolean isConnect)
				throws RemoteException {
		}

		@Override
		public void onSourceStatusChange(final int type) throws RemoteException {

		}

		@Override
		public void onPlayingStatusChange(final int type) throws RemoteException {
//			Log.i("TAG","playstatus in usb= " + type);
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if(PLAYING == type){
						btPlay.setBackgroundResource(R.drawable.bt_usb_pause);
					}else{
						btPlay.setBackgroundResource(R.drawable.bt_usb_play);
					}
				}
			});
		}

		@Override
		public void onPlayOrderChange(final int playOrder) throws RemoteException {
//			mHandler.post(new Runnable() {
//				@Override
//				public void run() {
//					if(PLAYING == playOrder){
//						btPlay.setBackgroundResource(R.drawable.bt_usb_pause);
//					}else{
//						btPlay.setBackgroundResource(R.drawable.bt_usb_play);
//					}
//				}
//			});
		}

		@Override
		public void onCurrIndexChange(final int currIndex) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					currPos = currIndex;
					if (tvMusicIndex.getVisibility()==View.GONE)
						tvMusicIndex.setVisibility(View.VISIBLE);
						tvMusicIndex.setText(currPos + "/" + allPos);

				}
			});
		}

		@Override
		public void onAllIndexChange(final int allIndex) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					allPos = allIndex;
					}
			});

		}

		@Override
		public void onCurrPlayTimeChange(final long currTime) throws RemoteException {
			musicSeekBar.setProgress((int) currTime);
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (tvCurrTime.getVisibility()==View.GONE)
						tvCurrTime.setVisibility(View.VISIBLE);
						tvCurrTime.setText(switchTimeToStr(currTime));
				}
			});
		}

		private String switchTimeToStr(long times){
			String c = new SimpleDateFormat("HH:mm:ss").format(new Date(times + diffTime));
			return c;
		}

		@Override
		public void onAllPlayTimeChange(final long allTime) throws RemoteException {
			musicSeekBar.setMax((int) allTime);

			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (tvAllTime.getVisibility()==View.GONE)
						tvAllTime.setVisibility(View.VISIBLE);
						tvAllTime.setText(switchTimeToStr(allTime));
				}
			});
		}

		@Override
		public void onCurrUsbMusicName(final String usbMusicName) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					tvUsbTitle.setText(usbMusicName);
				}
			});

		}

		@Override
		public void onCurrUsbMusicAuthor(final String usbMusicAuthor) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					tvUsbAuthor.setText(usbMusicAuthor);
				}
			});

		}

		@Override
		public void onCurrUsbMusicAlbum(final String usbMusicAlbum) throws RemoteException {

			mHandler.post(new Runnable() {
				@Override
				public void run() {
					tvUsbAlbum.setText(usbMusicAlbum);
				}
			});
		}

	};


	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			startService();
		}
	};



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.usb_pre:
			try {
				mControl.UsbPre();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.usb_next:
			try {
				mControl.UsbNext();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.usb_play_pause:
			try {
				mControl.UsbPlayAndPause();

			} catch (RemoteException e) {
				e.printStackTrace();
			}
//			mHandler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						mControl.getUsbStatus();
//					} catch (RemoteException e) {
//						e.printStackTrace();
//					}
//				}
//			},500);
			break;
		case R.id.usb_setting:
			Context context = getActivity();
			Intent intent = new Intent(context,AppSettingView.class);
			context.startActivity(intent);
			break;
		case R.id.volume_setting:
			refreshFmVolume(ctlFmVolume.getVisibility() != View.VISIBLE);
			return;

		case R.id.all_volume_add_fm:
			changeFmVolume(1);
			return;
		case R.id.all_volume_sub_fm:
			changeFmVolume(-1);
			return;
		case R.id.all_volume_mute:
			switchMuteFm(currVolume);
			return;
		default:
			break;
		}
	}


	private void refreshFmVolume(boolean isVisible) {
		Animation animation = null;
		if (isVisible) {

			if (ctlFmVolume.getVisibility() == View.VISIBLE)
				return;
			int volume = 32;
			((AllVolumeView)fmVolume).setVolume(volume);

			showVolume.setText("媒体音量" + volume);
			currVolume = volume;
			mHandler.removeMessages(WHAT_HIDE_VOLUME_BAR);
			mHandler.sendEmptyMessageDelayed(WHAT_HIDE_VOLUME_BAR,HIDE_VOLUME_BAR_TIME);
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.all_volume_ctl_show);
			ctlFmVolume.setVisibility(View.VISIBLE);


		} else {
			if (ctlFmVolume.getVisibility() == View.GONE)
				return;
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.all_volume_ctl_hide);
			animation.setAnimationListener(new HideViewAnimListener(ctlFmVolume));
			if(currVolume == 0){
				btVol.setBackgroundResource(R.drawable.voice_jingyin_h);
			}else{
				btVol.setBackgroundResource(R.drawable.voice_jingyin);
			}
		}
		if (animation != null) {
			animation.setDuration(ANIM_TIME);
			ctlFmVolume.startAnimation(animation);
		}

	}

	private void changeFmVolume(int step) {
		mHandler.removeMessages(WHAT_HIDE_VOLUME_BAR);
		mHandler.sendEmptyMessageDelayed(WHAT_HIDE_VOLUME_BAR, HIDE_VOLUME_BAR_TIME);
		currVolume += step;
		if (currVolume < 0) {
			currVolume = 0;
		}
		if(currVolume >= 32){
			currVolume = 32;
		}

		((AllVolumeView)fmVolume).setVolume(currVolume);

		showVolume.setText("媒体音量" + currVolume);
	}

	private void switchMuteFm(int curvol){
		if(curvol > 0){
			showVolume.setText("媒体音量" + 0);
			((AllVolumeView)fmVolume).setVolume(0);
			currVolume = 0;
		}else{
			showVolume.setText("媒体音量" + currVolume);
			((AllVolumeView)fmVolume).setVolume(currVolume);
		}

	}


}
