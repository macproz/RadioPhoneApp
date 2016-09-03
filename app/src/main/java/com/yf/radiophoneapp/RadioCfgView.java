package com.yf.radiophoneapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RadioCfgView extends Activity implements OnClickListener{

	Button btBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radiocfg);
		findView();
		Log.d("TAG", "bt-Radiocfg oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void findView(){
		btBack = (Button) findViewById(R.id.radiocfg_back);
		btBack.setOnClickListener(this);
		findViewById(R.id.fm_show_num).setOnClickListener(this);
		findViewById(R.id.am_show_num).setOnClickListener(this);
		findViewById(R.id.fm_show_num).setSelected(true);

	}

	
	private Handler mHandler = new Handler();

	Context conntext = this;




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radiocfg_back:
			finish();
			break;
		case R.id.fm_show_num:
			Intent intent = new Intent(this, FmCountsView.class);
			startActivity(intent);
			break;
		case R.id.am_show_num:
			Intent intent2 = new Intent(this, AmCountsView.class);
			startActivity(intent2);
			break;


		default:
			break;
		}
	}

}
