package com.yf.phoneapp.btcontrol;

import java.lang.reflect.Method;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class ClsUtils {
	
	public static boolean createBond(BluetoothDevice btDevice)
			throws Exception {
		Method createBondMethod = btDevice.getClass().getMethod("createBond");
		Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	
	public static boolean removeBond(BluetoothDevice btDevice)
			throws Exception {
		Method removeBondMethod = btDevice.getClass().getMethod("removeBond");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
		return returnValue.booleanValue();
	}
	
	public static boolean connectA2dp(BluetoothDevice btDevice,BluetoothA2dp a2dp)
			throws Exception {
		Method removeBondMethod = a2dp.getClass().getMethod("connect");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(a2dp,btDevice);
		return returnValue.booleanValue();
	}
	
	public static boolean disconnectA2dp(BluetoothDevice btDevice,BluetoothA2dp a2dp)
			throws Exception {
		Method removeBondMethod = a2dp.getClass().getMethod("disconnect");
		Boolean returnValue = (Boolean) removeBondMethod.invoke(a2dp,btDevice);
		return returnValue.booleanValue();
	}


}
