/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/jun/mydata/git_local_workps/local_android_proj/RadioPhoneApp/app/src/main/aidl/com/yf/phoneapp/aidl/PhoneAppControl.aidl
 */
package com.yf.phoneapp.aidl;
public interface PhoneAppControl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.yf.phoneapp.aidl.PhoneAppControl
{
private static final java.lang.String DESCRIPTOR = "com.yf.phoneapp.aidl.PhoneAppControl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.yf.phoneapp.aidl.PhoneAppControl interface,
 * generating a proxy if needed.
 */
public static com.yf.phoneapp.aidl.PhoneAppControl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.yf.phoneapp.aidl.PhoneAppControl))) {
return ((com.yf.phoneapp.aidl.PhoneAppControl)iin);
}
return new com.yf.phoneapp.aidl.PhoneAppControl.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_ConnectDevice:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.ConnectDevice(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setListener:
{
data.enforceInterface(DESCRIPTOR);
com.yf.phoneapp.aidl.PhoneAppCallback _arg0;
_arg0 = com.yf.phoneapp.aidl.PhoneAppCallback.Stub.asInterface(data.readStrongBinder());
this.setListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeListener:
{
data.enforceInterface(DESCRIPTOR);
com.yf.phoneapp.aidl.PhoneAppCallback _arg0;
_arg0 = com.yf.phoneapp.aidl.PhoneAppCallback.Stub.asInterface(data.readStrongBinder());
this.removeListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_RadioNext:
{
data.enforceInterface(DESCRIPTOR);
this.RadioNext();
reply.writeNoException();
return true;
}
case TRANSACTION_RadioPre:
{
data.enforceInterface(DESCRIPTOR);
this.RadioPre();
reply.writeNoException();
return true;
}
case TRANSACTION_RadioSetBand:
{
data.enforceInterface(DESCRIPTOR);
this.RadioSetBand();
reply.writeNoException();
return true;
}
case TRANSACTION_RadioSearch:
{
data.enforceInterface(DESCRIPTOR);
this.RadioSearch();
reply.writeNoException();
return true;
}
case TRANSACTION_RadioAddOne:
{
data.enforceInterface(DESCRIPTOR);
this.RadioAddOne();
reply.writeNoException();
return true;
}
case TRANSACTION_RadioSubOne:
{
data.enforceInterface(DESCRIPTOR);
this.RadioSubOne();
reply.writeNoException();
return true;
}
case TRANSACTION_RadioDefaultFreqSet:
{
data.enforceInterface(DESCRIPTOR);
this.RadioDefaultFreqSet();
reply.writeNoException();
return true;
}
case TRANSACTION_getFMFavorite:
{
data.enforceInterface(DESCRIPTOR);
this.getFMFavorite();
reply.writeNoException();
return true;
}
case TRANSACTION_getAMFavorite:
{
data.enforceInterface(DESCRIPTOR);
this.getAMFavorite();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbNext:
{
data.enforceInterface(DESCRIPTOR);
this.UsbNext();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbPre:
{
data.enforceInterface(DESCRIPTOR);
this.UsbPre();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbViewShow:
{
data.enforceInterface(DESCRIPTOR);
this.UsbViewShow();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbPlayAndPause:
{
data.enforceInterface(DESCRIPTOR);
this.UsbPlayAndPause();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbPlayOrder:
{
data.enforceInterface(DESCRIPTOR);
this.UsbPlayOrder();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbPlaySingle:
{
data.enforceInterface(DESCRIPTOR);
this.UsbPlaySingle();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbPlayRandom:
{
data.enforceInterface(DESCRIPTOR);
this.UsbPlayRandom();
reply.writeNoException();
return true;
}
case TRANSACTION_UsbPlayList:
{
data.enforceInterface(DESCRIPTOR);
this.UsbPlayList();
reply.writeNoException();
return true;
}
case TRANSACTION_BtMusicViewShow:
{
data.enforceInterface(DESCRIPTOR);
this.BtMusicViewShow();
reply.writeNoException();
return true;
}
case TRANSACTION_BtMusicPre:
{
data.enforceInterface(DESCRIPTOR);
this.BtMusicPre();
reply.writeNoException();
return true;
}
case TRANSACTION_BtMusicNext:
{
data.enforceInterface(DESCRIPTOR);
this.BtMusicNext();
reply.writeNoException();
return true;
}
case TRANSACTION_BtMusicPlayAndPause:
{
data.enforceInterface(DESCRIPTOR);
this.BtMusicPlayAndPause();
reply.writeNoException();
return true;
}
case TRANSACTION_getBtMusicStatus:
{
data.enforceInterface(DESCRIPTOR);
this.getBtMusicStatus();
reply.writeNoException();
return true;
}
case TRANSACTION_getBtMusicId3Info:
{
data.enforceInterface(DESCRIPTOR);
this.getBtMusicId3Info();
reply.writeNoException();
return true;
}
case TRANSACTION_getCurrPageShow:
{
data.enforceInterface(DESCRIPTOR);
this.getCurrPageShow();
reply.writeNoException();
return true;
}
case TRANSACTION_getUsbStatus:
{
data.enforceInterface(DESCRIPTOR);
this.getUsbStatus();
reply.writeNoException();
return true;
}
case TRANSACTION_getUsbId3Info:
{
data.enforceInterface(DESCRIPTOR);
this.getUsbId3Info();
reply.writeNoException();
return true;
}
case TRANSACTION_getConnectCarDevice:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getConnectCarDevice();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_createBluetoothBond:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.createBluetoothBond(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_disconnectBluetoothBond:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.disconnectBluetoothBond(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getBluetoothEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getBluetoothEnabled();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_startDiscovery:
{
data.enforceInterface(DESCRIPTOR);
this.startDiscovery();
reply.writeNoException();
return true;
}
case TRANSACTION_getBtDeviceList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.yf.phoneapp.data.BtDevice> _result = this.getBtDeviceList();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_removeBond:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.removeBond(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getRemoteDevice:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _result = this.getRemoteDevice();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.yf.phoneapp.aidl.PhoneAppControl
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void ConnectDevice(java.lang.String device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(device);
mRemote.transact(Stub.TRANSACTION_ConnectDevice, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setListener(com.yf.phoneapp.aidl.PhoneAppCallback listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeListener(com.yf.phoneapp.aidl.PhoneAppCallback listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioNext() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioNext, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioPre() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioPre, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioSetBand() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioSetBand, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioSearch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioSearch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioAddOne() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioAddOne, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioSubOne() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioSubOne, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RadioDefaultFreqSet() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_RadioDefaultFreqSet, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getFMFavorite() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFMFavorite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getAMFavorite() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAMFavorite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbNext() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbNext, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbPre() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbPre, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbViewShow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbViewShow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbPlayAndPause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbPlayAndPause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbPlayOrder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbPlayOrder, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbPlaySingle() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbPlaySingle, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbPlayRandom() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbPlayRandom, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UsbPlayList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_UsbPlayList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void BtMusicViewShow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_BtMusicViewShow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void BtMusicPre() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_BtMusicPre, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void BtMusicNext() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_BtMusicNext, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void BtMusicPlayAndPause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_BtMusicPlayAndPause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getBtMusicStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBtMusicStatus, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getBtMusicId3Info() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBtMusicId3Info, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getCurrPageShow() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrPageShow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getUsbStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUsbStatus, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void getUsbId3Info() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUsbId3Info, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getConnectCarDevice() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getConnectCarDevice, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void createBluetoothBond(java.lang.String address) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
mRemote.transact(Stub.TRANSACTION_createBluetoothBond, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void disconnectBluetoothBond(java.lang.String address) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
mRemote.transact(Stub.TRANSACTION_disconnectBluetoothBond, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getBluetoothEnabled() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBluetoothEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void startDiscovery() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startDiscovery, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<com.yf.phoneapp.data.BtDevice> getBtDeviceList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.yf.phoneapp.data.BtDevice> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBtDeviceList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.yf.phoneapp.data.BtDevice.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void removeBond(java.lang.String address) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
mRemote.transact(Stub.TRANSACTION_removeBond, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public android.bluetooth.BluetoothDevice getRemoteDevice() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.bluetooth.BluetoothDevice _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRemoteDevice, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_ConnectDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_removeListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_RadioNext = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_RadioPre = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_RadioSetBand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_RadioSearch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_RadioAddOne = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_RadioSubOne = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_RadioDefaultFreqSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getFMFavorite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getAMFavorite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_UsbNext = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_UsbPre = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_UsbViewShow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_UsbPlayAndPause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_UsbPlayOrder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_UsbPlaySingle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_UsbPlayRandom = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_UsbPlayList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_BtMusicViewShow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_BtMusicPre = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_BtMusicNext = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_BtMusicPlayAndPause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_getBtMusicStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_getBtMusicId3Info = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getCurrPageShow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getUsbStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_getUsbId3Info = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_getConnectCarDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_createBluetoothBond = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_disconnectBluetoothBond = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_getBluetoothEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_startDiscovery = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_getBtDeviceList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_removeBond = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_getRemoteDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
}
public void ConnectDevice(java.lang.String device) throws android.os.RemoteException;
public void setListener(com.yf.phoneapp.aidl.PhoneAppCallback listener) throws android.os.RemoteException;
public void removeListener(com.yf.phoneapp.aidl.PhoneAppCallback listener) throws android.os.RemoteException;
public void RadioNext() throws android.os.RemoteException;
public void RadioPre() throws android.os.RemoteException;
public void RadioSetBand() throws android.os.RemoteException;
public void RadioSearch() throws android.os.RemoteException;
public void RadioAddOne() throws android.os.RemoteException;
public void RadioSubOne() throws android.os.RemoteException;
public void RadioDefaultFreqSet() throws android.os.RemoteException;
public void getFMFavorite() throws android.os.RemoteException;
public void getAMFavorite() throws android.os.RemoteException;
public void UsbNext() throws android.os.RemoteException;
public void UsbPre() throws android.os.RemoteException;
public void UsbViewShow() throws android.os.RemoteException;
public void UsbPlayAndPause() throws android.os.RemoteException;
public void UsbPlayOrder() throws android.os.RemoteException;
public void UsbPlaySingle() throws android.os.RemoteException;
public void UsbPlayRandom() throws android.os.RemoteException;
public void UsbPlayList() throws android.os.RemoteException;
public void BtMusicViewShow() throws android.os.RemoteException;
public void BtMusicPre() throws android.os.RemoteException;
public void BtMusicNext() throws android.os.RemoteException;
public void BtMusicPlayAndPause() throws android.os.RemoteException;
public void getBtMusicStatus() throws android.os.RemoteException;
public void getBtMusicId3Info() throws android.os.RemoteException;
public void getCurrPageShow() throws android.os.RemoteException;
public void getUsbStatus() throws android.os.RemoteException;
public void getUsbId3Info() throws android.os.RemoteException;
public boolean getConnectCarDevice() throws android.os.RemoteException;
public void createBluetoothBond(java.lang.String address) throws android.os.RemoteException;
public void disconnectBluetoothBond(java.lang.String address) throws android.os.RemoteException;
public boolean getBluetoothEnabled() throws android.os.RemoteException;
public void startDiscovery() throws android.os.RemoteException;
public java.util.List<com.yf.phoneapp.data.BtDevice> getBtDeviceList() throws android.os.RemoteException;
public void removeBond(java.lang.String address) throws android.os.RemoteException;
public android.bluetooth.BluetoothDevice getRemoteDevice() throws android.os.RemoteException;
}
