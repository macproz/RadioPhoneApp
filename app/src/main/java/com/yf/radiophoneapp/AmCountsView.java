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

import com.yf.phoneapp.bluetoothadapter.RadioListAdapter;

public class AmCountsView extends Activity implements OnClickListener{

	Button amcounts_Back;
	private ListView radiolistview;
	private RadioListAdapter adapter;
	private String[] AmNums = new String[]{"6个","12个"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getamcounts);
		initView();
		Log.d("TAG", "bt-Fmcounts oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initView(){
		radiolistview = (ListView) findViewById(R.id.am_list);
		adapter = new RadioListAdapter(this, AmNums);
		radiolistview.setAdapter(adapter);
		amcounts_Back = (Button) findViewById(R.id.amcounts_back);
		amcounts_Back.setOnClickListener(this);
		radiolistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

	}

	
	private Handler mHandler = new Handler();

	Context conntext = this;




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.amcounts_back:
			finish();
			break;

		default:
			break;
		}
	}

}
