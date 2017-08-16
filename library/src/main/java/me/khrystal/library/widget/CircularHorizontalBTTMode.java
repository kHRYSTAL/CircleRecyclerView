package me.khrystal.library.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 17/8/16
 * update time:
 * email: 723526676@qq.com
 */

public class CircularHorizontalBTTMode implements ItemViewMode {

    private int mCircleOffset = 500;
    private float mDegToRad = 1.0f / 180.0f * (float) Math.PI;
    private float mScalingRatio = 0.001f;
    private float mTranslationRatio = 0.15f;
    private float mYOffset = 100;
    private boolean mUseRotation = false;

    public CircularHorizontalBTTMode(float yOffset, boolean useRotation) {
        this.mYOffset = yOffset;
        this.mUseRotation = useRotation;
    }

    public CircularHorizontalBTTMode(int circleOffset, float degToRad,
                                     float scalingRatio, float translationRatio,
                                     float yOffset, boolean useRotation) {
        mCircleOffset = circleOffset;
        mDegToRad = degToRad;
        mScalingRatio = scalingRatio;
        mTranslationRatio = translationRatio;
        mYOffset = yOffset;
        mUseRotation = useRotation;


    }


    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfWidth = v.getWidth() * 0.5f;
        float parentHalfWidth = parent.getWidth() * 0.5f;
        float x = v.getX();
        float rot = parentHalfWidth - halfWidth - x;
        if (mUseRotation) {
            ViewCompat.setPivotY(v, 0.0f);
            ViewCompat.setPivotX(v, halfWidth);
            ViewCompat.setRotation(v, -rot * 0.05f);
        }

        ViewCompat.setTranslationY(v, - 1 * (float) (-Math.cos(rot * mTranslationRatio * mDegToRad) + 1) * mCircleOffset + mYOffset);

        float scale = 1.0f - Math.abs(parentHalfWidth - halfWidth - x) * mScalingRatio;
        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
    }
}
