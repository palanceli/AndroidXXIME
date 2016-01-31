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

    private static final int OUT_OF_BOUNDS = -1;

    private AndroidXXIME mService;
    private List<String> mSuggestions;
    private Drawable mSelectionHighlight;

    private static final int X_GAP = 10;

    private static final List<String> EMPTY_LIST = new ArrayList<String>();

    private int mColorNormal;
    private int mVerticalPadding;
    private Paint mPaint;

    private int mTotalWidth;

    public CandidateView(Context context) {
        super(context);
        Log.d(this.getClass().toString(), "CandidateView: ");
        mSelectionHighlight = context.getResources().getDrawable(
                android.R.drawable.list_selector_background);
        mSelectionHighlight.setState(new int[]{
                android.R.attr.state_enabled,
                android.R.attr.state_focused,
                android.R.attr.state_window_focused,
                android.R.attr.state_pressed
        });

        Resources r = context.getResources();

        setBackgroundColor(getResources().getColor(R.color.candidate_background, null));

        mColorNormal = r.getColor(R.color.candidate_normal, null);
        mVerticalPadding = r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);

        mPaint = new Paint();
        mPaint.setColor(mColorNormal);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(r.getDimensionPixelSize(R.dimen.candidate_font_height));
        mPaint.setStrokeWidth(0);

        setHorizontalFadingEdgeEnabled(true);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }

    /**
     * A connection back to the service to communicate with the text field
     * @param listener
     */
    public void setService(AndroidXXIME listener) {
        mService = listener;
    }

    @Override
    public int computeHorizontalScrollRange() {
        return mTotalWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(this.getClass().toString(), "onMeasure: ");
        int measuredWidth = resolveSize(50, widthMeasureSpec);

        // Get the desired height of the icon menu view (last row of items does
        // not have a divider below)
        Rect padding = new Rect();
        mSelectionHighlight.getPadding(padding);
        final int desiredHeight = ((int)mPaint.getTextSize()) + mVerticalPadding
                + padding.top + padding.bottom;

        // Maximum possible width and desired height
        setMeasuredDimension(measuredWidth, resolveSize(desiredHeight, heightMeasureSpec));
    }

    /**
     * If the canvas is null, then only touch calculations are performed to pick the target
     * candidate.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(this.getClass().toString(), "onDraw: ");
        super.onDraw(canvas);

        mTotalWidth = 0;
        if (mSuggestions == null)
            return;

        int x = 0;
        final int count = mSuggestions.size();
        final int height = getHeight();
        final Paint paint = mPaint;
        final int y = (int) (((height - mPaint.getTextSize()) / 2) - mPaint.ascent());

        for (int i = 0; i < count; i++) {
            String suggestion = mSuggestions.get(i);
            float textWidth = paint.measureText(suggestion);
            final int wordWidth = (int) textWidth + X_GAP * 2;

            canvas.drawText(suggestion, x + X_GAP, y, paint);
            x += wordWidth;
        }
        mTotalWidth = x;
    }

    public void setSuggestions(List<String> suggestions, boolean completions,
                               boolean typedWordValid) {
        clear();
        if (suggestions != null) {
            mSuggestions = new ArrayList<String>(suggestions);
        }
        invalidate();
        requestLayout();
    }

    public void clear() {
        mSuggestions = EMPTY_LIST;
        invalidate();
    }
}

