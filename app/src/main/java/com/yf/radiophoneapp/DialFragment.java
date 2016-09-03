package com.yf.radiophoneapp;

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

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;

public class DialFragment extends Fragment implements OnClickListener{
	private static final String TAG = "DialFragment";
	ViewGroup mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.phone_dail_layout, null);
		findView();
		return mRootView;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void findView() {
		
	}
	
	private Handler mHandler = new Handler();
	@Override
	public void onClick(View v) {
	
		}
	
}
