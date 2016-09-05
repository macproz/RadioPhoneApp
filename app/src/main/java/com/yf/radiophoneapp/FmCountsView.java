package com.yf.radiophoneapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yf.phoneapp.bluetoothadapter.RadioListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FmCountsView extends Activity implements OnClickListener{

	Button fmcounts_Back;
	private ListView radiolistview;
	private RadioListAdapter adapter;
	private List FmDatas = null;
	private String[] FmNums = new String[]{"6个","12个","18个","24个","30个","36个"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getfmcounts);
		radiolistview = (ListView) findViewById(R.id.fm_list);
		FmDatas =  new ArrayList();
		FmDatas.add("6个");
		FmDatas.add("12个");
		FmDatas.add("18个");
		FmDatas.add("24个");
		FmDatas.add("30个");
		FmDatas.add("36个");
		initView();
		Log.d("TAG", "bt-Fmcounts oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initView(){
		if(FmDatas == null || FmDatas.size() == 0){
			return;
		}
		if(AmCountsView.isSelected != null)
			AmCountsView.isSelected = null;
		AmCountsView.isSelected = new HashMap<Integer, Boolean>();
		for(int i = 0; i < FmDatas.size(); i++){
			AmCountsView.isSelected.put(i, false);
		}

		if(AmCountsView.beSelectedData.size() > 0){
			AmCountsView.beSelectedData.clear();
		}

		adapter = new RadioListAdapter(this, FmDatas);
		radiolistview.setAdapter(adapter);
		radiolistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter.notifyDataSetChanged();
		radiolistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("TAG", "----FM counts" + FmDatas.get(position).toString());
			}
		});

		fmcounts_Back = (Button) findViewById(R.id.fmcounts_back);
		fmcounts_Back.setOnClickListener(this);


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
