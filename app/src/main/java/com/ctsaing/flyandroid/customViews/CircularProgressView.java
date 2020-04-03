package com.ctsaing.flyandroid.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ctsaing.flyandroid.R;

/**
 * Created by Administrator on 2020/4/3.
 */

public class CircularProgressView extends View {

    private static final int defaultTextColor = Color.BLACK;//文字默认黑色
    private static final int defaultTextSize = 12;//默认字体大小
    private static final int defaultBackRingColor = Color.BLUE;
    private static final int defaultHeadRingColor = Color.GRAY;
    private static final int defaultViewWidthAndHeight = 100;//View默认大小
    private static final int defaultRingWidth = 10;//圆环默认宽度


    //记录点击或者长按状态
    private static final int STATE_ONE = 0x01;
    private static final int STATE_TWO = STATE_ONE << 1;
    private static final int STATE_MASK = 0x03;

    //记录实时状态
    private int currentState = STATE_ONE;

    private String centerText;//圆环中间的文字
    private TextPaint mTextPaint;//文字的画笔
    private int centerTextColor;//中间文字的颜色
    private float centerTextSize;//布局设置的文字大小
    private float textX, textY;//文字绘制区域

    private Paint mBackRingPaint, mHeadRingPaint;//前后绘制画笔
    private float mBackRingWidth, mHeadRingWidth;//前后圆环的宽度
    private int mBackRingColor, mHeadRingColor;
    private int mProgress;//圆环进度
    private RectF mBackRectF, mHeadRectF;//圆环绘制区域

    private CallBack mCallBack;

    //记录手指按下抬起的时间
    private long downTime, upTime;

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
            centerText = ta.getString(R.styleable.CircularProgressView_centerText);
            centerTextColor = ta.getColor(R.styleable.CircularProgressView_centerTextColor, defaultTextColor);
            centerTextSize = ta.getDimension(R.styleable.CircularProgressView_centerTextSize, defaultTextSize);
            mBackRingWidth = ta.getDimension(R.styleable.CircularProgressView_backRingWidth, defaultRingWidth);
            mBackRingColor = ta.getColor(R.styleable.CircularProgressView_backRingColor, defaultBackRingColor);
            mHeadRingWidth = ta.getDimension(R.styleable.CircularProgressView_headRingWidth, defaultRingWidth);
            mHeadRingColor = ta.getColor(R.styleable.CircularProgressView_headRingColor, defaultHeadRingColor);
            ta.recycle();
        }


        mBackRingPaint = new Paint();
        mBackRingPaint.setColor(mBackRingColor);
        mBackRingPaint.setAntiAlias(true);//抗锯齿
        mBackRingPaint.setStyle(Paint.Style.STROKE);//只描边不填充
        mBackRingPaint.setDither(true);//抖动
        mBackRingPaint.setStrokeWidth(mBackRingWidth);

        mHeadRingPaint = new Paint();
        mHeadRingPaint.setColor(mHeadRingColor);
        mHeadRingPaint.setAntiAlias(true);//抗锯齿
        mHeadRingPaint.setStyle(Paint.Style.STROKE);//只描边不填充
        mHeadRingPaint.setDither(true);//抖动
        mHeadRingPaint.setStrokeWidth(mHeadRingWidth);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(centerTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(centerTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBackRectF = new RectF();
        mHeadRectF = new RectF();

    }


    /**
     * 重新测量View宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int viewHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = Math.max(viewWidth, getSuggestedMinimumWidth());
        viewHeight = Math.max(viewHeight, getSuggestedMinimumHeight());

        //设置view的宽高
        if (viewWidthMode == MeasureSpec.AT_MOST && viewHeightMode == MeasureSpec.AT_MOST) {
            viewWidth = viewHeight = defaultViewWidthAndHeight;
        } else if (viewWidthMode == MeasureSpec.AT_MOST) {
            viewWidth = defaultViewWidthAndHeight;
        } else if (viewHeightMode == MeasureSpec.AT_MOST) {
            viewHeight = defaultViewWidthAndHeight;
        } else {

        }

        setMeasuredDimension(viewWidth, viewHeight);

        float ringLength = Math.min(viewWidth, viewHeight);

        //就算底部环的绘制区域
        float backRingDis = mBackRingPaint.getStrokeWidth() / 2;
        mBackRectF.set(backRingDis + getPaddingLeft(), backRingDis + getPaddingTop(),
                ringLength - backRingDis - getPaddingRight(), ringLength - backRingDis - getPaddingBottom());

        //计算顶部环的绘制区域
        float headRingDis = mHeadRingPaint.getStrokeWidth() / 2;
        mHeadRectF.set(headRingDis + getPaddingLeft(), headRingDis + getPaddingTop(),
                ringLength - headRingDis - getPaddingRight(), ringLength - headRingDis - getPaddingBottom());

        //计算text位置居中
        textX = mBackRectF.centerX();
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        textY = mBackRectF.centerY() + distance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mBackRectF, 0, 360, false, mBackRingPaint);
        if (mProgress > 100)
            mProgress = 100;
        canvas.drawArc(mHeadRectF, 0, 360 * mProgress / 100, false, mHeadRingPaint);
        canvas.drawText(centerText, textX, textY, mTextPaint);
    }


    //用手势还是自己监听？
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();

                if (upTime - downTime <= 500){
                    //说明是点击事件,回调处理
                    mCallBack.clickCall();
                }


                break;

        }
        return super.onTouchEvent(event);
    }

    public float dp2px(float dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dp * density + 0.5f;
    }


    public interface CallBack {
        void clickCall();
    }
}
