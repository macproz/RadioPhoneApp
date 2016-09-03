package com.yf.phoneapp.ui;


import com.yf.radiophoneapp.R;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AlbumImageView extends ImageView implements AnimatorUpdateListener{

	private Bitmap mDefAlbumBitmap,mAlbumBitmap,mTempAlbumBitmap,mAlbumBackGround;
	private Bitmap mMaskBitmap/*mNewAlbumBitmap*/;
	private int mAlbumWidth,mAlbumHeight;
	private ValueAnimator mAnimator;
	private final int target = 180;
	private boolean isMake = false;
	private Handler mHandler = new Handler();
	private AnimatorRunnable mAnimatorRunnable = new AnimatorRunnable();
	public AlbumImageView(Context context) {
		super(context);
		init();
	}

	public AlbumImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AlbumImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		mAlbumBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.touying);
		mDefAlbumBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.moren);
		mAlbumWidth  = mAlbumBackGround.getWidth();
		mAlbumHeight = mAlbumBackGround.getHeight();
		
		mMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.haibao);
		
		mAlbumBitmap = makeAlbumBitmap(mDefAlbumBitmap);
		setBackground(new BitmapDrawable(getResources(),mAlbumBitmap));
		
		mAnimator = ValueAnimator.ofInt(0,target);
		mAnimator.setDuration(1000);
		mAnimator.addUpdateListener(this);
	}
	
	public void setAlbumImage(Bitmap image){
		mAnimatorRunnable.setAlbumBitmap(image);
		mHandler.removeCallbacks(mAnimatorRunnable);
		mHandler.postDelayed(mAnimatorRunnable, 500);
	}
	
	class AnimatorRunnable implements Runnable{

		private Bitmap albumImage;
		
		public void setAlbumBitmap(Bitmap image){
			albumImage = image;
		}
		
		@Override
		public void run() {
			if(mTempAlbumBitmap != null){
				mTempAlbumBitmap.recycle();
				mTempAlbumBitmap = null;
			}
			isMake = false;
			mTempAlbumBitmap = makeAlbumBitmap(albumImage);
			mAnimator.start();
		}
	}
	
	private Bitmap makeAlbumBitmap(Bitmap album){
		Bitmap mNewAlbumBitmap = Bitmap.createBitmap(mAlbumWidth, mAlbumHeight, Config.ARGB_8888);
		
		if(album == null){
			album = mDefAlbumBitmap;
		}
		
		Bitmap albumBitmap = Bitmap.createBitmap(
				getBasePixels(mMaskBitmap, scaleBitmap(album, mMaskBitmap.getWidth(), mMaskBitmap.getHeight())),
				 0,mMaskBitmap.getWidth(), mMaskBitmap.getWidth(), mMaskBitmap.getWidth(),Config.ARGB_8888);
		
		Canvas canvas = new Canvas(mNewAlbumBitmap);
		canvas.drawBitmap(mAlbumBackGround, 0, 0, null);
		canvas.drawBitmap(albumBitmap, 0, 0, null);
		
        return mNewAlbumBitmap;
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		
		int value = (Integer) animation.getAnimatedValue();
		
		if(value > target/2 && !isMake){
			isMake = true;
			
			if(mAlbumBitmap != null){
				mAlbumBitmap.recycle();
				mAlbumBitmap = null;
			}
			
			mAlbumBitmap = mTempAlbumBitmap;
			//setBackground(new BitmapDrawable(getResources(),mAlbumBitmap));
			setBackground(new BitmapDrawable(getResources(),mAlbumBitmap.copy(Bitmap.Config.ARGB_8888, false)));
		}
		float scale = Math.abs(target/2f - value)/(target/2f) * 0.4f + 0.6f;
		setScaleX(scale);
		setScaleY(scale);
		if(isMake){
			value += target;
		}
		setRotationY(value);
	}
	
	 private Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
       int oldWidth = bitmap.getWidth();
       int oldHeight = bitmap.getHeight();
       
       float scaleWidth = (float)newWidth / oldWidth ;
       float scaleHeight = (float)newHeight / oldHeight ;
       Matrix matrix = new Matrix();
       matrix.postScale(scaleWidth, scaleHeight);
       Bitmap resizedBitmap = null;
       try {
    	   resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, oldWidth, oldHeight, matrix, true);
       } catch(Exception e){
    	   e.printStackTrace();
    	   System.gc();
    	   System.gc();
    	   System.gc();
    	   if(resizedBitmap !=null && !resizedBitmap.isRecycled()){
    		   resizedBitmap.recycle();
    		   resizedBitmap = null;
    	   }
    	   return mDefAlbumBitmap;
       }
       return resizedBitmap;
    }
	
	private int[] getBasePixels(Bitmap iconMask, Bitmap iconBitmap){
       int maskWidth = iconMask.getWidth(); 
       int maskHeight = iconMask.getHeight();
       int[] sMaskPixels = new int[maskWidth * maskHeight];
       iconMask.getPixels(sMaskPixels, 0, maskWidth, 0, 0, maskWidth, maskHeight);
       int baseWidth = iconBitmap.getWidth();
       int baseHeight = iconBitmap.getHeight();
       int[] basePixels = new int[baseWidth * baseHeight];
       iconBitmap.getPixels(basePixels, 0, baseWidth, 0, 0, baseWidth, baseHeight);
       int w = baseWidth;
       int h = baseHeight;
       if (sMaskPixels != null) {
           for (int i = 0; i < w * h; i++) {
               basePixels[i] = basePixels[i] & sMaskPixels[i];
           }
       }
       return basePixels;
   }


}
