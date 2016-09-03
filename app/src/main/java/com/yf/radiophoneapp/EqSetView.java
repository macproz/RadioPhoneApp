package com.yf.radiophoneapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yf.phoneapp.eqsetting.DaoSound;
import com.yf.phoneapp.eqsetting.GPointBar;
import com.yf.phoneapp.eqsetting.VolumeBar;

public class EqSetView extends Activity implements OnClickListener,View.OnLongClickListener,View.OnTouchListener,
		VolumeBar.VolumeBarChangeListener,GPointBar.GPointBarChangeListener{

	private Button[] btnStyles;
	private Button btnLeft, btnRight, btnReset,btnEqback;
	private View viewContentBalance, viewContentStyle;
	private GPointBar gPointBar;
	private TextView tvGPointX;// tvBass, tvMiddle, tvTreble;
	private TextView tvGPointY;
	private VolumeBar barBass, barMiddle, barTreble;

	DaoSound curDaoSound;
//	EQSaveUtils eqSaveUtils;
	private final int DEF_PROGRESS = DaoSound.DEF_PROGRESS;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_sound_set);
		init();
		initUI();
		showJhqView();
		Log.d("TAG", "eqsetting oncreate");
	}


	private void showJhqView() {

		btnLeft.setSelected(true);
		btnLeft.requestFocus();
		btnRight.setSelected(false);
		viewContentStyle.setVisibility(View.VISIBLE);
		viewContentBalance.setVisibility(View.GONE);
	}

	private void init() {
//		rootView.findViewById(R.id.low).setVisibility(View.GONE);
		findViewById(R.id.high).setVisibility(View.VISIBLE);

		// 显示X、隐藏Y轴坐标的显示
		findViewById(R.id.eq_tv_gpoint_text_x).setVisibility(View.VISIBLE);
		findViewById(R.id.eq_tv_gpoint_text_y).setVisibility(View.VISIBLE);

		// 显示bar1、影藏bar2,高低配置的bar范围不同
//		rootView.findViewById(R.id.eq_gpoint_bar).setVisibility(View.GONE);
		findViewById(R.id.eq_gpoint_bar2).setVisibility(View.VISIBLE);

		// 影藏Y轴的按钮
		findViewById(R.id.eq_btn_gpoint_up).setVisibility(View.VISIBLE);
		findViewById(R.id.eq_btn_gpoint_down).setVisibility(View.VISIBLE);

		// 影藏高配版的左右件
		findViewById(R.id.eq_btn_gpoint_sub1).setVisibility(View.VISIBLE);
		findViewById(R.id.eq_btn_gpoint_add1).setVisibility(View.VISIBLE);

		// 显示低配版的左右
		findViewById(R.id.eq_btn_gpoint_sub).setVisibility(View.GONE);
		findViewById(R.id.eq_btn_gpoint_add).setVisibility(View.GONE);
		return;

	}

	private void initUI(){

		viewContentStyle = findViewById(R.id.low_high_pitch);

		viewContentBalance = findViewById(R.id.eq_content_balance);

		btnLeft = (Button) findViewById(R.id.eq_btn_bottom_left);
		btnRight = (Button) findViewById(R.id.eq_btn_bottom_right);
		btnReset = (Button) findViewById(R.id.eq_btn_reset);
		btnEqback = (Button) findViewById(R.id.soundcfg_back);

		barBass = (VolumeBar) findViewById(R.id.eq_bass_volume);
		barMiddle = (VolumeBar)findViewById(R.id.eq_middle_volume);
		barTreble = (VolumeBar)findViewById(R.id.eq_treble_volume);


		gPointBar = (GPointBar)findViewById(R.id.eq_gpoint_bar2);


		tvGPointX = (TextView)findViewById(R.id.eq_tv_gpoint_text_x);
		tvGPointY = (TextView)findViewById(R.id.eq_tv_gpoint_text_y);
//		 tvBass = (TextView) root.findViewById(R.id.eq_tv_bass);
//		 tvMiddle = (TextView) root.findViewById(R.id.eq_tv_middle);
//		 tvTreble = (TextView) root.findViewById(R.id.eq_tv_treble);

		// 滑动条设置
//		mCarSwitchView = (CarSwitchView_new) root.findViewById(R.id.eq_close);
//		mCarSwitchView.setOnStateChangeListener(this);
//
//		mCarSwitchView.setOnInitStateChangeListener(new OnInitListener() {
//			@Override
//			public void initFinish() {
//
//				Log.d("sound", "--------setOnInitStateChangeListener-------");
//
//				initData();
//				// showJhqView(false);
//			}
//		});




		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnEqback.setOnClickListener(this);
		btnReset.setOnClickListener(this);
		barBass.setVolumeBarChangeListener(this);
		barMiddle.setVolumeBarChangeListener(this);
		barTreble.setVolumeBarChangeListener(this);

		gPointBar.setGPointBarChangeListener(this);

		// root.findViewById(R.id.tmp_view).setOnClickListener(this);
		Button btn = null;
		btn = (Button) findViewById(R.id.eq_bass_add);
		btn.setOnClickListener(this);
		btn.setOnLongClickListener(this);
		btn.setOnTouchListener(this);
		btn = (Button) findViewById(R.id.eq_bass_sub);
		btn.setOnClickListener(this);
		btn.setOnLongClickListener(this);
		btn.setOnTouchListener(this);
		btn = (Button) findViewById(R.id.eq_middle_add);
		btn.setOnClickListener(this);
		btn.setOnLongClickListener(this);
		btn.setOnTouchListener(this);
		btn = (Button) findViewById(R.id.eq_middle_sub);
		btn.setOnClickListener(this);
		btn.setOnLongClickListener(this);
		btn.setOnTouchListener(this);
		btn = (Button) findViewById(R.id.eq_treble_add);
		btn.setOnClickListener(this);
		btn.setOnLongClickListener(this);
		btn.setOnTouchListener(this);
		btn = (Button) findViewById(R.id.eq_treble_sub);
		btn.setOnClickListener(this);
		btn.setOnLongClickListener(this);
		btn.setOnTouchListener(this);

		findViewById(R.id.eq_btn_gpoint_up).setOnClickListener(this);
		findViewById(R.id.eq_btn_gpoint_down).setOnClickListener(this);
		btn = (Button) findViewById(R.id.eq_btn_gpoint_sub);
		btn.setOnClickListener(this);

		btn = (Button) findViewById(R.id.eq_btn_gpoint_sub1);
		btn.setOnClickListener(this);

		// btn.setOnLongClickListener(this);
		// btn.setOnTouchListener(this);
		btn = (Button) findViewById(R.id.eq_btn_gpoint_add);
		btn.setOnClickListener(this);

		btn = (Button) findViewById(R.id.eq_btn_gpoint_add1);
		btn.setOnClickListener(this);


		btn = (Button) findViewById(R.id.eq_btn_gpoint_up);
		btn.setOnClickListener(this);

		btn = (Button) findViewById(R.id.eq_btn_gpoint_down);
		btn.setOnClickListener(this);

		// 默认选中
		btnLeft.setSelected(true);
		btnRight.setSelected(false);


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	private Handler mHandler = new Handler();

	int progress;
	@Override
	public void onClick(View v) {
		myClick(v.getId(), v);
	}

	private void myClick(int id, View v) {
		switch (id) {
			case R.id.eq_bass_add:

				progress = barBass.getProgress() + 1;

				setBassVolume(barBass.getProgress() + 1);
				break;
			case R.id.eq_bass_sub:

				progress = barBass.getProgress() - 1;

				setBassVolume(barBass.getProgress() - 1);
				break;
			case R.id.eq_middle_add:
				setMiddleVolume(barMiddle.getProgress() + 1);
				break;
			case R.id.eq_middle_sub:
				setMiddleVolume(barMiddle.getProgress() - 1);
				break;
			case R.id.eq_treble_add:

				progress = barBass.getProgress() + 1;

				setTrebleVolume(barTreble.getProgress() + 1);
				break;
			case R.id.eq_treble_sub:

				progress = barBass.getProgress() - 1;

				setTrebleVolume(barTreble.getProgress() - 1);
				break;
			case R.id.eq_btn_gpoint_add:
			case R.id.eq_btn_gpoint_add1:
				changeGpoing(1, 0);
				break;
			case R.id.eq_btn_gpoint_sub:
			case R.id.eq_btn_gpoint_sub1:
				changeGpoing(-1, 0);
				break;
			case R.id.eq_btn_gpoint_up:
				changeGpoing(0, -1);
				break;
			case R.id.eq_btn_gpoint_down:
				changeGpoing(0, 1);
				break;
			case R.id.eq_btn_bottom_left:
				// 自定义

				Log.d("setSound", "--------eq_btn_style_define------");

				showLowHighPitch();

				changeSoundStyle(DaoSound.STYLE_INDEX_DEFINE, v);
				break;

			case R.id.eq_btn_bottom_right:
				showJyphView();
				break;

			case R.id.eq_btn_reset:
				reset();
				break;
			case R.id.soundcfg_back:
				finish();
				break;
		}

	}


	private void changeSoundStyle(int index, View view) {

		Log.d("setSound", "----changeSoundStyle-----index:" + index);

		if (view != null && view.isSelected()) {

			return;
		}
		// 保存选中索引
//		eqSaveUtils.saveInt(EQSaveUtils.KEY_STYLE_INDEX, index);

		for (int i = 0; i < btnStyles.length; i++) {
			if (btnStyles[i].isSelected())
				btnStyles[i].setSelected(false);
		}

		// 设置音效模式。
		btnStyles[index].setSelected(true);

		setSoundStyle(index);
	}


	private void setSoundStyle(int index) {

		Log.d("setSound", "------setSoundStyle(int index)---------index:"
				+ index);

		// 自定义时没有设置时的默认值
		int defvalue[] = new int[] { 7, 7, 7 };
		int value[] = new int[3];

		// 自定义时，记忆用户的值，然后设置给系统
		if (index == DaoSound.STYLE_INDEX_DEFINE) {

			if (this != null) {
				// value = SysConfigUtils.getCurEqtypeAndEqValues(getActivity(),
				// defvalue);

				// 获取用户自定义的低中高
//				value[0] = Settings.System.getInt(getActivity()
//						.getContentResolver(), EQSaveUtils.DEFINE_BASS, 7);
//				value[1] = Settings.System.getInt(getActivity()
//						.getContentResolver(), EQSaveUtils.DEFINE_MEDIANT, 7);
//				value[2] = Settings.System.getInt(getActivity()
//						.getContentResolver(), EQSaveUtils.DEFINE_TREBLE, 7);

				// 设置UI
				barBass.setProgress(value[0]);
				barMiddle.setProgress(value[1]);
				barTreble.setProgress(value[2]);

				Log.d("setSound", "---自定义--0:" + value[0] + "  1:" + value[1]
						+ "  2:" + value[2]);

				// 设置给系统
//				SysConfigUtils.setEqValue(getActivity(), 0, value[0], value[1], value[2]);
			}

		} else {
//			eqSaveUtils.setEqType(getActivity(), index);

		}
	}

	private void showJyphView() {

		viewContentStyle.setVisibility(View.GONE);

		if (viewContentBalance.getVisibility() == View.VISIBLE)
			return;
		btnLeft.setSelected(false);
		btnRight.setSelected(true);
		btnRight.requestFocus();
		viewContentBalance.setVisibility(View.VISIBLE);
	}

	private void showLowHighPitch() {
		Log.d("setSound", "--------showLowHighPitch-------------");
		btnLeft.setSelected(true);
		btnRight.setSelected(false);
		btnRight.requestFocus();
		viewContentBalance.setVisibility(View.GONE);
		viewContentStyle.setVisibility(View.VISIBLE);

		return;
	}


	private void changeGpoing(int xStep, int yStep) {

		GPointBar.GPointItem item = gPointBar.getProgress();

		item.x += xStep;
		item.y += yStep;

		gPointBar.setProgress(item.x, item.y);

		if (item.x < 0) {
			item.x = 0;
		}
		if (item.x > 20) {
			item.x = 20;
		}

		if (item.y < 0) {
			item.y = 0;
		}
		if (item.y > 20) {
			item.y = 20;
		}

		Log.d("balance", "-----changeGpoing--------item.x:" + item.x);
		Log.d("balance", "-----changeGpoing--------item.y:" + item.y);
//		SysConfigUtils.setBalance(getActivity(), item.x, item.y);
	}



	private void setBassVolume(int value) {

		barBass.setProgress(value);

		setVolume();
	}

	private void setMiddleVolume(int value) {
		barMiddle.setProgress(value);
		setVolume();
	}

	private void setTrebleVolume(int value) {
		barTreble.setProgress(value);
		setVolume();
	}

	// 自定义后，通过这设置音效值
	private void setVolume() {
		Log.d("setSound", "--0:" + curDaoSound.bass);
		Log.d("setSound", "--1:" + curDaoSound.middle);
		Log.d("setSound", "--2:" + curDaoSound.treble);

		// 记忆用户的值
//		Settings.System.putInt(getActivity().getContentResolver(),
//				EQSaveUtils.DEFINE_BASS, curDaoSound.bass);
//		Settings.System.putInt(getActivity().getContentResolver(),
//				EQSaveUtils.DEFINE_MEDIANT, curDaoSound.middle);
//		Settings.System.putInt(getActivity().getContentResolver(),
//				EQSaveUtils.DEFINE_TREBLE, curDaoSound.treble);
//
//		EQSaveUtils.setEqValue(getActivity(),
//				SysConfigUtils.EQTYPE_INDEX_DEFINE, curDaoSound.bass,
//				curDaoSound.middle, curDaoSound.treble);
	}


	private void reset() {
		barBass.setProgress(DEF_PROGRESS);
		barMiddle.setProgress(DEF_PROGRESS);
		barTreble.setProgress(DEF_PROGRESS);

		Log.d("setSound", "------------reset(7,7,7)--------------");

//		SysConfigUtils.setEqValue(getActivity(), DaoSound.STYLE_INDEX_DEFINE, DEF_PROGRESS, DEF_PROGRESS, DEF_PROGRESS);
	}

	@Override
	public void onGPointBarChange(View view, int valueX, int valueY, boolean isUser) {

	}

	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	public void onChange(View view, int value, boolean isUser) {

	}
}
