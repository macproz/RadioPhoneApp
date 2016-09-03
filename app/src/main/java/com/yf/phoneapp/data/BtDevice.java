package com.yf.phoneapp.data;


import android.os.Parcel;
import android.os.Parcelable;

public class BtDevice implements Parcelable{

	String address;
	String name;
	
	public BtDevice(String address,String name){
		this.address = address;
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "BtDevice [address=" + address + ", name=" + name + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(address);
		dest.writeString(name);
	}
	
	public void readFromParcel(Parcel _reply) {
		this.address = _reply.readString();
		this.name = _reply.readString();
	}
	
	public static final Parcelable.Creator<BtDevice> CREATOR  = new Parcelable.Creator<BtDevice>() {

		@Override
		public BtDevice[] newArray(int size) {
			return new BtDevice[size];
		}
		@Override
		public BtDevice createFromParcel(Parcel source) {
			return new BtDevice(source.readString(), source.readString());
		}
	};

}
