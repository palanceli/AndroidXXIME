package com.binglen.androidxxime;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palance on 16/1/19.
 */
public class CandidateView extends View {
    private List<String> mSuggestions;      // 存放候选列表
    private static final int X_GAP = 10;    // 每个候选之间的间隔
    private int mColorNormal;               // 候选前景色
    private int mVerticalPadding;           // 候选文字上下边距
    private Paint mPaint;                   // 用于绘制候选

    public CandidateView(Context context) {
        super(context);
        Log.d(this.getClass().toString(), "CandidateView: ");

        Resources r = context.getResources();

        setBackgroundColor(getResources().getColor(R.color.candidate_background, null));

        mColorNormal = r.getColor(R.color.candidate_normal, null);
        mVerticalPadding = r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);

        mPaint = new Paint();
        mPaint.setColor(mColorNormal);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(r.getDimensionPixelSize(R.dimen.candidate_font_height));
        mPaint.setStrokeWidth(0);

        setWillNotDraw(false);  // 覆盖了onDraw函数应清除该标记
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(this.getClass().toString(), "onMeasure: ");
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);

        int measuredWidth = resolveSize(50, widthMeasureSpec);

        final int desiredHeight = ((int)mPaint.getTextSize()) + mVerticalPadding;

        // Maximum possible width and desired height
        setMeasuredDimension(measuredWidth, resolveSize(desiredHeight, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(this.getClass().toString(), "onDraw: ");
        super.onDraw(canvas);

        if (mSuggestions == null)
            return;

        int x = 0;
        final int count = mSuggestions.size();
        final int height = getHeight();
        final int y = (int) (((height - mPaint.getTextSize()) / 2) - mPaint.ascent());

        for (int i = 0; i < count; i++) {
            String suggestion = mSuggestions.get(i);
            float textWidth = mPaint.measureText(suggestion);
            final int wordWidth = (int) textWidth + X_GAP * 2;

            canvas.drawText(suggestion, x + X_GAP, y, mPaint);
            x += wordWidth;
        }
    }

    public void setSuggestions(List<String> suggestions) {
        if (suggestions != null) {
            mSuggestions = new ArrayList<String>(suggestions);
        }
        invalidate();
        requestLayout();
    }

}

