package com.yf.phoneapp.ui;

import com.yf.radiophoneapp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MusicSeekBar extends View {
	public static final String TAG = "MusicSeekBar";
	private Bitmap mBackground,mThumb,mProgressDrawable,mCacheBitmap;
	private OnSeekBarChangeListener mChangeListener;
	private int mMax,mProgress;
	private int mWidth;
	
	public MusicSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MusicSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MusicSeekbar); 
		mBackground      	  = ((BitmapDrawable)typedArray.getDrawable(R.styleable.MusicSeekbar_bar_background)).getBitmap();
		mThumb       		  = ((BitmapDrawable)typedArray.getDrawable(R.styleable.MusicSeekbar_bar_thumb)).getBitmap();
		mProgressDrawable     = ((BitmapDrawable)typedArray.getDrawable(R.styleable.MusicSeekbar_bar_progress_drawable)).getBitmap();
		typedArray.recycle();
		update();
	}
	
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener){
		mChangeListener = listener;
	}
	
	public void setMax(int max){
		mMax = max;
	}
	
	public int getProgress(){
		return mProgress;
	}
	
	public void setProgress(int progress){
//		if(progress > 500000)
//			return;
//		else
			mProgress = progress;
		update();
	}
	
	Bitmap qj = null;
	private void update(){
		if(mCacheBitmap ==  null){
			mCacheBitmap = Bitmap.createBitmap(mBackground.getWidth(), mBackground.getHeight(), Config.ARGB_8888);
		}
		
		mCacheBitmap.eraseColor(Color.TRANSPARENT);
		
		Canvas canvas = new Canvas(mCacheBitmap);
		canvas.drawBitmap(mBackground, 0, 0, null);
		
		int right = (int) (((float)mProgress/mMax) * (mBackground.getWidth() - 46));
		if(qj== null)
			qj = BitmapFactory.decodeResource(getResources(), R.drawable.jindutiao_yuandian);
		canvas.clipRect(new Rect(0, 0, right, mCacheBitmap.getHeight()));
		Canvas canvas2 = new Canvas(mCacheBitmap);
		canvas2.clipRect(new Rect(20, 0, mProgressDrawable.getWidth() + 20, mCacheBitmap.getHeight()));
		int dRight = right - mThumb.getWidth()/2 +24;
		canvas.drawBitmap(mThumb,dRight, 0, null);
		/*qj.setBounds(23, 0, qj.getWidth()+23,qj.getMinimumHeight());
		canvas.clipRect(23, 0, dRight+23, mBackground.getHeight());
		qj.draw(canvas);*/
		canvas.drawBitmap(mProgressDrawable, 0, 0, null);
		
		canvas = new Canvas(mCacheBitmap);
		
		postInvalidate();
	}
	
	@Override  
	protected void onDraw(Canvas canvas) {
		if(mCacheBitmap != null){
			canvas.drawBitmap(mCacheBitmap, 0, 0, null);
		}
	} 
	 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		if(mWidth <= 0){
			mWidth = getWidth();
		}
		float x = event.getX();
		int w = mBackground.getWidth();

		if(x < 0){
			x = 0;
		}else if(x > w){
			x = w;
		}
		mProgress = (int) (x/mWidth * mMax);
		update();
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(mChangeListener != null){
				mChangeListener.onStartTrackingTouch(null);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(mChangeListener != null){
				mChangeListener.onProgressChanged(null, mProgress, true);
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if(mChangeListener != null){
				mChangeListener.onStopTrackingTouch(null);
			}
			break;
		}
		return true;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(mBackground != null){
			setMeasuredDimension(mBackground.getWidth(), mBackground.getHeight());
		}
	}
}
