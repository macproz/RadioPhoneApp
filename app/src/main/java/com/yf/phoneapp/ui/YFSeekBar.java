package com.yf.phoneapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.yf.radiophoneapp.R;

public class YFSeekBar extends SeekBar {
	private Drawable progressDrawable, thumbDrawable;
	private Rect barBounds, labelTextRect;
	private Bitmap labelBackground;
	private Point labelPos;
	private Paint labelTextPaint, labelBackgroundPaint;

	int viewWidth, barHeight, labelOffset = 0;
	float progressPosX;

	public YFSeekBar(Context context) {
		this(context, null);
	}

	public YFSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public YFSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		labelBackground = BitmapFactory.decodeResource(getResources(), R.drawable.transparent);
		barBounds = new Rect();
		labelTextRect = new Rect();
		labelPos = new Point();
		labelBackgroundPaint = new Paint();
		labelTextPaint = new Paint();
		labelTextPaint.setTextSize(24f);
		labelTextPaint.setAntiAlias(true);
		labelTextPaint.setColor(Color.WHITE);

		if (labelBackground != null) {
			Loger.d("YFSeekBar:" + labelBackground.getHeight());
		}
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		if (labelBackground != null) {
			int progress = getProgress();
			String labelText = "" + progress;
			int thumbX;
			int yoffset = 10;
			Rect rect = new Rect();
			getDrawingRect(rect);

			Loger.d("YFSeekBar 1 :" + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom);
			// thumbDrawable = getThumb();
//			thumbDrawable = LogUtils.getDrawable(R.drawable.setup_general_setup_screen_brightnessn_progress_bar);
			thumbDrawable = getDrawablebyBitmap(R.drawable.setup_general_setup_screen_brightnessn_progress_bar);


			barBounds.left = getPaddingLeft();
			barBounds.top = labelBackground.getHeight() + getPaddingTop();
			barBounds.right = barBounds.left + viewWidth - getPaddingRight() - getPaddingLeft();
			barBounds.bottom = barBounds.top + barHeight - getPaddingBottom() - getPaddingTop();

			if (labelBackground.getWidth() < thumbDrawable.getIntrinsicWidth()) {
				progressPosX = barBounds.left + ((float) (progress) / (float) this.getMax())
						* (barBounds.width() - thumbDrawable.getIntrinsicWidth());
			} else {
				progressPosX = barBounds.left + ((float) (progress) / (float) this.getMax())
						* (barBounds.width() /*- labelBackground.getWidth()*/);
			}

			labelPos.x = (int) progressPosX - labelOffset;
			labelPos.y = getPaddingTop();
			thumbX = (int) (progressPosX - getThumbOffset() + progress * 1.0);
//			progressDrawable = LogUtils.getDrawable(R.drawable.setup_general_setup_screen_brightnessn_progress_bj);
			progressDrawable = getDrawablebyBitmap(R.drawable.setup_general_setup_screen_brightnessn_progress_bj);

			progressDrawable.setBounds(barBounds.left, barBounds.top + 8, barBounds.right, barBounds.bottom + 8);
			progressDrawable.draw(canvas);


//			progressDrawable = LogUtils.getDrawable(R.drawable.setup_general_setup_screen_brightnessn_progress_qj);
			progressDrawable = getDrawablebyBitmap(R.drawable.setup_general_setup_screen_brightnessn_progress_qj);
			int x = (int) progressPosX;

	/*		if (x <= 10) {
				x = thumbX;
			} else if (x <= 145) {
				x += 4;
			} else if (x <= 235) {
				x += 6;
			} else if (x <= 325) {
				x += 10;
			} else if (x <= 461) {
				x += 13;
			}*/

			progressDrawable.setBounds(barBounds.left, barBounds.top + 8, x, barBounds.bottom + 8);
			System.out.println("yang3:" + progressDrawable.getBounds());
			progressDrawable.draw(canvas);

			labelTextPaint.getTextBounds(labelText, 0, labelText.length(), labelTextRect);

			canvas.drawBitmap(labelBackground, labelPos.x, labelPos.y, labelBackgroundPaint);

			if (thumbX <= 10) {
				thumbX -= 3;
			} else if (thumbX <= 55) {
				thumbX -= 3;
			} else if (thumbX <= 145) {
				thumbX -= 2;
			} else if (thumbX >= 280 && thumbX < 370) {
				thumbX += 4;
			} else if (thumbX >= 370) {
				thumbX += 6;
			}

			thumbDrawable.setBounds(thumbX, barBounds.top + 8, thumbX + thumbDrawable.getIntrinsicWidth(),
					barBounds.top + thumbDrawable.getIntrinsicHeight() + 8);

//			thumbDrawable.draw(canvas);

			rect = thumbDrawable.getBounds();
			Loger.d("YFSeekBar 2:" + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom);

		} else {
			super.onDraw(canvas);
		}
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (labelBackground != null) {
			viewWidth = getMeasuredWidth();
			barHeight = getMeasuredHeight();// returns only the bar height
											// (without the label);
			setMeasuredDimension(viewWidth, barHeight + labelBackground.getHeight());
		}

	}


	public  Drawable getDrawablebyBitmap(int resId){

		Bitmap mBitMapSrc= BitmapFactory.decodeResource(getResources(), resId);
		Drawable mDrableBg = new BitmapDrawable(getResources(), mBitMapSrc);
		return  mDrableBg;
	}

}
