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

import com.yf.phoneapp.bluetoothadapter.RadioListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmCountsView extends Activity implements OnClickListener{

	Button amcounts_Back;
	private ListView radiolistview;
	private RadioListAdapter adapter;
	public static Map<Integer, Boolean> isSelected;
	public static List beSelectedData = new ArrayList<>();
	private List datas = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getamcounts);
		radiolistview = (ListView) findViewById(R.id.am_list);
		datas = new ArrayList();
		datas.add("6个");
		datas.add("12个");
		initView();
		Log.d("TAG", "bt-Amcounts oncreate");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void initView(){
		if(datas == null || datas.size() == 0){
			return;
		}
		if(isSelected != null)
			isSelected = null;
		isSelected = new HashMap<Integer, Boolean>();
		for(int i = 0; i < datas.size(); i++){
			isSelected.put(i, false);
		}

		if(beSelectedData.size() > 0){
			beSelectedData.clear();
		}

		adapter = new RadioListAdapter(this, datas);
		radiolistview.setAdapter(adapter);
		radiolistview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter.notifyDataSetChanged();
		radiolistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("TAG", "-----AM counts" +  datas.get(position).toString());
			}
		});

		amcounts_Back = (Button) findViewById(R.id.amcounts_back);
		amcounts_Back.setOnClickListener(this);


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
