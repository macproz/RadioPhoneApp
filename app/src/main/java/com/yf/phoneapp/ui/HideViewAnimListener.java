package com.yf.phoneapp.ui;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class HideViewAnimListener implements AnimationListener {
    private View view;
    public HideViewAnimListener(View view)
    {
        this.view = view;
    }
    @Override
    public void onAnimationStart(Animation animation) {
        
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        view.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        
    }

}
