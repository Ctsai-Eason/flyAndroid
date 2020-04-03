package com.ctsaing.flyandroid.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


import com.ctsaing.flyandroid.R;

/**
 * 通过继承View实现上面图片下边文字的自定义View
 * created by cyh on 2019/12/25
 */
public class ImageTextCustomView extends View {

	private Drawable drawable;//画上方图片
	private String content;// 画下方文字

	//默认的View宽高
	private int viewWidth;
	private int viewHeight;


	//默认的属性值
	private int defalutImageWidth = 30;
	private int defalutImageHeight = 30;
	private int defalutTextColor = Color.BLACK;
	private float defalutTextSize = 8;

	//xml中获取到的属性值
	private int xmlImageWidth;
	private int xmlImageHeight;
	private int xmlTextColor;
	private float xmlTextSize;

	//计算text的长度
	private int textWidth;
	private int textHeight;

	//支持image和text的距离
	private int imageAndTextMargin;

	//画笔
	private Paint imagePaint = new Paint();
	private TextPaint textPaint = new TextPaint();

	public ImageTextCustomView(Context context) {
		super(context);
	}

	public ImageTextCustomView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs, 0);
	}

	public ImageTextCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
		//先获取xml中的属性值
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextCustomView);
		xmlImageWidth = typedArray.getDimensionPixelSize(R.styleable.ImageTextCustomView_imageWidth, defalutImageWidth);
		xmlImageHeight = typedArray.getDimensionPixelSize(R.styleable.ImageTextCustomView_imageHeight, defalutImageHeight);
		drawable = typedArray.getDrawable(R.styleable.ImageTextCustomView_imageSrc);
		xmlTextColor = typedArray.getColor(R.styleable.ImageTextCustomView_textColor, defalutTextColor);
		xmlTextSize = typedArray.getDimension(R.styleable.ImageTextCustomView_textSize, defalutTextSize);
		content = typedArray.getString(R.styleable.ImageTextCustomView_textContent);
		imageAndTextMargin = typedArray.getDimensionPixelSize(R.styleable.ImageTextCustomView_imageAndTextMargin, 0);
		textHeight = (int) xmlTextSize;
		textPaint.setColor(xmlTextColor);
		textPaint.setTextSize(xmlTextSize);
		textWidth = (int) Layout.getDesiredWidth(content,textPaint);

		typedArray.recycle();
	}

	/**
	 * 重新测量View的宽高，并支持wrap_content设置
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		//获取到View的整个高度
		viewWidth = xmlImageWidth > textWidth ? xmlImageWidth : textWidth;
		viewHeight = xmlImageHeight + imageAndTextMargin + textHeight;
		viewWidth = Math.max(viewWidth, getSuggestedMinimumWidth());
		viewHeight = Math.max(viewHeight, getSuggestedMinimumHeight());

		/**
		 * 设置View的宽高
		 * tips:当xml文件中设置的是wrap_content时，Mode==AT_MOST
		 * 当设置位match_parent或具体的宽高时,Mode == EXACTLY
		 */
		if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(viewWidth, viewHeight);
		} else if (widthMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(viewWidth, heightSize);
		} else if (heightMode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSize, viewHeight);
		} else {
			setMeasuredDimension(widthSize, heightSize);
		}


	}

	/**
	 * 在画布中间画出图片和文字，图片在上，文字在下
	 *
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//xml文件中image设置的宽高时，而实际要显示的图片宽高可能要大于设置值;因此要要对bitmap按比例缩放
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		//获得bitmap的宽高
		int bitmapWidth = bitmap.getWidth();
		int bitmapHeight = bitmap.getHeight();
		//计算缩放比列
		float scaleWidth = ((float)xmlImageWidth)/bitmapWidth;
		float scaleHeight = ((float)xmlImageHeight)/bitmapHeight;
		//设置缩放的Matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth,scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,0,bitmapWidth,bitmapHeight,matrix,true);

		//开始画bitmap的位置
		float imageLeft = (viewWidth - xmlImageWidth) / 2;
		canvas.drawBitmap(newBitmap, imageLeft, 0, imagePaint);

		//画text的位置
		float y = xmlImageHeight + imageAndTextMargin;
		canvas.drawText(content,0,y,textPaint);
		bitmap.recycle();
		newBitmap.recycle();
		canvas.restore();
	}
}
