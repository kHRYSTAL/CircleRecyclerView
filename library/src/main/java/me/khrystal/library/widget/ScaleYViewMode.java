package me.khrystal.library.widget;

import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/15
 * update time:
 * email: 723526676@qq.com
 */
public class ScaleYViewMode implements ItemViewMode{

    private float mScaleRatio = 0.001f;

    public ScaleYViewMode(){}

    public ScaleYViewMode(float scaleRatio) {
        mScaleRatio = scaleRatio;
    }

    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfHeight = v.getHeight() * 0.5f;
        float parentHalfHeight = parent.getHeight() * 0.5f;
        float y = v.getY();
        float rot = parentHalfHeight - halfHeight - y;

        float scale = 1.0f - Math.abs(rot) * mScaleRatio;
        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
    }
}
