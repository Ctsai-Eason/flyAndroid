package com.ctsaing.flyandroid.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ctsaing.flyandroid.R;

/**
 * 上面图片，下面文字的自定义view，通过RelativeLayout的方式实现
 * create by cyh on 2019/12/25
 */
public class ImageTextLayoutView extends RelativeLayout {

	private ImageView topImage;
	private TextView bottomText;

	/**
	 * 这个构造方法是在代码中new的时候调用的
	 * @param context
	 */
	public ImageTextLayoutView(Context context) {
		super(context);
	}

	/**
	 * 这个构造方法是在xml文件中初始化调用的
	 * @param context
	 * @param attrs			View的xml属性
	 */

	public ImageTextLayoutView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}

	/**
	 * 这个方法不常用，有前两个足够了
	 * @param context
	 * @param attrs
	 * @param defStyleAttr		应用到View的主题风格（定义在主题中）
	 */
	public ImageTextLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	private void initView(Context context,AttributeSet attrs){
		//获取子控件
		LayoutInflater.from(context).inflate(R.layout.image_text_view_layout,this);
		topImage = findViewById(R.id.top_image);
		bottomText = findViewById(R.id.bottom_text);

		//获取xml中定义的所有属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ImageTextLayoutView);

		//获取ImageView相关自定义属性并设置
		int image_width = typedArray.getDimensionPixelSize(R.styleable.ImageTextLayoutView_image_width,30);
		int image_height = typedArray.getDimensionPixelSize(R.styleable.ImageTextLayoutView_image_height,30);
		Drawable drawable = typedArray.getDrawable(R.styleable.ImageTextLayoutView_image_src);
		//topImage.setLayoutParams(new LayoutParams(image_width,image_height));
		drawable.setBounds(0,0,image_width,image_height);
		topImage.setImageDrawable(drawable);

		//获取TextView相关的自定义属性并设置
		String content = typedArray.getString(R.styleable.ImageTextLayoutView_text_content);
		float text_size = typedArray.getDimension(R.styleable.ImageTextLayoutView_text_size,12);
		int text_color = typedArray.getColor(R.styleable.ImageTextLayoutView_text_color, Color.BLACK);
		bottomText.setText(content);
		bottomText.setTextSize(text_size);
		bottomText.setTextColor(text_color);
	}
}
