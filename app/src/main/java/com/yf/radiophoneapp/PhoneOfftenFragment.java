package com.yf.radiophoneapp;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


/*
* 常用电话本页面
*
* */
public class PhoneOfftenFragment extends Fragment implements OnClickListener{
	private static final String TAG = "DialFragment";
	ViewGroup mRootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.phone_oftenuse_layout, null);
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
