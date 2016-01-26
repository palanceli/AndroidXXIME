package com.binglen.androidxxime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by palance on 16/1/19.
 */
public class CandidateView extends View {
    private AndroidXXIME xxIME;
    private String m_inputString = new String("");
    private Paint m_textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CandidateView(Context context){
        super(context);
    }

    public CandidateView(Context context, AttributeSet set){
        super(context, set);
        m_textPaint.setTextSize(100);
        m_textPaint.setColor(Color.BLACK);
    }

    public void setService(AndroidXXIME androidXXIME){
        xxIME = androidXXIME;
    }

    @Override public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawText(m_inputString, 0, 100, m_textPaint);
    }
}
