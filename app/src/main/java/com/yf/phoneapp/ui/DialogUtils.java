package com.yf.phoneapp.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.yf.radiophoneapp.MainActivity;
import com.yf.radiophoneapp.R;


import android.app.Dialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogUtils {
    
    private static Dialog dialog;
    private static Timer hideDialogTimer;
    private static Handler handler = new Handler();
    
    
    public static WindowDialog windowDialog = null;
    
    
    public static void destory() {
        if (null != windowDialog) {
            windowDialog.hide();
        }

        if (isDialogShowing()) {
            dialog.dismiss();
        }
    }
    
    public static boolean isDialogShowing() {
        if (dialog != null && dialog.isShowing()) {
            return true;
        }
        return false;
    }
    
    public static Dialog getDialog(){
        return dialog;
    }
    
    public static void showLoadingDialog(final Context context, String msg, long delayClose) {
        hideLoadingDialog();
        dialog = new Dialog(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        
        ImageView iv = (ImageView) view.findViewById(R.id.loading_image);
        iv.setBackgroundResource(R.drawable.load1);
        
        iv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.loading));
        
        
        ((TextView) view.findViewById(R.id.loading_text)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
//        dialog.getWindow().setLayout(600, 314);
        dialog.getWindow().setLayout(800, 480);
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog = null;
                cancelDialogTimer();
            }
        });
        
        
        if (delayClose != -1) {
            hideDialogTimer = new Timer();
            hideDialogTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    hideLoadingDialog();
                    
                    handler.post(new Runnable(){

                        @Override
                        public void run() {
                        }
                    });
                }
            }, delayClose);
        }
    }

    private static void cancelDialogTimer() {
        if (hideDialogTimer != null) {
            hideDialogTimer.cancel();
            hideDialogTimer = null;
        }
    }

    public static void hideLoadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        cancelDialogTimer();
    }
    
    public static void closeWindownDialog(){
        if(null != windowDialog){
            windowDialog.hide();
            windowDialog= null;
        }
    }
    public static WindowDialog showWindowDialog(Context context, View view) {
        closeWindownDialog();
        WindowDialog windowDialog = new WindowDialog(context, view);
        windowDialog.show(context);
        windowDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				 DialogUtils.windowDialog = null;
			}
		});
        DialogUtils.windowDialog = windowDialog;
        return windowDialog;
    }
    
    static WindowDialog mWindowDialog;
    public static void hideWindow(){
    	if(mWindowDialog != null)
    		mWindowDialog.hide();
    	handler.removeCallbacksAndMessages(null);
    }

    public static View showConnectFailedDialog(Context context, String strDevName, int delayClose) {
        
        View view = LayoutInflater.from(context).inflate(R.layout.bt_pair_dialog, null);
        ((TextView) view.findViewById(R.id.pair_msg)).setText(R.string.disconnect_device);
        
        if(strDevName == null) {
            strDevName = "";
        }
        
        ((TextView) view.findViewById(R.id.pair_farm_msg)).setText(strDevName);
        ((TextView) view.findViewById(R.id.pair_farm_msg)).setTextColor(context.getResources().getColor(R.color.freq_color));
        final WindowDialog windowDialog = new WindowDialog(context, view);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                windowDialog.hide();
            }
            
        };
        
        windowDialog.show(context);
        mWindowDialog = windowDialog;
        
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delayClose);
        
        return view;
    }
    
 public static View showFirstConnectDialog(Context context, String strDevName, int textId, boolean isConnBt) {
        View view = LayoutInflater.from(context).inflate(R.layout.bt_pair_dialog, null);
        ((TextView) view.findViewById(R.id.pair_msg)).setText(textId);

        Button btyes = (Button) view.findViewById(R.id.pair_yes);
        Button btno = (Button) view.findViewById(R.id.pair_no);

        if(isConnBt){
            btyes.setText("设置");
            btno.setText("退出");
        }else {
            btyes.setText("是");
            btno.setText("否");
        }

        if(strDevName == null) {
            strDevName = "";
        }
        
        ((TextView) view.findViewById(R.id.pair_farm_msg)).setText(strDevName);
        ((TextView) view.findViewById(R.id.pair_farm_msg)).setTextColor(context.getResources().getColor(R.color.freq_color));
        final WindowDialog windowDialog = new WindowDialog(context, view);
        windowDialog.show(context);
        mWindowDialog = windowDialog;
        Log.d("TAG","====showfistconn=====: ");
        return view;
    }


    public static View showOnlyTextDialog(Context context, int textId) {
        View view = LayoutInflater.from(context).inflate(R.layout.only_text_dialog, null);
        ((TextView) view.findViewById(R.id.only_text)).setText(textId);

        final WindowDialog windowDialog = new WindowDialog(context, view);
        windowDialog.show(context);
        mWindowDialog = windowDialog;
        return view;
    }

    public static Drawable getDrawablebyBitmap(int resId){

        Bitmap mBitMapSrc= BitmapFactory.decodeResource(Resources.getSystem(), resId);
        Drawable mDrableBg = new BitmapDrawable(Resources.getSystem(), mBitMapSrc);
        return  mDrableBg;
    }

}
