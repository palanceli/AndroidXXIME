package com.binglen.androidxxime;

import android.content.Context;
import android.view.View;

/**
 * Created by palance on 16/1/19.
 */
public class CandidateView extends View {
    private AndroidXXIME xxIME;

    public CandidateView(Context context){
        super(context);
    }

    public void setService(AndroidXXIME androidXXIME){
        xxIME = androidXXIME;
    }
}
