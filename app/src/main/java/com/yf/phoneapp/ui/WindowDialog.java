
package com.yf.phoneapp.ui;


import com.yf.radiophoneapp.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;


/**
 * @author lishanfeng
 * @version 2014-9-18
 */
public class WindowDialog {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private View mView;
    private Dialog mDialog;

    public WindowDialog(Context context, View view) {
        mView = view;
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setCancelable(false);
        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mDialog.setContentView(view);
    }

    public View getView() {
        return mView;
    }

    
    private int getWidth(Context context){
    	WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		return (wm.getDefaultDisplay().getWidth() * 900 ) / 1920;
    }
    
    private int getHight(Context context){
    	WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		return (wm.getDefaultDisplay().getHeight() * 471 ) / 1080;
    }
    
    public void show(Context context) {
        if (isShowing()) {
            return;
        }
        WindowManager.LayoutParams params =  mDialog.getWindow().getAttributes();
        params.gravity = Gravity.TOP| Gravity.LEFT;
        params.width = getWidth(context);
        params.height = getHight(context);
        params.gravity = Gravity.CENTER;
        params.alpha = 1.f;
        mDialog.getWindow().setAttributes(params);
//        mDialog.show();
        mDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_UP)
				{	
					if(keyCode == KeyEvent.KEYCODE_BACK)
						hide();
				}
				
				return false;
			}
		});
    }

    public void hide() {
        if(isShowing()){
            mDialog.dismiss();
        }

        Log.v("WindowDialog", "hide");
    }

    public boolean isShowing(){
        if(mDialog != null){
            return mDialog.isShowing();
        }
        return false;
    }
    
    public void setOnDismissListener(OnDismissListener listener)
    {
        mDialog.setOnDismissListener(listener);
    }
    
    public void setCancelable(boolean enable) {
        mDialog.setCancelable(enable);
        mDialog.setCanceledOnTouchOutside(enable);
    }
}
