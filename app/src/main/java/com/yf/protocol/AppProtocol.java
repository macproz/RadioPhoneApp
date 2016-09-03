package com.yf.protocol;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class AppProtocol {
	private static final String TAG = "AppProtocol";
	/***
	 * 同步字 + 长度字  + 控制字   + 信息字 + 校验字
	 * 同步字:两个字节，分别 为 'Y' 'F'
	 * 长度字 : 4个字节，为 信息字 和控制字的长度  
	 * 控制字: 1个字节， 为 信息字的标签
	 * 信息字： 数据信息
	 * 校验字 : 1个字节，为 ∑同步字 +长度字+控制字+信息字
	 */
	private static final int SYNC_1 = 1;
	
	private static final int SYNC_2 = 'F';

	private static final int RETURN_BT_STATUS = 2;
	private static final int RETURN_USB_STATUS = 3;
	private static final int RETURN_RADIO_STATUS = 4;

	private  int BT_PLAY_STATE;
	private  int BT_PLAY_ORDER;
	private static final int BT_STATUS_STOPED = 0x0;
	private static final int BT_STATUS_PLAYING = 0x1;
	private static final int BT_STATUS_PAUSED = 0x2;
	private static final int BT_STATUS_FWD_SEEK = 0x3;
	private static final int BT_STATUS_REV_SEEK = 0x4;
	private static final int BT_STATUS_ERROR = 0xf;

	private static final int BT_ORDER_NOTSUPPORT = 0x0;
	private static final int BT_ORDER_PALY = 0x1;
	private static final int BT_RANDOM_PALY = 0x2;
	private static final int BT_SINGLE_LOOP = 0x3;
	private static final int BT_LIST_LOOP = 0x4;

	private  int USB_PLAY_STATE;
	private  int USB_PLAY_ORDER;


	
	private static final int PHONE = 0x1;
	
	private static final int FM = 0x2;
	
	private static final int AM = 0x3;
	
	private static final int USB = 0x4;
	
	private static final int BLUETOOTH = 0x5;
	
	
//	private static final int SYNC_WORD_SIZE = 2;
//	
//	private static final int LENGTH_WORD_SIZE = 4;
//	
//	private static final int CONTROL_WORD_SIZE = 1;
//	
//	private static final int CHECK_WORD_SIZE = 1;
//	
//	private static final int LENGTH_WORD_POS = 0 + SYNC_WORD_SIZE;
//	
//	private static final int CONTROL_WORD_POS = LENGTH_WORD_POS + LENGTH_WORD_SIZE;
//	
//	private static final int INFO_WORD_POS = CONTROL_WORD_POS + CONTROL_WORD_SIZE;
	
	/***
	 * 控制字 协议表
	 * 
	 */
	public static final int REQUEST_RADIO_CONTROL = 1;
	
	public static final int REQUEST_USB_CONTROL = 2;
	
	public static final int REQUEST_BTMUSIC_CONTROL = 3;
	
	public static final int REQUEST_VOLUME_CONTROL = 4;
	
	public static final int REQUEST_VOLUME_MUTE_CONTROL = 5;

	public static final int REQUEST_FM_FAVOR_CONTROL = 8;//一次性读取全部收藏电台
	public static final int REQUEST_AM_FAVOR_CONTROL = 2;
	
	public static final byte SET_RADIO_FREQ_VALUE = 1;

	public static final byte SET_RADIO_FREQ_NEXT = 2;
	
	public static final byte SET_RADIO_FREQ_PRE = 3;
	
	public static final byte SET_RADIO_BAND = 4;

	public static final byte SET_FM_FAVOR = 8;//直接一次性读取FM/AM全部收藏电台

	public static final byte SET_AM_FAVOR = 2;


	public static final byte SET_ONEKEY_SEARCH = 0x05;
	public static final byte SET_ADDONE_FREQ = 0x06;
	public static final byte SET_SUBONE_FREQ = 0x07;



	public static final byte FREQ_HIGH = 0x22;

	public static final byte FREQ_LOW = 0x2e;

	public static final byte SET_FM_FAVOR_CHECK = 0x15;
	public static final byte SET_AM_FAVOR_CHECK = 0x10;

	public static final byte SET_RADIO_FREQ_NEXT_CHECK = 0x0a;
	public static final byte SET_RADIO_FREQ_PRE_CHECK = 0x0b;
	public static final byte SET_RADIO_BAND_CHECK = 0x08 + SET_RADIO_BAND ;
	public static final byte SET_ONEKEY_SEARCH_CHECK = 0x08 + SET_ONEKEY_SEARCH;
	public static final byte SET_ADDONE_FREQ_CHECK = 0x08 + SET_ADDONE_FREQ;
	public static final byte SET_SUBONE_FREQ_CHECK = 0x08 + SET_SUBONE_FREQ;


	public static final byte SET_USB_PLAY_CHECK = 0x0a;
	public static final byte SET_USB_PAUSE_CHECK = 0x0b;
	public static final byte SET_USB_PLAY_NEXT_CHECK = 0x0c;
	public static final byte SET_USB_PLAY_PRE_CHECK = 0x0d;
	public static final byte SET_USB_PLAY_ORDER_CHECK = 0x0e;
	public static final byte SET_USB_PLAY_RANDOM_CHECK = 0x0f;
	public static final byte SET_USB_PLAY_SINGLE_CHECK = 0x10;
	public static final byte SET_USB_PLAY_LIST_CHECK = 0x11;

	public static final byte SET_BTMUSIC_PLAY_CHECK = 0x0b;
	public static final byte SET_BTMUSIC_PAUSE_CHECK = 0x0c;
	public static final byte SET_BTMUSIC_NEXT_CHECK = 0x0d;
	public static final byte SET_BTMUSIC_PRE_CHECK = 0x0e;


	public static final byte defaultFreq = (byte) (AppProtocol.FREQ_HIGH + AppProtocol.FREQ_LOW);
	public static final byte SET_RADIO_DEFAULT_FREQ_CHECK = 0x0b + defaultFreq;


	public static final byte SET_CURR_PAGE_SHOW = 0x1;
	public static final byte SET_CURR_PAGE_SHOW_CHECK = 0x06;


	//for read usb playstatus
	public static final byte SET_USB_STATUS = 0x1;
	public static final byte SET_USB_STATUS_CHECK = 0x0a;
	public static final byte SET_BT_STATUS_CHECK = 0x09;

	public static final byte SET_USB_ID3_INFO = 0x05;
	public static final byte SET_USB_ID3_CHECK = 0x0e;
	public static final byte SET_BT_ID3_CHECK = 0x0d;

	
	public static final byte SET_USB_PALY = 1;
	
	public static final byte SET_USB_PAUSE = 2;

	public static final short SET_USB_PLAY_NEXT = 3;
	
	public static final byte SET_USB_PLAY_PRE = 4;

	public static final byte SET_USB_PLAY_ORDER = 5;

	public static final byte SET_USB_PLAY_RANDOM = 6;

	public static final short SET_USB_PLAY_SINGLE = 7;

	public static final byte  SET_USB_PLAY_LIST = 8;

	
    public static final short SET_BT_MUSIC_PLAY = 1;
    
    public static final short SET_BT_MUSIC_PAUSE = 2;
    
    public static final short SET_BT_MUSIC_PLAY_NEXT = 3;

    public static final short SET_BT_MUSIC_PLAY_PRE = 4;
    
    
    public static final short SET_VOLUME_VALUE = 11;
    
    public static final short SET_VOLUME_MUTE_UNMUTE = 0;

    public static final short SET_VOLUME_MUTE_MUTE = 1;

	private byte[] FIRST_SHOW_CONTROL = new byte[] {0x02};

	private byte[] USB_PLAY_STATUS_CONTROL = new byte[]{0x02, 0x03};

	private byte[] BT_PLAY_STATUS_CONTROL = new byte[]{0x02, 0x02};
    
    private byte[] RADIO_CONTROL = new byte[] {0x03,0x01};

	private byte[] FAVOR_FM_RADIO_CONTROL = new byte[]{0x02, 0x04, 0x01, 0x0};
	private byte[] FAVOR_AM_RADIO_CONTROL = new byte[]{0x02, 0x04, 0x01, 0x1};

    
    private byte[] USB_CONTROL = new byte[] {0x03,0x02};
    
    private byte[] BT_MUSIC_CONTROL = new byte[] {0x03,0x03};
    
    private byte[] VOLUME_SETTING = new byte[] {0x03,0x04};
    
    private byte[] VOLUME_MUTE = new byte[] {0x03,0x05};

    private byte[][] CONTROLALL = new byte[][]{RADIO_CONTROL,USB_CONTROL,
    		BT_MUSIC_CONTROL,VOLUME_SETTING,VOLUME_MUTE};
    
    private int REC_PLAY_STATUS_LENGTH = 3;
    
    private static final String tag = "Protocol";
    
    private static final boolean DEBUG = false;
	
	private IDispatch dispatcher;
	private String name;
	private final static long diffTime = 57600000;
	private int curr= -1;

	private static boolean isSameSong = false;
	public AppProtocol(IDispatch dispather) {
		this.dispatcher = dispather;
	}


	public AppProtocol(){

	}
//	public void setDispatcher(IDispatch dispatch){
//		
//		this.dispatcher = dispatch;
//	}
	
//	private int getSizeFromInfosLength(int infosLen){
//		
//		return SYNC_WORD_SIZE + LENGTH_WORD_SIZE + CONTROL_WORD_SIZE + infosLen + CHECK_WORD_SIZE;
//	}
//	
//	private int getLengthFromInfosLength(int infosLen){
//		
//		return CONTROL_WORD_SIZE + infosLen;
//	}
	
	private int fourByteToInt(byte b1, byte b2, byte b3, byte b4){
		
		 int value= 0;
		 value +=  (b1 & 0x000000FF) << 24;
		 value +=  (b2 & 0x000000FF) << 16;
		 value +=  (b3 & 0x000000FF) << 8;
		 value +=  (b4 & 0x000000FF) ;
	     return value;
		
	}

	//int tranfer to byte[] high at front, low at behind
	public static byte[] intToBytesH(int i) {
		  byte[] result = new byte[4];   
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
		}

	//bytes[] tranfer to int  high at front, low at behind
	public static int bytesToIntH(byte[] src, int offset){
		int value ;
		value = (int) ( ((src[offset] & 0xFF)<<24)
				|((src[offset+1] & 0xFF)<<16)
				|((src[offset+2] & 0xFF)<<8)
				|(src[offset+3] & 0xFF));
		return value;

	}
	
	private byte getCheckSumFromDataPackage(byte[] data, int len){
		
		byte checkSum = 0;
		
		for (int index = 0; index < len; index ++)
			checkSum += data[index];
		
		return checkSum;
	}
	
	public byte[] packCmd(int control, byte[] infos, int infosLen){
		byte[] buffer = new byte[infosLen];
		if(infosLen > 0){
			/*System.arraycopy(CONTROL[control - 1], 0, buffer, 0 , CONTROL[control - 1].length);
			System.arraycopy(infos, 0, buffer,CONTROL[control - 1].length, infosLen - CONTROL[control - 1].length);*/
			switch (control) {
			case REQUEST_RADIO_CONTROL:
				System.arraycopy(RADIO_CONTROL, 0, buffer, 0 , RADIO_CONTROL.length);
				System.arraycopy(infos, 0, buffer,RADIO_CONTROL.length, infosLen - RADIO_CONTROL.length);

				break;
			case REQUEST_USB_CONTROL:
				System.arraycopy(USB_CONTROL, 0, buffer, 0 , USB_CONTROL.length);
				System.arraycopy(infos, 0, buffer,USB_CONTROL.length, infosLen - USB_CONTROL.length);
				break;
			case REQUEST_BTMUSIC_CONTROL:
				System.arraycopy(BT_MUSIC_CONTROL, 0, buffer, 0 , BT_MUSIC_CONTROL.length);
				System.arraycopy(infos, 0, buffer,BT_MUSIC_CONTROL.length, infosLen - BT_MUSIC_CONTROL.length);
				break;
			case REQUEST_VOLUME_CONTROL:
				System.arraycopy(VOLUME_SETTING, 0, buffer, 0 , VOLUME_SETTING.length);
				System.arraycopy(infos, 0, buffer,VOLUME_SETTING.length, infosLen - VOLUME_SETTING.length);
				break;
			case REQUEST_VOLUME_MUTE_CONTROL:
				break;
			default:
				break;
			}
		}
		
		return buffer;
	}

	//封装手机读取当前显示的数据
	public byte[] packFirstShowCmd(int control, byte[] infos, int infosLen){
		byte[] buffer = new byte[infosLen];
		if(infosLen > 0){
			switch (control) {
				case REQUEST_RADIO_CONTROL:
					System.arraycopy(FIRST_SHOW_CONTROL, 0, buffer, 0 , FIRST_SHOW_CONTROL.length);
					System.arraycopy(infos, 0, buffer,FIRST_SHOW_CONTROL.length, infosLen - FIRST_SHOW_CONTROL.length);
					break;
				default:
					break;
			}
		}

		return buffer;
	}

	//封装读取当前USB状态数据
	public byte[] packUsbPlayStatus(int control, byte[] infos, int infosLen){
		byte[] buffer = new byte[infosLen];
		if(infosLen > 0){
			switch (control) {
				case REQUEST_RADIO_CONTROL:
					System.arraycopy(USB_PLAY_STATUS_CONTROL, 0, buffer, 0 , USB_PLAY_STATUS_CONTROL.length);
					System.arraycopy(infos, 0, buffer,USB_PLAY_STATUS_CONTROL.length, infosLen - USB_PLAY_STATUS_CONTROL.length);
					break;

				case REQUEST_VOLUME_MUTE_CONTROL:
					System.arraycopy(USB_PLAY_STATUS_CONTROL, 0, buffer, 0 , USB_PLAY_STATUS_CONTROL.length);
					System.arraycopy(infos, 0, buffer,USB_PLAY_STATUS_CONTROL.length, infosLen - USB_PLAY_STATUS_CONTROL.length);
					break;
				default:
					break;
			}
		}

		return buffer;
	}



	//封装读取当前BT状态数据
	public byte[] packBtPlayStatus(int control, byte[] infos, int infosLen){
		byte[] buffer = new byte[infosLen];
		if(infosLen > 0){
			switch (control) {
				case REQUEST_RADIO_CONTROL:
					System.arraycopy(BT_PLAY_STATUS_CONTROL, 0, buffer, 0 , BT_PLAY_STATUS_CONTROL.length);
					System.arraycopy(infos, 0, buffer,BT_PLAY_STATUS_CONTROL.length, infosLen - BT_PLAY_STATUS_CONTROL.length);
					break;

				case REQUEST_VOLUME_MUTE_CONTROL:
					System.arraycopy(BT_PLAY_STATUS_CONTROL, 0, buffer, 0 , BT_PLAY_STATUS_CONTROL.length);
					System.arraycopy(infos, 0, buffer,BT_PLAY_STATUS_CONTROL.length, infosLen - BT_PLAY_STATUS_CONTROL.length);
					break;
				default:
					break;
			}
		}
		return buffer;
	}


	//封装读取FM/AM收藏频率
	public byte[] packFavorRadio(int control, byte[] infos, int infosLen){
		byte[] buffer = new byte[infosLen];
		if(infosLen > 0){
			switch (control) {
				case REQUEST_FM_FAVOR_CONTROL:
					System.arraycopy(FAVOR_FM_RADIO_CONTROL, 0, buffer, 0 , FAVOR_FM_RADIO_CONTROL.length);
					System.arraycopy(infos, 0, buffer,FAVOR_FM_RADIO_CONTROL.length, infosLen - FAVOR_FM_RADIO_CONTROL.length);
					break;

				case REQUEST_AM_FAVOR_CONTROL:
					System.arraycopy(FAVOR_AM_RADIO_CONTROL, 0, buffer, 0 , FAVOR_AM_RADIO_CONTROL.length);
					System.arraycopy(infos, 0, buffer,FAVOR_AM_RADIO_CONTROL.length, infosLen - FAVOR_AM_RADIO_CONTROL.length);
					break;
				default:
					break;
			}
		}
		return buffer;
	}


	//封装设置默认频点数据
	public byte[] packDefaultFreqCmd(int control, byte[] infos, int infosLen){
		byte[] buffer = new byte[infosLen];
		if(infosLen > 0){
			switch (control) {
				case REQUEST_RADIO_CONTROL:
					System.arraycopy(RADIO_CONTROL, 0, buffer, 0 , RADIO_CONTROL.length);
					System.arraycopy(infos, 0, buffer,RADIO_CONTROL.length, infosLen - RADIO_CONTROL.length);
					break;
				default:
					break;
			}
		}

		return buffer;
	}

	public String unpackArray(byte[] Num,int len){
		StringBuilder bulder = new StringBuilder();
		
		for(int i = 2 ; i < len ; i++){
			bulder.append(Integer.toHexString((Num[i] & 0xFF)));
		}
		
		return bulder.toString();
	}


//	public String unpackArrayById(byte[] Num, int from, int to){
//		StringBuilder bulder = new StringBuilder();
//
//		for(int i = from ; i < to ; i++){
//			bulder.append(Integer.toHexString(Num[i] & 0xFF));
//		}
//		return bulder.toString();
//	}

	//按高位在前低位在后顺序解析byte[]为16进制字符串
	private static String byte2hexbyId(byte[] buffer, int start, int end){
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




	private String unpackPhone(byte[] phoneNum,int len){
		return unpackArray(phoneNum, len);
	}

	//解析USB音乐ID3信息
	private String unpackUsbId3(byte[] usbId3,int len){

		StringBuffer buffershow=new StringBuffer();

        for(int i=4;i<usbId3.length;i++){
            if(usbId3[i]!= 0x0){
                try {
                    String h=new String(new byte[]{(byte) usbId3[i],(byte) usbId3[i+1]},"gbk");
                    if(h.matches("[\u4e00-\u9fff]")){
                        i++;
                        buffershow.append(h);
                    }else{
                        String e=new String(new byte[]{(byte)usbId3[i]},"gbk");
                        buffershow.append(e);
					}
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                break;
            }
        }

		return buffershow.toString();
	}

	//解析BT音乐ID3信息
	private String unpackBtMusicId3(byte[] btMusicId3,int len){

		StringBuffer btshow=new StringBuffer();

		for(int i=2; i<btMusicId3.length; i++){
			if(btMusicId3[i]!= 0x0){
				try {
					String h=new String(new byte[]{(byte) btMusicId3[i],(byte) btMusicId3[i+1]},"gbk");
					if(h.matches("[\u4e00-\u9fff]")){
						i++;
						btshow.append(h);
					}else{
						String e=new String(new byte[]{(byte)btMusicId3[i]},"gbk");
						btshow.append(e);
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				break;
			}
		}

		return btshow.toString();
	}


	public int unpackRadio(byte[] FMNum){

		return Integer.parseInt(byte2hexbyId(FMNum, 3 , 5), 16);
	}
	
	private void unpackReturnShow( byte[] pkg, int len){
			if(len > REC_PLAY_STATUS_LENGTH)
				dispatcher.onCurSourceChange(pkg[1]);
			switch (pkg[1]) {
			case PHONE:
				dispatcher.onPhoneStatusChange(unpackPhone(pkg, len));
				break;
			case FM:
			case AM:
				dispatcher.onRadioFreqChange(unpackRadio(pkg));
				break;
			case USB:
				dispatcher.onCurrPlayIndexChange(unpackPlayIndex(pkg));
				dispatcher.onPlayAllIndexChange(unpackAllIndex(pkg));
				dispatcher.onCurrPlayTimeChange(unpackCurrPlayTime(pkg));
				if (unpackPlayIndex(pkg)!=curr){
					curr=unpackPlayIndex(pkg);
					dispatcher.onAllPlayTimeChange(unpackAllPlayTime(pkg));
				}
//				Log.i("TAG","curr time in  appProtol= #" + pkg[8]+ "#" +  pkg[9]+ "#" +  pkg[10]);
//				Log.i("TAG","all time in  appProtol= #" + pkg[11]+ "#" +  pkg[12]+ "#" +  pkg[13]);
				break;
			case BLUETOOTH:
				dispatcher.onCurrPlayIndexChange(unpackPlayIndex(pkg));
				dispatcher.onPlayAllIndexChange(unpackAllIndex(pkg));
				dispatcher.onCurrPlayTimeChange(unpackCurrPlayTime(pkg));
				dispatcher.onAllPlayTimeChange(unpackAllPlayTime(pkg));
				break;
			default:
				break;
			}
	}



	//解析歌曲当前播放时长
	private long unpackCurrPlayTime(byte[] currTime) {
		long time1=(currTime[8]*60*60*1000)+(currTime[9]*60*1000)+(currTime[10]*1000);
		return time1;
	}

	//解析当前歌曲总时长
	private long unpackAllPlayTime(byte[] allTime) {
		long time2=(allTime[11]*60*60*1000)+(allTime[12]*60*1000)+(allTime[13]*1000);
		return time2;
	}

	//解析歌曲总数量
	private int unpackAllIndex(byte[] allIndexs) {
		return Integer.parseInt(byte2hexbyId(allIndexs, 5 , 7), 16);
	}

	//解析当前播放歌曲索引
	private int unpackPlayIndex(byte[] indexs) {
		return Integer.parseInt(byte2hexbyId(indexs, 3 , 5), 16);
	}


//	//解析蓝牙音乐播放状态
//	private void unpackBtStatus(byte[] pkg) {
//		switch (pkg[1]){
//			case BT_STATUS_STOPED:
//			case BT_STATUS_PAUSED:
//			case BT_STATUS_PLAYING:
//			case BT_STATUS_FWD_SEEK:
//			case BT_STATUS_REV_SEEK:
//			case BT_STATUS_ERROR:
//				BT_PLAY_STATE = pkg[1];
//				 break;
//			default:
//				break;
//		}
//
//		switch (pkg[2]){
//			case BT_ORDER_NOTSUPPORT:
//			case BT_ORDER_PALY:
//			case BT_RANDOM_PALY:
//			case BT_SINGLE_LOOP:
//			case BT_LIST_LOOP:
//				BT_PLAY_ORDER = pkg[2];
//				break;
//			default:
//				break;
//		}
//
//
//	}

	public int identifyPackage(BluetoothDevice device, byte[] buffer, int len){

		//MCU 返回当前显示数据给手机
//		if (len > 0  && (buffer[0] == SYNC_1 || buffer[0] == 4)){
//			if (len > 0  && (buffer[0] == SYNC_1)){
//			unpackReturnShow(buffer,len);
//		}

		//同时解析优盘，电台，蓝牙播放状态和播放顺序
		if((dispatcher != null) && (len > 0)){
			switch (buffer[0]){
				case SYNC_1:
					 unpackReturnShow(buffer,len);//解析当前显示数据
					 break;
				case RETURN_USB_STATUS:
						if(buffer[1] == 0x01){
							dispatcher.onCurPlayStatusChange(buffer[2]);
//							Log.i("TAG","playstatus in  appProtol= " + buffer[2]);
							dispatcher.onCurrPlayOrderChange(buffer[3]);
						}else if(buffer[1] == 0x02){
							dispatcher.onCurrUsbMusicName(unpackUsbId3(buffer, len));
						}else if(buffer[1] == 0x03){
							dispatcher.onCurrUsbMusicAuthor(unpackUsbId3(buffer, len));
						}else{
							dispatcher.onCurrUsbMusicAlbum(unpackUsbId3(buffer, len));
						}
					break;
				case RETURN_RADIO_STATUS:
					if(buffer[1] == 0x01){
						//解析FM/AM 对应每页的6个收藏频率(电台1-电台6)
						dispatcher.onCurrPageFavorFreqChange(buffer);
					}
					break;
				case RETURN_BT_STATUS:
					if(buffer[1] == 0x01){
						dispatcher.onCurPlayStatusChange(buffer[2]);
						dispatcher.onCurrPlayOrderChange(buffer[3]);
					}else if(buffer[1] == 0x02){
						dispatcher.onCurrUsbMusicName(unpackBtMusicId3(buffer, len));
					}else if(buffer[1] == 0x03){
						dispatcher.onCurrUsbMusicAuthor(unpackBtMusicId3(buffer, len));
					}else{
						dispatcher.onCurrUsbMusicAlbum(unpackBtMusicId3(buffer, len));
					}
					break;
			}
		}
		return 0;
	}

//	private byte[] unpackCurrPageFavor(byte[] buffer) {
//		return buffer;
//	}

}
