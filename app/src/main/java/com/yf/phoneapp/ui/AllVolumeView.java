package com.yf.phoneapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.yf.radiophoneapp.R;


/**
 * Created by lishanfeng on 16/8/4.
 */
public class AllVolumeView extends View{

    private Bitmap mCacheBitmap;
    private Drawable mDrableBg;
    private int mValue = 0, mTotal = 32;
    private Bitmap mBitMapSrc ;

    public AllVolumeView(Context context) {
        this(context, null);
    }

    public AllVolumeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllVolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBitMapSrc= BitmapFactory.decodeResource(getResources(), R.drawable.voice_pic);
        mDrableBg = new BitmapDrawable(getResources(), mBitMapSrc);
        mCacheBitmap = Bitmap.createBitmap(mDrableBg.getMinimumWidth(), mDrableBg.getMinimumHeight(), Bitmap.Config.ARGB_8888);
        mDrableBg.setBounds(0, 0, mCacheBitmap.getWidth(), mCacheBitmap.getHeight());
        update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mCacheBitmap, 0, 0, null);
    }

    public void setVolume(int value) {
        mValue = value;
        update();
    }

    private void update() {
        mCacheBitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(mCacheBitmap);
        int right = (int) (((float) mValue / (float) mTotal) * mCacheBitmap.getWidth());
        canvas.clipRect(0, 0, right, mCacheBitmap.getHeight());
        mDrableBg.draw(canvas);
        postInvalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mCacheBitmap.getWidth(), mCacheBitmap.getHeight());
    }
}
