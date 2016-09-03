package com.yf.radiophoneapp;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.yf.phoneapp.aidl.PhoneAppControl;
import com.yf.phoneapp.ui.DialogUtils;
import com.yf.phoneapp.util.UrrSourceType;

import java.util.Arrays;
import java.util.List;

public class PhoneActivity extends Activity implements OnClickListener{
	private static final String TAG = "PhoneActivity";
	public  FragmentTransaction mFragmentTransaction;
	private DialFragment mDialFragment;
	private TalkFragment mTalkFragment;
	private PhoneBookFragment mphoneBookFragment;
	private PhoneOfftenFragment mphoneOfftenFragment;

	private Button btDail,btTalk,btPhoneBook,btPhoneOffen;
	private int id_layout_phone=R.id.id_layout_phone;
	private List<Integer> mButtonIds = Arrays.asList(
			R.id.bt_dial_panel,
			R.id.bt_talk_record,
			R.id.bt_phone_book,
			R.id.bt_phone_offen);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        findView();

    }



	@Override
    protected void onDestroy() {
    	super.onDestroy();
		Toast.makeText(this,"----phone-act--onDestroy----",Toast.LENGTH_SHORT).show();
    }


	private void findView() {
		btDail = (Button) findViewById(R.id.bt_dial_panel);
		btDail.setOnClickListener(this);
		btTalk = (Button) findViewById(R.id.bt_talk_record);
		btTalk.setOnClickListener(this);
		btPhoneBook = (Button) findViewById(R.id.bt_phone_book);
		btPhoneBook.setOnClickListener(this);
		btPhoneOffen = (Button) findViewById(R.id.bt_phone_offen);
		btPhoneOffen.setOnClickListener(this);
		
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mDialFragment = new DialFragment();
		mTalkFragment = new TalkFragment();
		mphoneBookFragment = new PhoneBookFragment();
		mphoneOfftenFragment = new PhoneOfftenFragment();

		
		mFragmentTransaction.add(id_layout_phone, mDialFragment);
		mFragmentTransaction.add(id_layout_phone, mTalkFragment);
		mFragmentTransaction.add(id_layout_phone, mphoneBookFragment);
		mFragmentTransaction.add(id_layout_phone, mphoneOfftenFragment);


		mFragmentTransaction.hide(mTalkFragment);
		mFragmentTransaction.hide(mphoneBookFragment);
		mFragmentTransaction.hide(mphoneOfftenFragment);
		mFragmentTransaction.show(mDialFragment);
		mFragmentTransaction.commit();
	}
	
	public void showDialFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mTalkFragment);
		mFragmentTransaction.hide(mphoneBookFragment);
		mFragmentTransaction.hide(mphoneOfftenFragment);
		mFragmentTransaction.show(mDialFragment);
		mFragmentTransaction.commit();
	}
	
	public void showTalkFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mDialFragment);
		mFragmentTransaction.hide(mphoneBookFragment);
		mFragmentTransaction.hide(mphoneOfftenFragment);
		mFragmentTransaction.show(mTalkFragment);
		mFragmentTransaction.commit();
	}
	
	public void showPhoneBookFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mDialFragment);
		mFragmentTransaction.hide(mTalkFragment);
		mFragmentTransaction.hide(mphoneOfftenFragment);
		mFragmentTransaction.show(mphoneBookFragment);
		mFragmentTransaction.commit();
	}

	public void showPhoneOfftenFragment() {
		mFragmentTransaction = getFragmentManager().beginTransaction();
		mFragmentTransaction.hide(mDialFragment);
		mFragmentTransaction.hide(mTalkFragment);
		mFragmentTransaction.hide(mphoneBookFragment);
		mFragmentTransaction.show(mphoneOfftenFragment);
		mFragmentTransaction.commit();
	}

	public void selectButton(int id){
		for (int btnId : mButtonIds) {
			findViewById(btnId).setSelected(id == btnId);
		}
	}
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_dial_panel:
			showDialFragment();
			selectButton(R.id.bt_dial_panel);
			break;
		case R.id.bt_talk_record:
			showTalkFragment();
			selectButton(R.id.bt_talk_record);
			break;
		case R.id.bt_phone_book:
			showPhoneBookFragment();
			selectButton(R.id.bt_phone_book);
			break;
		case R.id.bt_phone_offen:
			showPhoneOfftenFragment();
			selectButton(R.id.bt_phone_offen);
			break;
		default:
			break;
		}
	}

	public void gotoPhone(){
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		selectButton(R.id.bt_phone);
	}

}
