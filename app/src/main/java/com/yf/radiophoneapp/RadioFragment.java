package com.yf.radiophoneapp;

import com.yf.phoneapp.aidl.PhoneAppCallback;
import com.yf.phoneapp.aidl.PhoneAppControl;

import com.yf.phoneapp.ui.AllVolumeView;
import com.yf.phoneapp.ui.DialogUtils;
import com.yf.phoneapp.ui.HideViewAnimListener;
import com.yf.phoneapp.util.UrrSourceType;
import com.yf.protocol.AppProtocol;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RadioFragment extends Fragment implements OnClickListener, View.OnLongClickListener, View.OnTouchListener,
		ViewPager.OnPageChangeListener{
	private static final String PHONE_APP_SERVICE = "com.yf.phoneapp.service";
	private ViewGroup mRootView;
	private View ctlFmVolume, fmVolume,muteFm;
	private TextView showVolume,radioUnitType,textFreq;
	private PhoneAppControl mControl = null;
	private Button btNext,btPre,btFmChange,btAmChange,btSearch,btAdd, btSub;
	private Button btVol, btEqualizer, btSetting;

	private TextView favorTxts[] = null;
	private ViewPager mViewPager;

	//每页列表索引
	private ImageView mRadioPage1, mRadioPage2, mRadioPage3, mRadioPage4, mRadioPage5, mRadioPage6;

	//每页6个频率
	private TextView mTv1, mTv2, mTv3, mTv4, mTv5, mTv6;

	private LinearLayout mPageLayout;
	private Drawable favorbtnDrawable;
	private Drawable favorbtnDrawable_p;

	private static int FM_TYPE = 0x02;
	private static int AM_TYPE = 0x03;
	private int curr_radio_type;
	// 上一个频道
	private static int nPreBand = FM_TYPE;
	// 收藏夹频道
	private static int favorBand = FM_TYPE;


	// 计算第几个频点
	private static int posAm;
	private static int posFm;
	private static int isflag = 0;

	// 记忆当前页数，默认第一页
	private static int remAM = 0;
	private static int remFM = 0;

	private static int pageNumFm = 1; // 页数
	private static int pageNumAm = 1;

	// private static DataParams mStereo; // 立体音

	private SharedPreferences sp;

	private int currVolume = 0;
	private long HIDE_VOLUME_BAR_TIME = 5000;

	private Timer timerVolume;
	private long TIMER_DELAY = 50;
	private long TIMER_PERIOD = 400;
	private long ANIM_TIME = 300;

	private final MyHandler mHandler = new MyHandler((MainActivity) getActivity());


	private static final int MSG_SLIDE_COORDINATE_INIT = 0x50;
	private static final int MSG_PROTECT = 0x60;
	private static final int MSG_STEREO_SHOW = 0x70;
	private static final int MSG_KEY_LEFT = 0x80;
	private static final int MSG_KEY_RIGHT = 0x90;
	private static final int MSG_DELAY_DISMISS = 0x91;
	private static final int MSG_DELAY_SET_PROGRESS = 0x99;
	private static final int MSG_CHANGE_AMBAND = 0x20;
	private static final int MSG_UPDATA_UI = 0x10;
	private static final int MSG_START_UPDATE_UI = 0x30;
	private static final int MSG_RADIO_QUIT = 0x40;

	private int lastViewPagerIndex = -1;
	private int onPageSelected = 0;
	private List<View> mViewContainter = new ArrayList<View>();

	//每页收藏电台布局，FM有6页，AM有2页
	private View page1, page2, page3, page4, page5, page6 = null;


	private static boolean isProtect = false;
	private static final int PROTECT_TIME = 600;

	// 新增搜索界面使用
	private SeekBar seekabrSearch = null;
	private Button btQuitSearch = null;
	private int count = 0; // 计个数
	private int progress = 0; // 进度
	private boolean isTouchClick = true; // 是否可Touch和长安

	private View layoutVol;
	private int[] singlePageFavor= new int[12];
	private int[] recvPage1Data;
	private int[] recvPage2Data;
	private int[] recvPage3Data;
	private int[] recvPage4Data;
	private int[] recvPage5Data;
	private int[] recvPage6Data;
	private int[] recvPage7Data;
	private int[] recvPage8Data;

	private int[] lastUse;
	private int recvType = -1;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		startService();
		mRootView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.raido_layout, null);
		findView();
		initPages();
		startByPage();
		return mRootView;
	}


	private void initPages(){
		page1 = LayoutInflater.from(getActivity()).inflate(R.layout.page, null);//对应第一页的布局，以下类推
		page2 = LayoutInflater.from(getActivity()).inflate(R.layout.page, null);
		page3 = LayoutInflater.from(getActivity()).inflate(R.layout.page, null);
		page4 = LayoutInflater.from(getActivity()).inflate(R.layout.page, null);
		page5 = LayoutInflater.from(getActivity()).inflate(R.layout.page, null);
		page6 = LayoutInflater.from(getActivity()).inflate(R.layout.page, null);
	}




	private void findView() {
		layoutVol = mRootView.findViewById(R.id.fm_contrl_volume);
		mRootView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
					case MotionEvent.ACTION_UP:
							if (layoutVol.getVisibility()==View.VISIBLE){
								layoutVol.setVisibility(View.GONE);
							}
							break;
				}
				return true;
			}
		});

		textFreq = (TextView) mRootView.findViewById(R.id.radio_freq);
		btNext = (Button) mRootView.findViewById(R.id.radio_next);
		btNext.setOnClickListener(this);
		btPre = (Button) mRootView.findViewById(R.id.radio_pre);
		btPre.setOnClickListener(this);

		btAdd = (Button) mRootView.findViewById(R.id.freq_add);
		btAdd.setOnClickListener(this);
		btSub = (Button) mRootView.findViewById(R.id.freq_sub);
		btSub.setOnClickListener(this);

		btFmChange = (Button) mRootView.findViewById(R.id.radio_fm_band);
		btFmChange.setOnClickListener(this);

		btAmChange = (Button) mRootView.findViewById(R.id.radio_am_band);
		btAmChange.setOnClickListener(this);

		btSearch = (Button) mRootView.findViewById(R.id.radio_search);
		btSearch.setOnClickListener(this);
		btVol = (Button) mRootView.findViewById(R.id.volume_setting);
		btVol.setOnClickListener(this);
		btEqualizer = (Button) mRootView.findViewById(R.id.eq_setting);
		btEqualizer.setOnClickListener(this);
		btSetting = (Button) mRootView.findViewById(R.id.system_setting);
		btSetting.setOnClickListener(this);
		ctlFmVolume = mRootView.findViewById(R.id.fm_contrl_volume);
		fmVolume = mRootView.findViewById(R.id.id_volume_view);

		showVolume = (TextView) mRootView.findViewById(R.id.id_volume_show);
		radioUnitType = (TextView) mRootView.findViewById(R.id.radio_unit_type);

		mPageLayout = (LinearLayout) mRootView.findViewById(R.id.radio_page_index);//页面指示器父容器
		mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);//单页面容器,同时又是每页6个文本的父布局
		mRadioPage1 = (ImageView) mRootView.findViewById(R.id.page_index_1);//每页对应编号索引，表示第一个小圆点，对应第一页
		mRadioPage2 = (ImageView) mRootView.findViewById(R.id.page_index_2);
		mRadioPage3 = (ImageView) mRootView.findViewById(R.id.page_index_3);
		mRadioPage4 = (ImageView) mRootView.findViewById(R.id.page_index_4);
		mRadioPage5 = (ImageView) mRootView.findViewById(R.id.page_index_5);
		mRadioPage6 = (ImageView) mRootView.findViewById(R.id.page_index_6);

		mViewPager.setAdapter(new CustomViewAdapter());
		mViewPager.setOnPageChangeListener(this);

		View addFmVol = mRootView.findViewById(R.id.all_volume_add_fm);
		View subFmVol = mRootView.findViewById(R.id.all_volume_sub_fm);
		addFmVol.setOnClickListener(this);
		subFmVol.setOnClickListener(this);
		addFmVol.setOnLongClickListener(this);
		subFmVol.setOnLongClickListener(this);
		addFmVol.setOnTouchListener(this);
		subFmVol.setOnTouchListener(this);
		muteFm = mRootView.findViewById(R.id.all_volume_mute);
		muteFm.setOnClickListener(this);
		posFm = 0;
		posAm = 0;

		// 获取记忆的值
//		sp = getActivity().getSharedPreferences("am_fm_number", getActivity().MODE_PRIVATE);
//
//		numAm = sp.getInt("am", 5);
//		numFm = sp.getInt("fm", 5);

		favorbtnDrawable = DialogUtils.getDrawablebyBitmap(R.drawable.radio_stored_n);
		favorbtnDrawable_p = DialogUtils.getDrawablebyBitmap(R.drawable.radio_favor_p);
		btQuitSearch = (Button) mRootView.findViewById(R.id.bt_quit_serach);
		btQuitSearch.setOnClickListener(this);

//		seekabrSearch = (SeekBar) mRootView.findViewById(R.id.eq_seekbar_search);

//		seekabrSearch.setMax(329);

//		seekabrSearch.setOnTouchListener(new View.OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//
//				return true;
//			}
//		});

	}


	private synchronized void startByPage() {

		try {
			initPageViewGroup(favorBand);//根据每页电台数量初始化每页布局显示
			favorTxts = new TextView[] {
					(TextView) mViewContainter.get(0).findViewById(R.id.radio_favor_txt_1),
					(TextView) mViewContainter.get(0).findViewById(R.id.radio_favor_txt_2),
					(TextView) mViewContainter.get(0).findViewById(R.id.radio_favor_txt_3),
					(TextView) mViewContainter.get(0).findViewById(R.id.radio_favor_txt_4),
					(TextView) mViewContainter.get(0).findViewById(R.id.radio_favor_txt_5),
					(TextView) mViewContainter.get(0).findViewById(R.id.radio_favor_txt_6) };
//					mHandler.sendEmptyMessageDelayed(MSG_START_UPDATE_UI, 300);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void initPageViewGroup(int favorBand) {
		if (favorBand == FM_TYPE) {
			int number = posFm;
			if (number <= 6) {
				pageNumFm = 1;
			} else if (number <= 12) {
				pageNumFm = 2;
			} else if (number <= 18) {
				pageNumFm = 3;
			} else if (number <= 24) {
				pageNumFm = 4;
			} else if (number <= 30) {
				pageNumFm = 5;
			} else {
				pageNumFm = 6;
			}
			addFmView(pageNumFm);

		} else if (favorBand == AM_TYPE) {
			int number = posAm + 1;
			if (number <= 6) {
				pageNumAm = 1;
			} else {
				pageNumAm = 2;
			}
			addAmView(pageNumAm);
		}
		mViewPager.getAdapter().notifyDataSetChanged();
	}


	private void addFmView(int numPage) {

		if (mViewContainter.size() == numPage) {
			return;
		}

		switch (numPage) {
			case 1:
				mViewContainter.clear();//页面容器清空
				mViewContainter.add(page1);//页面容器装载第一个页面内含6个文本
				mPageLayout.removeAllViews();//移除全部索引
				mPageLayout.addView(mRadioPage1);//新增第一个索引
				break;
			case 2:
				mViewContainter.clear();
				mViewContainter.add(page1);
				mViewContainter.add(page2);
				mPageLayout.removeAllViews();
				mPageLayout.addView(mRadioPage1);
				mPageLayout.addView(mRadioPage2);
				break;
			case 3:
				mViewContainter.clear();
				mViewContainter.add(page1);
				mViewContainter.add(page2);
				mViewContainter.add(page3);
				mPageLayout.removeAllViews();
				mPageLayout.addView(mRadioPage1);
				mPageLayout.addView(mRadioPage2);
				mPageLayout.addView(mRadioPage3);
				break;
			case 4:
				mViewContainter.clear();
				mViewContainter.add(page1);
				mViewContainter.add(page2);
				mViewContainter.add(page3);
				mViewContainter.add(page4);
				mPageLayout.removeAllViews();
				mPageLayout.addView(mRadioPage1);
				mPageLayout.addView(mRadioPage2);
				mPageLayout.addView(mRadioPage3);
				mPageLayout.addView(mRadioPage4);
				break;
			case 5:
				mViewContainter.clear();
				mViewContainter.add(page1);
				mViewContainter.add(page2);
				mViewContainter.add(page3);
				mViewContainter.add(page4);
				mViewContainter.add(page5);
				mPageLayout.removeAllViews();
				mPageLayout.addView(mRadioPage1);
				mPageLayout.addView(mRadioPage2);
				mPageLayout.addView(mRadioPage3);
				mPageLayout.addView(mRadioPage4);
				mPageLayout.addView(mRadioPage5);
				break;
			case 6:
				mViewContainter.clear();
				mViewContainter.add(page1);
				mViewContainter.add(page2);
				mViewContainter.add(page3);
				mViewContainter.add(page4);
				mViewContainter.add(page5);
				mViewContainter.add(page6);
				mPageLayout.removeAllViews();
				mPageLayout.addView(mRadioPage1);
				mPageLayout.addView(mRadioPage2);
				mPageLayout.addView(mRadioPage3);
				mPageLayout.addView(mRadioPage4);
				mPageLayout.addView(mRadioPage5);
				mPageLayout.addView(mRadioPage6);
				break;
		}

		return;
	}


	private void addAmView(int numPage) {

		if (mViewContainter.size() == numPage) {
			return;
		}

		switch (numPage) {
			case 1:
				mViewContainter.clear();
				mViewContainter.add(page1);
				mPageLayout.removeAllViews();
				mPageLayout.addView(mRadioPage1);
				break;
			case 2:
				mPageLayout.removeAllViews();
				mViewContainter.clear();
				mViewContainter.add(page1);
				mViewContainter.add(page2);
				mPageLayout.addView(mRadioPage1);
				mPageLayout.addView(mRadioPage2);
				break;
		}
		return;
	}


	
	/*@Override
	public void onPause() {
		super.onPause();
		if(mControl != null)
			getActivity().unbindService(connection);
	}*/
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		stopScan();
		mHandler.removeMessages(WHAT_HIDE_VOLUME_BAR);
		mHandler.removeCallbacksAndMessages(null);
		getActivity().unbindService(connection);
	}
	
	private void startService(){
		Intent intent = new Intent(PHONE_APP_SERVICE).setPackage("com.yf.radiophoneapp");
		getActivity().startService(intent);
		getActivity().bindService(intent,connection,Context.BIND_AUTO_CREATE);
	}
	
	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			startService();
		}
	};
	
	ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mControl = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mControl = PhoneAppControl.Stub.asInterface(service);
			try {
				mControl.setListener(CallbackListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	public final static  int WHAT_HIDE_VOLUME_BAR = 0x100;

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		onPageSelected = arg0;
		resetFavorBtns(arg0 * 6);
		updatePageViewSelected(arg0);
	}

	private final int Text_NUMBER = 6; // 每一页的频率个数
	private void resetFavorBtns(int index) {
		initPageViewGroup(favorBand);

		int i = index / Text_NUMBER;


		if (favorBand == AM_TYPE && i > pageNumAm - 1) {
			i = pageNumAm - 1;
		} else if (favorBand == FM_TYPE && i > pageNumFm - 1) {
			i = pageNumFm - 1;
		}

		if (lastViewPagerIndex == i) {
			return;
		}

		lastViewPagerIndex = i;
		mTv1 = (TextView) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_1);
		mTv2 = (TextView) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_2);
		mTv3 = (TextView) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_3);
		mTv4 = (TextView) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_4);
		mTv5 = (TextView) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_5);
		mTv6 = (TextView) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_6);

		favorTxts = new TextView[] { mTv1, mTv2, mTv3, mTv4, mTv5, mTv6 };
		for(int tv = 0; tv < favorTxts.length; tv++){

			favorTxts[tv].setOnClickListener(this);
			favorTxts[tv].setOnLongClickListener(this);
		}

//		Button mButton1 = (Button) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_1);
//		Button mButton2 = (Button) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_2);
//		Button mButton3 = (Button) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_3);
//		Button mButton4 = (Button) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_4);
//		Button mButton5 = (Button) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_5);
//		Button mButton6 = (Button) mViewContainter.get(i).findViewById(R.id.radio_favor_txt_6);



//		mButton1.setOnClickListener(this);
//		mButton2.setOnClickListener(this);
//		mButton3.setOnClickListener(this);
//		mButton4.setOnClickListener(this);
//		mButton5.setOnClickListener(this);
//		mButton6.setOnClickListener(this);
//
//		mButton1.setOnLongClickListener(this);
//		mButton2.setOnLongClickListener(this);
//		mButton3.setOnLongClickListener(this);
//		mButton4.setOnLongClickListener(this);
//		mButton5.setOnLongClickListener(this);
//		mButton6.setOnLongClickListener(this);
//		favorBtns = new Button[] { mButton1, mButton2, mButton3, mButton4, mButton5, mButton6 };
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}


	private void updatePageViewSelected(int item) {

		if (item == 0) {
			mRadioPage1.setSelected(true);
			mRadioPage2.setSelected(false);
			mRadioPage3.setSelected(false);
			mRadioPage4.setSelected(false);
			mRadioPage5.setSelected(false);
			mRadioPage6.setSelected(false);
			mViewPager.setCurrentItem(0);
		} else if (item == 1) {
			mRadioPage1.setSelected(false);
			mRadioPage2.setSelected(true);
			mRadioPage3.setSelected(false);
			mRadioPage4.setSelected(false);
			mRadioPage5.setSelected(false);
			mRadioPage6.setSelected(false);
			mViewPager.setCurrentItem(1);
		} else if (item == 2) {
			mRadioPage1.setSelected(false);
			mRadioPage2.setSelected(false);
			mRadioPage3.setSelected(true);
			mRadioPage4.setSelected(false);
			mRadioPage5.setSelected(false);
			mRadioPage6.setSelected(false);
			mViewPager.setCurrentItem(2);
		} else if (item == 3) {
			mRadioPage1.setSelected(false);
			mRadioPage2.setSelected(false);
			mRadioPage3.setSelected(false);
			mRadioPage4.setSelected(true);
			mRadioPage5.setSelected(false);
			mRadioPage6.setSelected(false);
			mViewPager.setCurrentItem(3);
		} else if (item == 4) {
			mRadioPage1.setSelected(false);
			mRadioPage2.setSelected(false);
			mRadioPage3.setSelected(false);
			mRadioPage4.setSelected(false);
			mRadioPage5.setSelected(true);
			mRadioPage6.setSelected(false);
			mViewPager.setCurrentItem(4);
		} else if (item == 5) {
			mRadioPage1.setSelected(false);
			mRadioPage2.setSelected(false);
			mRadioPage3.setSelected(false);
			mRadioPage4.setSelected(false);
			mRadioPage5.setSelected(false);
			mRadioPage6.setSelected(true);
			mViewPager.setCurrentItem(5);
		}
	}


	private class CustomViewAdapter extends PagerAdapter {

		@Override
		public int getCount() {

			return mViewContainter.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViewContainter.get(position));
			return mViewContainter.get(position);
		}
	}

	private class MyHandler extends  Handler{

		private WeakReference<MainActivity> mWeakReference = null;
		private MainActivity mainActivity;

		public MyHandler(MainActivity mActivity) {
			mWeakReference = new WeakReference<MainActivity>(mActivity);
			if (mWeakReference != null)
				mainActivity = mWeakReference.get();
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case MSG_START_UPDATE_UI:
					updateRadioFavorUI(favorBand);
					break;
				case MSG_CHANGE_AMBAND:
//					mainActivity.setBand(BAND_AM, true);
					break;
				case MSG_RADIO_QUIT:

					new Thread(new Runnable() {

						@Override
						public void run() {

//							stopScan();
							quit();


						}
					}).start();

					getActivity().finish();

					break;

				case WHAT_HIDE_VOLUME_BAR:
					refreshFmVolume(false);
					break;

				case MSG_DELAY_SET_PROGRESS:
					 break;
					default:
						break;

			}
		}
	}

	private void quit() {

		//set sound mute
	}


//	public void stopScan() {
//		Log.i("TAG","stopScan");
//		seekabrSearch.setProgress(0);
//		if(mRootView.findViewById(R.id.eq_layout_search).getVisibility() == View.VISIBLE){
//			mRootView.findViewById(R.id.eq_layout_search).setVisibility(View.GONE);
//		}
//
//		//stopsearch
//	}


	private PhoneAppCallback.Stub CallbackListener = new PhoneAppCallback.Stub() {

		@Override
		public void onFreqChanage(final int freq) throws RemoteException {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					textFreq.setText(formatFreq(freq, curr_radio_type == FM_TYPE));
				}
			});
		}

		@Override
		public void onFavorFreqChange(final byte[] favors) throws RemoteException {
			/*favors[2] 表示频率类型，分别= 0 / 1, 表示FM/AM,
			* favors[3] 表示各个页数，分别＝1-6(FM), =1-2(AM)
			* favors[4]-favors[15]共12个字节分别对应每页的6个电台。如favors[4],favors[5]两个字节组成第一个电台，以此类推.
			* 每一个byte[] favors都包含上述内容，即一个favors对应一页6个电台数据。总计有8页电台收藏数据。
			*
			* eg.以第一页FM 6个频率为例： favors={04 01 00 01   29 FE  29 F5  29 e4  28 a2  28 40  25 49  3E}
			*/

			recvType = favors[2];
			if(recvType == 0){
				switch(favors[3]){
					//解析出 6页FM电台，每页6个频率。
					case 1:
						recvPage1Data = getSinglePageFreq(favors);//保存第一页FM电台数据共6个频率
						Log.i("TAG","the one page fm freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;

					case 2:
						recvPage2Data = getSinglePageFreq(favors);
						Log.i("TAG","the two page fm freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
					case 3:
						recvPage3Data = getSinglePageFreq(favors);
						Log.i("TAG","the three page fm freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
					case 4:
//						recvPage4Data = getSinglePageFreq(favors);
						Log.i("TAG","the four page fm freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
					case 5:
//						recvPage5Data = getSinglePageFreq(favors);
						Log.i("TAG","the five page fm freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
					case 6:
//						recvPage6Data = getSinglePageFreq(favors);
						Log.i("TAG","the six page fm freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
				}

			}else{
				//解析出 2页AM电台，每页6个频率。
				switch(favors[3]){
					case 1:
//						recvPage7Data = getSinglePageFreq(favors);
						Log.i("TAG","the seven page am freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
					case 2:
//						recvPage8Data = getSinglePageFreq(favors);
						Log.i("TAG","the eight page am freq= " +
								favors[4] + "#" + favors[5] + "#" + favors[6] + "#" + favors[7] +"#"+
								favors[8] + "#" + favors[9] + "#" + favors[10] + "#" + favors[11] + "#" +
								favors[12] + "#" + favors[13] + "#" + favors[14] + "#" + favors[15]);
						break;
				}

			}
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					updateFavorsList(recvPage1Data);
				}
			});

		}

		@Override
		public void onPhoneStatusChange(String num) throws RemoteException {

		}

		@Override
		public void onDeviceBondStatusChange(String address, boolean isConnect)
				throws RemoteException {

		}

		@Override
		public void onSourceStatusChange(final int type) throws RemoteException {
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					getCurrSourceType(type);
				}
			});
			
		}

		@Override
		public void onPlayingStatusChange(int type) throws RemoteException {
			
		}

		@Override
		public void onPlayOrderChange(int playOrder) throws RemoteException {

		}

		@Override
		public void onCurrIndexChange(int currIndex) throws RemoteException {

		}

		@Override
		public void onAllIndexChange(int allIndex) throws RemoteException {

		}

		@Override
		public void onCurrPlayTimeChange(long currTime) throws RemoteException {

		}

		@Override
		public void onAllPlayTimeChange(long allTime) throws RemoteException {

		}

		@Override
		public void onCurrUsbMusicName(String usbMusicName) throws RemoteException {

		}

		@Override
		public void onCurrUsbMusicAuthor(String usbMusicAuthor) throws RemoteException {

		}

		@Override
		public void onCurrUsbMusicAlbum(String usbMusicAlbum) throws RemoteException {

		}
	};




	private void getCurrSourceType(int type) {
		if (UrrSourceType.onUrrSourceType!=null)
		UrrSourceType.onUrrSourceType.OnurrSourceTypeListener(type);
		if(type == 2){
			curr_radio_type = FM_TYPE;
			btFmChange.setVisibility(View.VISIBLE);
			btAmChange.setVisibility(View.GONE);
		}
		if(type == 3){
			curr_radio_type = AM_TYPE;
			btFmChange.setVisibility(View.GONE);
			btAmChange.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		myClick(v.getId());
	}


	private void myClick(int id){
		switch (id) {
//newadd
		case R.id.radio_favor_txt_1:
			handlerClickFavorateButton(0);
			break;
		case R.id.radio_favor_txt_2:
			handlerClickFavorateButton(1);
			break;
		case R.id.radio_favor_txt_3:
			handlerClickFavorateButton(2);
			break;
		case R.id.radio_favor_txt_4:
			handlerClickFavorateButton(3);
			break;
		case R.id.radio_favor_txt_5:
			handlerClickFavorateButton(4);
			break;
		case R.id.radio_favor_txt_6:
			handlerClickFavorateButton(5);
			break;

		case R.id.radio_next:
			try {
				mControl.RadioNext();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.radio_pre:
			try {
				mControl.RadioPre();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;

		case R.id.radio_am_band:
		case R.id.radio_fm_band:

			setUiVisibility(true);
//			switchRadioBand();
			try {
				mControl.RadioSetBand();

			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.radio_search:
			try {
				mControl.RadioSearch();//发送搜台命令
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			//newadd

			setUiVisibility(true);
			isTouchClick = false;
//			seekabrSearch.setProgress(0);
//			mHandler.sendEmptyMessageDelayed(MSG_DELAY_SET_PROGRESS, 1000);
			count = 0;
			posAm = 0;
			posFm = 0;
			isflag = 0;

			pageNumAm = 1;
			pageNumFm = 1;

			btFmChange.setVisibility(View.VISIBLE);
			btAmChange.setVisibility(View.GONE);
			fastSearch();

			break;


//		case R.id.bt_quit_serach:
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//
//					stopScan(); // 停止搜索
//
//
//				}
//			}).start();
//
//			setUiVisibility(true);
//			break;

		case R.id.freq_add:
			try {
				mControl.RadioAddOne();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case R.id.freq_sub:
			try {
				mControl.RadioSubOne();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;

		case R.id.volume_setting:
			refreshFmVolume(ctlFmVolume.getVisibility() != View.VISIBLE);
			return;
		case R.id.eq_setting:

			Toast.makeText(getActivity(), "音效设置", Toast.LENGTH_LONG).show();

			break;
		case R.id.system_setting:
			Context context = getActivity();
			Intent intent = new Intent(context,AppSettingView.class);
			context.startActivity(intent);
			break;
		case R.id.all_volume_add_fm:
			changeFmVolume(1);
			return;
		case R.id.all_volume_sub_fm:
			changeFmVolume(-1);
			return;
		case R.id.all_volume_mute:
			switchMuteFm(currVolume);
			return;
		default:
			break;
		}
		refreshFmVolume(false);
	}


	//一键搜台
	private void fastSearch() {
		Log.i("TAG","in fastSearch=======");
		mViewPager.setCurrentItem(0);
	}


	//点击收藏按钮
	private int handlerClickFavorateButton(int num) {
		num += onPageSelected * 6;
		//获取波段内的前6个收藏频率
		int[] favlist = {};

		if (favlist != null && favlist.length != 0 && num < favlist.length) {
			if (favlist[num] == 0) {
				return 0;
			}

			//播放频道
//			playChannal(favlist, num);

		}
		updateFavorateButtonsColor(getPlayingIndex());
		return 0;

	}

	//返回播放索引
	public int getPlayingIndex() {

		int index = 0;
		return index;
	}


	//显示按钮颜色
	private void updateFavorateButtonsColor(int colorIndex) {

		clearFavorateButtonsColor();

		resetFavorBtns(colorIndex);

		updatePageViewSelected(mViewPager.getCurrentItem());

		if (colorIndex != -1) {

			if (favorBand == AM_TYPE && (colorIndex >= 0 && colorIndex < 12)) {
				favorTxts[colorIndex % 6].setBackground(favorbtnDrawable_p);
			} else if (favorBand == FM_TYPE) {
				favorTxts[colorIndex % 6].setBackground(favorbtnDrawable_p);
			}

			if (mViewPager.getCurrentItem() != colorIndex / 6) {
				mViewPager.setCurrentItem(colorIndex / 6);
			}

		} else {

//			for (int i = 0; i < getFreqMax(); i++) {
//				resetFavorBtns(i);
//				favorTxts[i % 6].setBackground(favorbtnDrawable);
//			}
		}
	}

	private void clearFavorateButtonsColor() {

		for (int i = 0; i < getFreqMax(); i++) {
			resetFavorBtns(i);
//			favorTxts[i % 6].setBackground(favorbtnDrawable);

		}
	}

	private int getFreqMax() {

		int maxFm = 6;
		int maxAm = 6;

		if (favorBand == FM_TYPE) {
			int number = posFm ;

			if (number <= 6) {
				maxFm = 6;
			} else if (number <= 12) {
				maxFm = 12;
			} else if (number <= 18) {
				maxFm = 18;
			} else if (number <= 24) {
				maxFm = 24;
			} else if (number <= 30) {
				maxFm = 30;
			} else {
				maxFm = 36;
			}
		} else if (nPreBand == AM_TYPE) {
			int number = posAm + 1;

			if (number <= 6) {
				maxAm = 6;
			} else {
				maxAm = 12;
			}
		}

		return favorBand == 2 ? maxFm : maxAm;
	}

	private void refreshFmVolume(boolean isVisible) {
		Animation animation = null;
		if (isVisible) {

			if (ctlFmVolume.getVisibility() == View.VISIBLE)
				return;
			int volume = 32;
			((AllVolumeView)fmVolume).setVolume(volume);
//			((SeekBar)fmVolume).setProgress(volume);

			showVolume.setText("媒体音量" + volume);
			currVolume = volume;
			mHandler.removeMessages(WHAT_HIDE_VOLUME_BAR);
			mHandler.sendEmptyMessageDelayed(WHAT_HIDE_VOLUME_BAR,HIDE_VOLUME_BAR_TIME);
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.all_volume_ctl_show);
			ctlFmVolume.setVisibility(View.VISIBLE);


		} else {
			if (ctlFmVolume.getVisibility() == View.GONE)
				return;
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.all_volume_ctl_hide);
			animation.setAnimationListener(new HideViewAnimListener(ctlFmVolume));
			if(currVolume == 0){
				btVol.setBackgroundResource(R.drawable.voice_jingyin_h);
			}else{
				btVol.setBackgroundResource(R.drawable.voice_jingyin);
			}
		}
		if (animation != null) {
			animation.setDuration(ANIM_TIME);
			ctlFmVolume.startAnimation(animation);
		}

	}

	private void changeFmVolume(int step) {
		mHandler.removeMessages(WHAT_HIDE_VOLUME_BAR);
		mHandler.sendEmptyMessageDelayed(WHAT_HIDE_VOLUME_BAR, HIDE_VOLUME_BAR_TIME);
		currVolume += step;
		if (currVolume < 0) {
			currVolume = 0;
		}
		if(currVolume >= 32){
			currVolume = 32;
		}

		((AllVolumeView)fmVolume).setVolume(currVolume);
//		((SeekBar)fmVolume).setProgress(currVolume);

		showVolume.setText("媒体音量" + currVolume);
	}

	private void switchMuteFm(int curvol){
			if(curvol > 0){
				showVolume.setText("媒体音量" + 0);
				((AllVolumeView)fmVolume).setVolume(0);
//				((SeekBar)fmVolume).setProgress(0);
				currVolume = 0;
			}else{
				showVolume.setText("媒体音量" + currVolume);
				((AllVolumeView)fmVolume).setVolume(currVolume);
//				((SeekBar)fmVolume).setProgress(currVolume);
			}

	}


	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
			case R.id.all_volume_add_fm: {
				if (timerVolume != null) {
					timerVolume.cancel();
				}

				timerVolume = new Timer();
				timerVolume.schedule(new TimerTask() {
					@Override
					public void run() {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								changeFmVolume(1);
							}
						});
					}
				}, TIMER_DELAY, TIMER_PERIOD);
			}
			break;

			case R.id.all_volume_sub_fm: {
				if (timerVolume != null) {
					timerVolume.cancel();
				}

				timerVolume = new Timer();
				timerVolume.schedule(new TimerTask() {
					@Override
					public void run() {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								changeFmVolume(-1);
							}
						});
					}
				}, TIMER_DELAY, TIMER_PERIOD);
			}
			break;
			default:
				break;
		}

		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
			case R.id.all_volume_add_fm:
			case R.id.all_volume_sub_fm: {
				if (event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_UP
						|| event.getAction() == MotionEvent.ACTION_CANCEL) {
					if (timerVolume != null) {
						timerVolume.cancel();
						timerVolume = null;
					}
				}

			}
			break;
		}

		return false;
	}

	public String formatFreq(int nFreq, boolean bFm) {

		String mStrFreq = "";
		if (bFm) {

			// 防止越界
			if (nFreq > 10800) {
				nFreq = 10800;
			} else if (nFreq < 8750) {
				nFreq = 8750;
			}
			mStrFreq = String.format("%.1f", nFreq / 100.f);
			radioUnitType.setText(R.string.radio_FMtype);
		} else {

			// 防止越界
			if (nFreq > 1620) {
				nFreq = 1620;
			} else if (nFreq < 522) {
				nFreq = 522;
			}

			mStrFreq = String.format("%d", nFreq / 1);
			radioUnitType.setText(R.string.radio_AMtype);
		}

		return mStrFreq;
	}



	int viewIds[] = new int[] {R.id.radio_unit_type, R.id.radio_freq};

	int btIds[] = new int[] { R.id.radio_pre, R.id.radio_next, R.id.radio_search, R.id.freq_sub, R.id.freq_add };


	private void setUiVisibility(boolean isView) {

		if (isView) {
			for (int i = 0, size = viewIds.length; i < size; i++) {
				mRootView.findViewById(viewIds[i]).setVisibility(View.VISIBLE);
			}

			for (int i = 0, size = btIds.length; i < size; i++) {
				mRootView.findViewById(btIds[i]).setEnabled(true);
			}

			mRootView.findViewById(R.id.eq_layout_search).setVisibility(View.GONE);

		} else {
			for (int i = 0, size = viewIds.length; i < size; i++) {
				mRootView.findViewById(viewIds[i]).setVisibility(View.GONE);
			}

			for (int i = 0, size = btIds.length; i < size; i++) {
				mRootView.findViewById(btIds[i]).setEnabled(false);
			}
		}

		return;
	}


	public void updateRadioFavorUI(int favorBand) {
		if (-1 == favorBand) {
			return;
		}
		clearFavorateButtonsColor();
	}


	private void updateFavorsList(int[] datas) {
//		int max = getFreqMax();
		int max = 6;
		if(datas == null || datas.length == 0){
			return;
		}

		Log.i("TAG", "------update favorlist= " + recvType);
		for (int i = 0; i < max; i++) {
			resetFavorBtns(i);
			favorTxts[i % 6].setText(formatFreq(datas[i*2], true));
//			favorTxts[i % 6].setText("22");

		}

	}


	private static String byte2hexbyPos(byte[] buffer, int start, int end){
		String h = "";
		for(int i = start; i < end; i++){
			String temp = Integer.toHexString(buffer[i] & 0xFF);
			if(temp.length() == 1){
				temp = "0" + temp;
			}
			h = h + temp;
		}
		return h;
	}


	private  int[] getSinglePageFreq(byte[] favors) {
		//解析出每页6个频率,如FM第一页6个频率。1.favors[4-5], 2.favors[6-7]，类推
		for (int i = 4; i < 16; i += 2) {
			//i=4,6,8,10,12,14,分别对应1-6个频率。

			byte[] bytetwo = new byte[]{(byte) favors[i], (byte) favors[i + 1]};
			String hexFM = byte2hexbyPos(bytetwo, 0,  2);

			//singlePageFavor[0-2-4-6-8-10]分别对应每页的1-6个频率用于显示。
			singlePageFavor[i-4] = Integer.parseInt(hexFM, 16);
			posFm++;
		}

		return singlePageFavor;//该数组格式为singlePageFavor[0,2,4,6]而不是[0,1,2,3]
	}

}
