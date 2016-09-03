package com.yf.protocol;

import android.bluetooth.BluetoothDevice;

public interface IDataRecvListener {
   void onDataRecv(BluetoothDevice device, byte[] data, int len);
   void onDeviceConnectStatusChange(BluetoothDevice device,boolean isConnect);
}
