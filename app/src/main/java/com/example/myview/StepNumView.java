package com.example.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class StepNumView extends View {
    private final int stepViewWidth = 300;
    private final int stepViewHeight = 300;

    private Paint devicePaint;//计步器画笔
    private Paint stepPaint;//步数画笔
    private Paint textPaint;//数字字符串画笔
    private int textColor;//数字字符串颜色
    private int textSize;//数字字符大小
    private Rect textRect;


    private int leftBodercolor;
    private RectF mRect;
    // 绘制画笔
    private Paint arcPaint;
    private int strokeWidth;
    //    private static final int DEFAULT_COLOR_LOWER = Color.parseColor("");
    private int r;//计步器半径
    private int top;//计步器view顶部距离canvas的高度
    private int left;
    private int right;
    private int bottom;


    public StepNumView(Context context) {
        this(context, null);
    }

    public StepNumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepNumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.StepNumView, defStyleAttr, 0);
        textColor = ta.getColor(R.styleable.StepNumView_textColor, Color.GREEN);
        textSize = ta.getDimensionPixelSize(R.styleable.StepNumView_textSize, 70);
        textRect = new Rect();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);


        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
        leftBodercolor = attributes.getColor(R.styleable.ClockView_color_dial_lower, Color.GRAY);
        strokeWidth = (int) attributes.getDimension(R.styleable.ClockView_stroke_width_dial, 50);
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(strokeWidth);
        r = Math.min(getRight() / 2, getBottom() / 2);
        top = (getHeight() - r) / 2;
        left = (getWidth() - r) / 2;
        right = r + left;
        bottom = top + r;
        mRect = new RectF(left, top, right, bottom);

    }

    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(stepViewWidth, stepViewHeight);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, stepViewHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(stepViewWidth, heightSize);
        }
    }

    private int stepNum = 100;//步数
    private int target = 2000;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawText(canvas);
        drawDevice(canvas);
        drawStep(canvas);
    }

    private void drawText(Canvas canvas) {
        r = Math.min(getRight() / 2, getBottom() / 2);
        top = (getHeight() - r) / 2;
        left = (getWidth() - r) / 2;
        right = r + left;
        bottom = top + r;
        String stepStr = stepNum + "/" + target;
        textPaint.getTextBounds(stepStr, 0, stepStr.length(), textRect);
        canvas.drawText(stepStr, left + r / 2, top + r / 3 * 2, textPaint);
        canvas.save();
        canvas.restore();
    }

    private void drawDevice(Canvas canvas) {
        canvas.save();
        r = Math.min(getRight() / 2, getBottom() / 2);
        top = (getHeight() - r) / 2;
        left = (getWidth() - r) / 2;
        right = r + left;
        bottom = top + r;
        mRect = new RectF(left, top, right, bottom);
        arcPaint.setColor(leftBodercolor);
        canvas.drawArc(mRect, 45, -270, false, arcPaint);
        canvas.restore();
    }

    private void drawStep(Canvas canvas) {
        canvas.save();
        r = Math.min(getRight() / 2, getBottom() / 2);
        top = (getHeight() - r) / 2;
        left = (getWidth() - r) / 2;
        right = r + left;
        bottom = top + r;
        mRect = new RectF(left, top, right, bottom);
        arcPaint.setColor(Color.GREEN);
        double per = 0.0;
        //计算度数
        if (stepNum >= target) {
            per = 1;
        } else {
            per = (double) stepNum / (double) target;
        }
        double degree = -270 * per;
        canvas.drawArc(mRect, 45, (int) degree, false, arcPaint);
        canvas.restore();
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
        if (stepNum != 0) {
            invalidate();
        }
    }

    public void setTarget(int target) {
        this.target = target;
//        if (target != 0) {
//            invalidate();
//        }
    }
}

