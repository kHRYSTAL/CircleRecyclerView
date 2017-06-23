package me.khrystal.library.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/24
 * update time:
 * email: 723526676@qq.com
 */

public class CircularHorizontalMode implements ItemViewMode {

    private int mCircleOffset = 500;
    private float mDegToRad = 1.0f / 180.0f * (float) Math.PI;
    private float mScalingRatio = 0.001f;
    private float mTranslationRatio = 0.15f;

    public CircularHorizontalMode() {
    }

    public CircularHorizontalMode(int circleOffset, float degToRad, float scalingRatio, float translationRatio) {
        mCircleOffset = circleOffset;
        mDegToRad = degToRad;
        mScalingRatio = scalingRatio;
        mTranslationRatio = translationRatio;

    }


    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfWidth = v.getWidth() * 0.5f;
        float parentHalfWidth = parent.getWidth() * 0.5f;
        float x = v.getX();
        float rot = parentHalfWidth - halfWidth - x;

        ViewCompat.setPivotY(v, 0.0f);
        ViewCompat.setPivotX(v, halfWidth);
        ViewCompat.setRotation(v, -rot * 0.05f);
        ViewCompat.setTranslationY(v, (float) (-Math.cos(rot * mTranslationRatio * mDegToRad) + 1) * mCircleOffset);

        float scale = 1.0f - Math.abs(parentHalfWidth - halfWidth - x) * mScalingRatio;
        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
    }
}
