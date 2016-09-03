package com.example.zhou.testchn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private int[] recvdata;
    private int[] singlePageFavor = new int[12];
    private int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String str="蒋国平ABC";
//        Log.i("TAG",enUnicode(str));
        //打印出   738B6C5F6CE2662F4E004E2A5C0F670B53CBFF0C0048004F0048004F0048004F0048004F
//        str="738B6C5F6CE2662F4E004E2A5C0F670B53CBFF0C0048004F0048004F0048004F0048004F";
//        System.out.println(deUnicode(str));
//        String str = "848B56FD5E73004100420043d6dcbefc";
//        String istr = {0xd6,0xdc,0x41,0xbe,0xfc,0x42};
//        String istr = "d6dc41befc42";
//        byte[] bstr={0x41,0x42};

//        Log.i("TAG","decode " + decode(istr));

//        String encoded = "bdafb9fac6bd4142d6dc";
//
//        byte a0 = (byte) Integer.parseInt(encoded.substring(0, 2), 16);
//        byte a1 = (byte) Integer.parseInt(encoded.substring(2, 4), 16);
//        byte a2 = (byte) Integer.parseInt(encoded.substring(4,6), 16);
//        byte a3 = (byte) Integer.parseInt(encoded.substring(6, 8), 16);
//        byte a4 = (byte) Integer.parseInt(encoded.substring(8, 10), 16);
//        byte a5 = (byte) Integer.parseInt(encoded.substring(10,12), 16);
//        byte a6 = (byte) Integer.parseInt(encoded.substring(12,14), 16);
//        byte a7 = (byte) Integer.parseInt(encoded.substring(14,16), 16);
//        byte a8 = (byte) Integer.parseInt(encoded.substring(16,18), 16);
//        byte a9 = (byte) Integer.parseInt(encoded.substring(18), 16);



//        byte[] gbk = new byte[] {a0,a1,a2,a3,a4,a5,a6,a7,a8,a9};
        byte[] gbk3 = new byte[]{0x01,0x12,0x13,0x14,0x29,(byte)0xfe,0x29,(byte)0xcc,0x27,0x32,0x25,0x22,0x29,0x54,0x28,0x6e,0x2f,0x11};
        recvdata = getSinglePageFreq(gbk3);

        Log.i("TAG","after convert the recv intfreq = " + pos+ "@" + recvdata[0] +"#" + recvdata[2] + "#" + recvdata[4]
                                                        + "#" + recvdata[6] + "#" + recvdata[8] + "#" + recvdata[10]);


//        StringBuffer buffer=new StringBuffer();
//
//        for(int i=2;i<gbk2.length;i++){
//            if(gbk2[i]!=0){
//                try {
////                    String h=new String(new byte[]{(byte) gbk2[i],(byte) gbk2[i+1]},"utf-16");
////                    if(h.matches("[\u4e00-\u9fff]")){
////                        i++;
////                        buffer.append(h);
////                    }else{
//                        String e=new String(new byte[]{(byte) gbk2[i]},"GBK");
//                        buffer.append(e);
////                    }
//                } catch (UnsupportedEncodingException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }else{
//                break;
//            }
//        }
//
//        System.out.println("数据结果："+buffer.toString());


    }

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

    private  int[] getSinglePageFreq(byte[] favors) {
        //解析出每页6个频率,如FM第一页6个频率。1.favors[4-5], 2.favors[6-7]，类推
        for (int i = 4; i < 16; i += 2) {
            //i=4,6,8,10,12,14,分别对应1-6个频率。

            byte[] bytetwo = new byte[]{(byte) favors[i], (byte) favors[i + 1]};
            String hexFM = byte2hexbyId(bytetwo, 0,  2);

            //singlePageFavor[0-2-4-6-8-10]分别对应每页的1-6个频率用于显示。
            singlePageFavor[i-4] = Integer.parseInt(hexFM, 16);
            pos++;
        }

        return singlePageFavor;

    }
}




