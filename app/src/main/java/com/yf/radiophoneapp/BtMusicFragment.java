package com.yf.radiophoneapp;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BtMusicFragment extends Fragment implements OnClickListener{
	private static final String PHONE_APP_SERVICE = "com.yf.phoneapp.service";
	private PhoneAppControl mControl = null;
	ViewGroup mRootView;
	Button btPre,btNext,btPlay;
	TextView tvBtIndex,tvBtTitle,tvBtAuthor,tvBtAlbum;
	private int PLAYING = 0x01;
	private static int currPos;
	private static int allPos;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.btmusic_play_layout, null);
		findView();
		startService();
		return mRootView;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unbindService(connection);
	}

	private void findView() {
		btPre = (Button) mRootView.findViewById(R.id.btmusic_pre);
		btPre.setOnClickListener(this);
		btNext = (Button) mRootView.findViewById(R.id.btmusic_next);
		btNext.setOnClickListener(this);
		btPlay = (Button) mRootView.findViewById(R.id.btmusic_play_pause);
		btPlay.setOnClickListener(this);
		tvBtIndex = (TextView) mRootView.findViewById(R.id.btmusic_index);
		tvBtTitle = (TextView) mRootView.findViewById(R.id.btmusic_title);
		tvBtAuthor = (TextView) mRootView.findViewById(R.id.btmusic_author);
		tvBtAlbum = (TextView) mRootView.findViewById(R.id.btmusic_album);


	}
	
	private void startService(){
		Intent intent = new Intent(PHONE_APP_SERVICE).setPackage("com.yf.radiophoneapp");
		getActivity().startService(intent);
		getActivity().bindService(intent,connection,Context.BIND_AUTO_CREATE);
	}
	
	private Handler mHandler = new Handler();
	
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
//			mHandler.post(new Runnable() {
//				@Override
//				public void run() {
////					getCurrSource(type);
//				}
//			});
		}

		@Override
		public void onPlayingStatusChange(final int type) throws RemoteException {
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
		public void onPlayOrderChange(int playOrder) throws RemoteException {

		}

		@Override
		public void onCurrIndexChange(final int currIndex) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					currPos = currIndex;
					if (tvBtIndex.getVisibility()==View.GONE)
						tvBtIndex.setVisibility(View.VISIBLE);
					tvBtIndex.setText(currPos + "/" + allPos);

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
		public void onCurrPlayTimeChange(long currTime) throws RemoteException {

		}

		@Override
		public void onAllPlayTimeChange(long allTime) throws RemoteException {

		}

		@Override
		public void onCurrUsbMusicName(final String usbMusicName) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					tvBtTitle.setText(usbMusicName);
				}
			});

		}

		@Override
		public void onCurrUsbMusicAuthor(final String usbMusicAuthor) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					tvBtAuthor.setText(usbMusicAuthor);
				}
			});

		}

		@Override
		public void onCurrUsbMusicAlbum(final String usbMusicAlbum) throws RemoteException {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					tvBtAlbum.setText(usbMusicAlbum);
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
		case R.id.btmusic_pre:
			try {
				mControl.BtMusicPre();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.btmusic_next:
			try {
				mControl.BtMusicNext();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.btmusic_play_pause:
			try {
				mControl.BtMusicPlayAndPause();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
