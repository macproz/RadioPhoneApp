package com.yf.radiophoneapp;

import android.app.Fragment;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;

public class DialFragment extends Fragment implements OnClickListener{
	private static final String TAG = "DialFragment";
	ViewGroup mRootView;
	private StringBuilder mCallPhoneNo;
	private TextView mCurPhoneNo;


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
		mCurPhoneNo = (TextView) mRootView.findViewById(R.id.cur_phone_num);
		for(int i=0; i < ids.length; i++){
			mRootView.findViewById(ids[i]).setOnClickListener(this);
		}
		refresh();

	}
	
	private Handler mHandler = new Handler();


	int[] ids = new int[]{
			R.id.cur_num_1,
			R.id.cur_num_2,
			R.id.cur_num_3,
			R.id.cur_num_4,
			R.id.cur_num_5,
			R.id.cur_num_6,
			R.id.cur_num_7,
			R.id.cur_num_8,
			R.id.cur_num_9,
			R.id.cur_num_st,
			R.id.cur_num_0,
			R.id.cur_num_sh,
			R.id.cur_del,
			R.id.cur_clear,
			R.id.cur_cal
	};
	private int selectIndex = -1;
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cur_clear:
				mCurPhoneNo.setText("");
				mCallPhoneNo = new StringBuilder();
				break;
			case R.id.cur_cal:
				callPhone(mCallPhoneNo.toString());
				break;
			case R.id.cur_del:
				if(mCallPhoneNo.length() > 0){
					mCallPhoneNo.deleteCharAt(mCallPhoneNo.length() - 1 );
				}
				break;
			case R.id.cur_num_sh:
				if(mCallPhoneNo.length() < 20){
					mCallPhoneNo.append("#");
				}
				break;
			case R.id.cur_num_st:
				if(mCallPhoneNo.length() < 20){
					mCallPhoneNo.append("*");
				}
				break;
			default:
				for(int i=0; i < ids.length; i++){
					mRootView.findViewById(ids[i]).setSelected(false);
				}
				if(mCallPhoneNo.length() < 20){
					mCallPhoneNo.append(((TextView)v).getText());
				}
				break;
		}
			updateShowPhoneNum();
		}

	private void callPhone(String phoneNo) {
		String showNum = mCurPhoneNo.getText().toString();

		if (TextUtils.isEmpty(showNum)) {
			updateShowPhoneNum();
		} else if (phoneNo != null && phoneNo.trim().length() > 0) {
//					PhoneActivity.saveDialPhoneNum = mCallPhoneNo.toString();
					mCurPhoneNo.setText("");
					mCallPhoneNo.setLength(0);
					Uri uri= Uri.parse("tel:"+ phoneNo);
					Intent intent=new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(uri);
					getActivity().startActivity(intent);

		}
	}

	private void updateShowPhoneNum() {
		if (mCallPhoneNo.length() >= 17) {
			String tmp = mCallPhoneNo.substring(mCallPhoneNo.length() - 14, mCallPhoneNo.length());
			mCurPhoneNo.setText("..." + tmp);
		} else {
			mCurPhoneNo.setText(mCallPhoneNo.toString());
		}
	}

	public void refresh() {
		mCallPhoneNo = new StringBuilder();
		mCurPhoneNo.setText("");

//		if (devName == null) {
//			return;
//		}
//		BluetoothDevice device = mYFBTManager.getRemoteDevice();
//		if (device != null && mYFBTManager.isBluetoothConnected()) {
//			// why need get two times?
//			device.getName();
//			devName.setText(device.getName());
//		} else {
//			devName.setText("");
//		}
	}
	
}
