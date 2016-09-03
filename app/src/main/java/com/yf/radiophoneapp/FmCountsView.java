package com.yf.radiophoneapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yf.phoneapp.bluetoothadapter.RadioListAdapter;

public class FmCountsView extends Activity implements OnClickListener{

	Button fmcounts_Back;
	private ListView radiolistview;
	private RadioListAdapter adapter;
	private String[] FmNums = new String[]{"6个","12个","18个","24个","30个","36个"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getfmcounts);
		initView();
		Log.d("TAG", "bt-Fmcounts oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initView(){
		radiolistview = (ListView) findViewById(R.id.fm_list);
		adapter = new RadioListAdapter(this, FmNums);
		radiolistview.setAdapter(adapter);
		fmcounts_Back = (Button) findViewById(R.id.fmcounts_back);
		fmcounts_Back.setOnClickListener(this);
		radiolistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

	}

	
	private Handler mHandler = new Handler();

	Context conntext = this;




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fmcounts_back:
			finish();
			break;

		default:
			break;
		}
	}

}
