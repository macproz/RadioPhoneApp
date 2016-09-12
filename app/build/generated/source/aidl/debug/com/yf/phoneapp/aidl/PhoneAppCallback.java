/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/jun/mydata/git_local_workps/local_android_proj/RadioPhoneApp/app/src/main/aidl/com/yf/phoneapp/aidl/PhoneAppCallback.aidl
 */
package com.yf.phoneapp.aidl;
public interface PhoneAppCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.yf.phoneapp.aidl.PhoneAppCallback
{
private static final java.lang.String DESCRIPTOR = "com.yf.phoneapp.aidl.PhoneAppCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.yf.phoneapp.aidl.PhoneAppCallback interface,
 * generating a proxy if needed.
 */
public static com.yf.phoneapp.aidl.PhoneAppCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.yf.phoneapp.aidl.PhoneAppCallback))) {
return ((com.yf.phoneapp.aidl.PhoneAppCallback)iin);
}
return new com.yf.phoneapp.aidl.PhoneAppCallback.Stub.Proxy(obj);
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
case TRANSACTION_onFreqChanage:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onFreqChanage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onFavorFreqChange:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
int _arg0_length = data.readInt();
if ((_arg0_length<0)) {
_arg0 = null;
}
else {
_arg0 = new byte[_arg0_length];
}
this.onFavorFreqChange(_arg0);
reply.writeNoException();
reply.writeByteArray(_arg0);
return true;
}
case TRANSACTION_onPhoneStatusChange:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onPhoneStatusChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onDeviceBondStatusChange:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onDeviceBondStatusChange(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onSourceStatusChange:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onSourceStatusChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onPlayingStatusChange:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onPlayingStatusChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onPlayOrderChange:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onPlayOrderChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCurrIndexChange:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onCurrIndexChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAllIndexChange:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onAllIndexChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCurrPlayTimeChange:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
this.onCurrPlayTimeChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAllPlayTimeChange:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
this.onAllPlayTimeChange(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCurrUsbMusicName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onCurrUsbMusicName(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCurrUsbMusicAuthor:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onCurrUsbMusicAuthor(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCurrUsbMusicAlbum:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onCurrUsbMusicAlbum(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.yf.phoneapp.aidl.PhoneAppCallback
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
@Override public void onFreqChanage(int freq) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(freq);
mRemote.transact(Stub.TRANSACTION_onFreqChanage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onFavorFreqChange(byte[] favors) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((favors==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(favors.length);
}
mRemote.transact(Stub.TRANSACTION_onFavorFreqChange, _data, _reply, 0);
_reply.readException();
_reply.readByteArray(favors);
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPhoneStatusChange(java.lang.String num) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(num);
mRemote.transact(Stub.TRANSACTION_onPhoneStatusChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onDeviceBondStatusChange(java.lang.String address, boolean isConnect) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(((isConnect)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onDeviceBondStatusChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSourceStatusChange(int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_onSourceStatusChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPlayingStatusChange(int playStatus) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(playStatus);
mRemote.transact(Stub.TRANSACTION_onPlayingStatusChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPlayOrderChange(int playOrder) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(playOrder);
mRemote.transact(Stub.TRANSACTION_onPlayOrderChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCurrIndexChange(int currIndex) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(currIndex);
mRemote.transact(Stub.TRANSACTION_onCurrIndexChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAllIndexChange(int allIndex) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(allIndex);
mRemote.transact(Stub.TRANSACTION_onAllIndexChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCurrPlayTimeChange(long currTime) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(currTime);
mRemote.transact(Stub.TRANSACTION_onCurrPlayTimeChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAllPlayTimeChange(long allTime) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(allTime);
mRemote.transact(Stub.TRANSACTION_onAllPlayTimeChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCurrUsbMusicName(java.lang.String usbMusicName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(usbMusicName);
mRemote.transact(Stub.TRANSACTION_onCurrUsbMusicName, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCurrUsbMusicAuthor(java.lang.String usbMusicAuthor) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(usbMusicAuthor);
mRemote.transact(Stub.TRANSACTION_onCurrUsbMusicAuthor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCurrUsbMusicAlbum(java.lang.String usbMusicAlbum) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(usbMusicAlbum);
mRemote.transact(Stub.TRANSACTION_onCurrUsbMusicAlbum, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onFreqChanage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onFavorFreqChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onPhoneStatusChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onDeviceBondStatusChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onSourceStatusChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onPlayingStatusChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onPlayOrderChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onCurrIndexChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_onAllIndexChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_onCurrPlayTimeChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_onAllPlayTimeChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_onCurrUsbMusicName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_onCurrUsbMusicAuthor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_onCurrUsbMusicAlbum = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
public void onFreqChanage(int freq) throws android.os.RemoteException;
public void onFavorFreqChange(byte[] favors) throws android.os.RemoteException;
public void onPhoneStatusChange(java.lang.String num) throws android.os.RemoteException;
public void onDeviceBondStatusChange(java.lang.String address, boolean isConnect) throws android.os.RemoteException;
public void onSourceStatusChange(int type) throws android.os.RemoteException;
public void onPlayingStatusChange(int playStatus) throws android.os.RemoteException;
public void onPlayOrderChange(int playOrder) throws android.os.RemoteException;
public void onCurrIndexChange(int currIndex) throws android.os.RemoteException;
public void onAllIndexChange(int allIndex) throws android.os.RemoteException;
public void onCurrPlayTimeChange(long currTime) throws android.os.RemoteException;
public void onAllPlayTimeChange(long allTime) throws android.os.RemoteException;
public void onCurrUsbMusicName(java.lang.String usbMusicName) throws android.os.RemoteException;
public void onCurrUsbMusicAuthor(java.lang.String usbMusicAuthor) throws android.os.RemoteException;
public void onCurrUsbMusicAlbum(java.lang.String usbMusicAlbum) throws android.os.RemoteException;
}
