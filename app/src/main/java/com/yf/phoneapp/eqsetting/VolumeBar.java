package com.yf.phoneapp.eqsetting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yf.radiophoneapp.R;


public class VolumeBar extends View {
	private static Bitmap bar;
	private static Bitmap bg;
	private int curProgress = 0;
	private int width;
	private int height;
	private int MAX_PROGRESS = DaoSound.DEF_PROGRESS << 1;
	Paint paint;

	public VolumeBar(Context context) {
		this(context, null);
	}

	public VolumeBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VolumeBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		if (bar == null)
			bar = BitmapFactory.decodeResource(getResources(), R.drawable.setup_eq_jhq_dzg_bj_bar);
		if (bg == null)
			bg = BitmapFactory.decodeResource(getResources(), R.drawable.setup_eq_jhq_dzg_bj1);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(24);
	}

	public int getProgress() {
		return this.curProgress;
	}

	public void setProgress(int progress) {
		setProgress(progress, true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}

	private void setProgress(int progress, boolean isUser) {
		if (curProgress == progress)
			return;
		curProgress = progress;
		if (curProgress < 0)
			curProgress = 0;
		else if (curProgress > MAX_PROGRESS)
			curProgress = MAX_PROGRESS;
		invalidate();
		
		if (listener != null)
			listener.onChange(this, curProgress, isUser);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() < 58)
			return false;
		int y = (int) event.getY();
		if (y < 5)
			y = 5;
		else if (y > height - 5)
			y = height - 5;
		y = height - y;
		int progress = (y - 5) / (bg.getHeight() / MAX_PROGRESS);
		if (progress < 0)
			progress = 0;
		else if (progress > MAX_PROGRESS)
			progress = MAX_PROGRESS;
		setProgress(progress, false);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) { 
		canvas.drawBitmap(bg, 58, 4, null);
		
		Log.d( "barss", "Max progress:"+ MAX_PROGRESS);
		Log.d( "barss", ">>>>>>>>>bg height:"+ bg.getHeight());
		Log.d( "barss", ">>>>>>>>>bg wight:"+ bg.getWidth());
		
		
		int itemHieht = bg.getHeight() / MAX_PROGRESS;
		Log.d( "barss", "item Height:"+ itemHieht);
		
		int pos = itemHieht * curProgress;
		Log.d( "barss", "current pos:"+ pos);
		
		int y = height - pos - DaoSound.DEF_PROGRESS - bar.getHeight() / 2;
		
		//-------------cg--------笨方法-------
		if( y< 9 )
		{
			y= 9;
		}
		else if( y<= 23 )
		{
			y+=8;
		}
		else if( y<= 31 )
		{
			y+= 6;
		}
		else if( y<= 41 )
		{
			y+= 4;
		}
		else if( y<= 51 )
		{
			y+= 3;
		}
		else if( y<= 69)
		{
			y+=2;
		}
		else if( y>= 109)
		{
			y-=3;
		}
		
		Log.d( "barss", "显示的位置:"+ y);
		 
		
		//---------------cg----------------
		canvas.drawBitmap(bar, 55, y, null);

		String str = (curProgress - DaoSound.DEF_PROGRESS) + "";
		
		//------------显示-----------------
		Log.d("sounds", ""+ str);
		
		if (curProgress > DaoSound.DEF_PROGRESS)
			str = "+" + str;
		canvas.drawText(str + "", (58 - paint.measureText(str)) / 2+ 3,
				y + bar.getHeight() / 2 + DaoSound.DEF_PROGRESS -2, paint);
		super.onDraw(canvas);
	}

	VolumeBarChangeListener listener;

	public int getMaxProgress() {
		return MAX_PROGRESS;
	}

	public void setVolumeBarChangeListener(VolumeBarChangeListener listener) {
		this.listener = listener;
	}

	public interface VolumeBarChangeListener {
		public void onChange(View view, int value, boolean isUser);
	}
}
