package com.yf.phoneapp.eqsetting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yf.radiophoneapp.R;


public class GPointBar extends View {
	private static Bitmap bar;
	private int width, height; 
	private int MAX_PROGRESS = 20;
	private int curXProgress = 20;
	private int curYProgress;
	private int diameter = 40; // icon diameter (radios * 2)
	private int mPX; // center x, y; x, y pixel-scale
	private int mpY;

	private int low;

	public GPointBar(Context context) {
		this(context, null);
	}

	public GPointBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GPointBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		// 获取高低配置
//		low = Iapplication.getLV();

		if (low == 0) {
			curYProgress = 20;
		} else {
			curYProgress = 10;
		}

		if (bar == null)
			bar = BitmapFactory.decodeResource(getResources(), R.drawable.setup_eq_sound_zy_zqd);
	}

	public int getMaxProgress() {
		return MAX_PROGRESS;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		int itemX = mPX;
		int itemY = mpY;
		int x;
		int y;
		// 低配版
		if (low == 0) {
			Log.d("mv", "---------低配版-------");
			x = itemX * curXProgress;
			y = 0;// item*curYProgress;

			Log.d("mv", "---X:" + x + "----Y:" + y);
			canvas.drawBitmap(bar, x, y, null);
		} else if (low == 1) // 高配版
		{
			x = itemX * curXProgress;
			y = itemY * curYProgress;

			Log.d("mvvv", "---high---> itemX:" + itemX + "itemY:" + itemY
					+ "    curXProgress:" + curXProgress + "     curYProgress:"
					+ curYProgress);
			Log.d("mvvv", "---high---> x:" + x + "     y:" + y);

			canvas.drawBitmap(bar, x - 8, y - 3, null);
			// canvas.drawBitmap(bar, x-8 , y-3 , null);
		}

		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int x;
		int y;
		int xprgress = 10, yprgress = 10;

		Log.d("mv", "-----onTouchEvent-----");
		x = (int) event.getX();
		y = (int) event.getY();

		// 低配版
		if (low == 0) {

			Log.d("mv", "--onTouch----low:" + low);

			if (x > width - diameter / 2) {
				x = width - diameter / 2;
			} else if (x < diameter / 2) {
				x = diameter / 2;
			}
			if (Math.abs(x - width / 2) < diameter / 4) {
				x = width / 2;
			}
			x -= diameter / 2;

			if (mPX > 0) {
				xprgress = x / mPX;
			}

			if (mpY > 0) {
				yprgress = x / mpY;
			}

			if (xprgress != curXProgress) {
				curXProgress = xprgress;
				curYProgress = yprgress;

				if (listener != null) {
					listener.onGPointBarChange(this, curXProgress,
							curYProgress, true);
				}
				invalidate();
			}
		} else if (low == 1) // 高配版
		{

			Log.d("mv", "--onTouch----low:" + low);

			if (x < 0)
				x = 0;
			else if (x > width - diameter / 2)
				x = width - diameter / 2;
			if (y < 0)
				y = 0;
			else if (y > height - diameter / 2)
				y = height - diameter / 2;

			if (((x - 118) * (x - 118) + (y - 122) * (y - 122)) > 20 * 20) {
				xprgress = x * MAX_PROGRESS / (width - diameter / 2);
				yprgress = y * MAX_PROGRESS / (height - diameter / 2);

			}
			if (xprgress != curXProgress || yprgress != curYProgress) {
				curXProgress = xprgress;
				curYProgress = yprgress;


				if (listener != null) {
					listener.onGPointBarChange(this, curXProgress,
							curYProgress, true);
				}
				invalidate();
			}
		}

		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		Log.d("mvv", "---height:" + h);

		width = w;
		height = h;
		mPX = (width - diameter) / MAX_PROGRESS;
		mpY = (height - diameter*2 ) / MAX_PROGRESS;
		
//		Loger.v("width = " + width + ", height = " + height + ", mPX = " + mPX);
	}

	GPointBarChangeListener listener;

	public void setGPointBarChangeListener(GPointBarChangeListener listener) {
		this.listener = listener;
	}

	public interface GPointBarChangeListener {
		public void onGPointBarChange(View view, int valueX, int valueY,
									  boolean isUser);
	}

	public static class GPointItem {
		public int x, y;

		public GPointItem(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public GPointItem getProgress() {
		return new GPointItem(curXProgress, curYProgress);
	}

	public void setProgress(int xProgress, int yProgress) {
		if (xProgress < 0)
			xProgress = 0;
		else if (xProgress > MAX_PROGRESS) {
			xProgress = MAX_PROGRESS;
		}

		if (yProgress < 0) {
			yProgress = 0;
		} else if (yProgress > MAX_PROGRESS) {
			yProgress = MAX_PROGRESS;
		}

		if (xProgress != curXProgress || yProgress != curYProgress) {

			Log.d("bass", "curXProgress:" + curXProgress);
			Log.d("bass", "xProgress:" + xProgress);

			curXProgress = xProgress;
			curYProgress = yProgress;
			if (listener != null) {
				listener.onGPointBarChange(this, curXProgress, curYProgress,
						false);
			}
			
			invalidate();
		}
	}
}
