
package com.yf.radiophoneapp;

import java.util.ArrayList;
import java.util.List;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.yf.bluetooth.PhoneActivity;
import com.yf.bluetooth.R;
import com.yf.bluetooth.manager.utils.LogTools;
import com.yf.bluetooth.utils.BluetoothHFPCallDetails;

/**
 * 拨号键盘界面
 * 
 * @author lishanfeng
 * @version 2014-9-19
 */
public class DialKeyboardView extends Page implements OnClickListener {

    private StringBuilder mCallPhoneNo;
    private TextView mCurPhoneNo, devName;
    View rootView;
    public DialKeyboardView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.page_phone, this);
        mCurPhoneNo = (TextView) findViewById(R.id.cur_phone_num);
        mCurPhoneNo.setBackground(getDrawable(R.drawable.keyboard_enter));
        devName = (TextView) findViewById(R.id.cur_dev_name);
        initItemView(R.id.cur_num_0, R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_1,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_2,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_3,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_4,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_5,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_6,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_7,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_8,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_9,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_num_sh,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_clear,R.drawable.cur_btn_bg);
        initItemView(R.id.cur_cal, R.drawable.cur_cal_bg);
        initItemView(R.id.cur_del, R.drawable.cur_del_bg);
        initItemView(R.id.cur_num_st,R.drawable.cur_btn_bg);
        refresh();
    }
    private void initItemView(int id, int drawableId)
    {
    	View v = findViewById(id);
    	v.setOnClickListener(this);
    	v.setBackground(getDrawable(drawableId));
    }
    
    public void recall(){
    	//call(mCallPhoneNo.toString());
    	String phoneNo = "10086";
    	if (phoneNo != null && phoneNo.trim().length() > 0) {
            try {
                if (mYFBTManager.isBluetoothHfpConnected()) {
                    PhoneActivity.saveDialPhoneNum = mCallPhoneNo.toString();
                    mCurPhoneNo.setText("");
                    mCallPhoneNo.setLength(0);
                    mYFBTManager.dialTelephone(phoneNo);

                    BluetoothHFPCallDetails detail = new BluetoothHFPCallDetails();
                    detail.setThisCallStatus(2);
                    detail.setCallDir(0);
                    detail.setCallNumber(phoneNo);
                    detail.setCallMode(0);
                    List<BluetoothHFPCallDetails> list = new ArrayList<BluetoothHFPCallDetails>();
                    list.add(detail);
                    /*Intent intent = new Intent(IBluetoothHfp.ACTION_HFP_CALL_DETAILS_CHANGE);
                    intent.putParcelableArrayListExtra(IBluetoothHfp.EXTRA_HFP_CALL_DETAILS,
                    		(ArrayList<? extends Parcelable>) list);
                    getContext().sendBroadcast(intent);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cur_clear:
                mCurPhoneNo.setText("");
                mCallPhoneNo = new StringBuilder();
                break;
            case R.id.cur_cal:
                call(mCallPhoneNo.toString());
                return;
            case R.id.cur_del:
                if (mCallPhoneNo.length() > 0) {
                    mCallPhoneNo.deleteCharAt(mCallPhoneNo.length() - 1);
                }
                break;
            case R.id.cur_num_st:
                if (mCallPhoneNo.length() < 20) {
                    mCallPhoneNo.append("*");
                }
                break;
            default:
            	for(int i =0; i < ids.length; i++){
            		findViewById(ids[i]).setSelected(false);
            	}
            	
                if (mCallPhoneNo.length() < 20) {
                    mCallPhoneNo.append(((TextView) v).getText());
                }
                break;
        }
        updateShowPhoneNum();
    }
    private void updateShowPhoneNum() {
        if (mCallPhoneNo.length() >= 17) {
            String tmp = mCallPhoneNo.substring(mCallPhoneNo.length() - 14, mCallPhoneNo.length());
            mCurPhoneNo.setText("..." + tmp);
        } else {
            mCurPhoneNo.setText(mCallPhoneNo.toString());
        }
    }
    private void call(String phoneNo) {
        String showNum = mCurPhoneNo.getText().toString();
        LogTools.v(getClass(), "save:" + PhoneActivity.saveDialPhoneNum + ",shlw:" + showNum + ",phoneNo:" + phoneNo);
        if (TextUtils.isEmpty(showNum)) {
            if (PhoneActivity.saveDialPhoneNum != null && PhoneActivity.saveDialPhoneNum.length() > 0) {
                mCallPhoneNo = new StringBuilder(PhoneActivity.saveDialPhoneNum);

                updateShowPhoneNum();
            }
        } else if (phoneNo != null && phoneNo.trim().length() > 0) {
            try {
                if (mYFBTManager.isBluetoothHfpConnected()) {
                    PhoneActivity.saveDialPhoneNum = mCallPhoneNo.toString();
                    mCurPhoneNo.setText("");
                    mCallPhoneNo.setLength(0);
                    mYFBTManager.dialTelephone(phoneNo);

                    BluetoothHFPCallDetails detail = new BluetoothHFPCallDetails();
                    detail.setThisCallStatus(2);
                    detail.setCallDir(0);
                    detail.setCallNumber(phoneNo);
                    detail.setCallMode(0);
                    List<BluetoothHFPCallDetails> list = new ArrayList<BluetoothHFPCallDetails>();
                    list.add(detail);
                    /*Intent intent = new Intent(IBluetoothHfp.ACTION_HFP_CALL_DETAILS_CHANGE);
                    intent.putParcelableArrayListExtra(IBluetoothHfp.EXTRA_HFP_CALL_DETAILS,
                    		(ArrayList<? extends Parcelable>) list);
                    getContext().sendBroadcast(intent);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void refresh() {
        mCallPhoneNo = new StringBuilder();
        mCurPhoneNo.setText("");

        if (devName == null) {
            return;
        }
        BluetoothDevice device = mYFBTManager.getRemoteDevice();
        if (device != null && mYFBTManager.isBluetoothConnected()) {
            // why need get two times?
            device.getName();
            devName.setText(device.getName());
        } else {
            devName.setText("");
        }
    }

    @Override
    public void onResume() {
        
    }

    @Override
    public void onDestory() {
        
    }

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
	public void onYfKeyUp(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			selectIndex -=2;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			selectIndex ++;
			if(selectIndex < 0)
				selectIndex = ids.length-1;
			else if(selectIndex >= ids.length)
				selectIndex = 0;
			for(int i = 0; i < ids.length; i++)
			{
				findViewById(ids[i]).setSelected(i == selectIndex);
			}
			break;
		case KeyEvent.KEYCODE_ENTER:
			if(selectIndex >=0 && selectIndex <ids.length)
			{
				onClick(findViewById(ids[selectIndex]));
			}
			break;
		}
	}

}
