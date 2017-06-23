package me.khrystal.library.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/6/23
 * update time:
 * email: 723526676@qq.com
 */

public class CircularViewRTLMode implements ItemViewMode {

    private int mCircleOffset = 500;
    private float mDegToRad = 1.0f / 180.0f * (float) Math.PI;
    private float mScalingRatio = 0.001f;
    private float mTranslationRatio = 0.09f;

    public CircularViewRTLMode() {
    }

    public CircularViewRTLMode(int circleOffset, float degToRad, float scalingRatio, float translationRatio) {
        mCircleOffset = circleOffset;
        mDegToRad = degToRad;
        mScalingRatio = scalingRatio;
        mTranslationRatio = translationRatio;

    }

    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfHeight = v.getHeight() * 0.5f;
        float parentHalfHeight = parent.getHeight() * 0.5f;
        float y = v.getY();
        float rot = parentHalfHeight - halfHeight - y;

        ViewCompat.setPivotX(v, v.getWidth());
        ViewCompat.setPivotY(v, halfHeight);
        ViewCompat.setRotation(v, -1 * rot * 0.05f);
        ViewCompat.setTranslationX(v, -1 * (float) (-Math.cos(rot * mTranslationRatio * mDegToRad) + 1) * mCircleOffset);

        float scale = 1.0f - Math.abs(parentHalfHeight - halfHeight - y) * mScalingRatio;
        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
    }
}
