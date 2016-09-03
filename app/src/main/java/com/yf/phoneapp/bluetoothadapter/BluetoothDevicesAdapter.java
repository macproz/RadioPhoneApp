package com.yf.phoneapp.bluetoothadapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.yf.phoneapp.data.BtDevice;
import com.yf.protocol.BtConnectStatusChange;
import com.yf.radiophoneapp.R;



import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BluetoothDevicesAdapter extends BaseAdapter{

	private List<BluetoothDevice> allDevices = new ArrayList<BluetoothDevice>();
	private Context mContext;
	BtConnectStatusChange listener;
	private BluetoothDevice mRemoteDevice;
	private static int curSelectIndex = 0;
	
	public BluetoothDevicesAdapter(Context context) {
		mContext = context;
		updateDevices(BluetoothAdapter.getDefaultAdapter());
	}
	
	public void setDeviceList(List<BluetoothDevice> Devices){
		allDevices = Devices;
	}
	
	public void setRemoteDevice(BluetoothDevice device){
		mRemoteDevice = device;
	}
	
	private void updateDevices(BluetoothAdapter btAdapter){
		allDevices.clear();
		
		Set<BluetoothDevice> set = btAdapter.getBondedDevices();

		Iterator<BluetoothDevice> i = set.iterator();
		
		while (i.hasNext()){
			
			BluetoothDevice device = i.next();
			
			allDevices.add(device);
		}
	}
	
	public void updateSelectIndex(int pos){
		curSelectIndex = pos;
		notifyDataSetChanged();
	}
	
	public void setBtStatusListener(BtConnectStatusChange mBtConnectStatusChange){
		listener = mBtConnectStatusChange;
	}
	
	
	 public void onChangedConnectState(ViewHolder viewholder,int position) {
	        try {
	        	BluetoothDevice device = (BluetoothDevice) allDevices.get(position);
	            if (null != mRemoteDevice && mRemoteDevice.getAddress().equals(device.getAddress())) {
                	viewholder.btConnect.setVisibility(View.VISIBLE);
                	viewholder.btConnect.setBackgroundResource((R.drawable.set_bt_dev_connect_item_disconnect));
                	viewholder.btConnect.setTag(mRemoteDevice);
	            }else{
	            	viewholder.btConnect.setBackgroundResource(R.drawable.set_bt_dev_connect_item_connect);
                	viewholder.btConnect.setTag(null);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	public List<BluetoothDevice> getDeviceList(){
		return allDevices;
	}
	
	
	
	@Override
	public int getCount() {
		return allDevices.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		if (convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.connect_item_conn, null);
			
			viewholder = new ViewHolder();
			
			viewholder.btConnect = (Button) convertView.findViewById(R.id.id_button_connect);
			viewholder.btDelete = (Button) convertView.findViewById(R.id.id_button_delete);
			viewholder.tvDeviceName = (TextView) convertView.findViewById(R.id.id_text_device_name);
			viewholder.tvConnected = (TextView) convertView.findViewById(R.id.id_text_device_con);
			viewholder.tvUnConDeviceName = (TextView) convertView.findViewById(R.id.id_unconnect_device_name);
			convertView.setTag(viewholder);
		} else 
			viewholder = (ViewHolder) convertView.getTag();
		//判断是否连接
		
		BluetoothDevice device = (BluetoothDevice) allDevices.get(position);
		boolean isConnected = (null != mRemoteDevice && mRemoteDevice.getAddress().equals(device.getAddress()));
		String name = device.getName();
        if (name == null) {
            name = device.getAddress();
        }
        onChangedConnectState(viewholder,position);
        if (curSelectIndex == position) {
        	convertView.setBackgroundResource(R.drawable.icon_n);
            if (isConnected) {
            	viewholder.tvDeviceName.setVisibility(View.VISIBLE);
            	viewholder.tvDeviceName.setText(name);
            	viewholder.tvUnConDeviceName.setVisibility(View.INVISIBLE);
            	viewholder.btConnect.setVisibility(View.VISIBLE);
            	viewholder.btDelete.setVisibility(View.INVISIBLE);
            	viewholder.tvConnected.setVisibility(View.VISIBLE);
            } else {
            	convertView.findViewById(R.id.unconnect_device).setVisibility(View.VISIBLE);
            	viewholder.tvConnected.setVisibility(View.INVISIBLE);
            	viewholder.tvDeviceName.setVisibility(View.INVISIBLE);
            	viewholder.tvUnConDeviceName.setVisibility(View.VISIBLE);
            	viewholder.tvUnConDeviceName.setText(name);
            	
            	if(isConnected)
            	{
            		viewholder.btConnect.setVisibility(View.INVISIBLE);
            	}
            	else
            	{
            		viewholder.btConnect.setVisibility(View.VISIBLE);
            	}
            	viewholder.btDelete.setVisibility(View.VISIBLE);
            }
        }else{
        	convertView.findViewById(R.id.unconnect_device).setVisibility(View.VISIBLE);
        	if(isConnected){
        		viewholder.tvConnected.setVisibility(View.VISIBLE);
        		viewholder.tvDeviceName.setVisibility(View.VISIBLE);
        		viewholder.tvUnConDeviceName.setVisibility(View.INVISIBLE);
        		viewholder.tvDeviceName.setText(name);
        	}
        	else{
        		viewholder.tvDeviceName.setVisibility(View.INVISIBLE);
        		viewholder.tvConnected.setVisibility(View.INVISIBLE);
        		viewholder.tvUnConDeviceName.setVisibility(View.VISIBLE);
        		viewholder.tvUnConDeviceName.setText(name);
        	}
        	
        	convertView.setBackgroundResource(R.drawable.transparent);
        	viewholder.btConnect.setVisibility(View.INVISIBLE);
        	viewholder.btDelete.setVisibility(View.INVISIBLE);
        }
        
        viewholder.btConnect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(v.getTag() != null){
					if(listener != null){
						listener.disConnectBluetooth(position);
					}
				}else {
					if(listener != null){
						listener.ConnectBluetooth(position);
					}
                 }
			}
		});
        
        viewholder.btDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(listener != null){
					listener.delectBluetooth(position);
				}
			}
		});
		
		return convertView;
	}
	
	public class ViewHolder{
		Button btConnect;
		Button btDelete;
		TextView tvDeviceName;
		TextView tvConnected;
		TextView tvUnConDeviceName;
	};

}
