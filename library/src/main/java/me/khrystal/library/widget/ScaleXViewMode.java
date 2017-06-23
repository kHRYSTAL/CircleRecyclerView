package me.khrystal.library.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import me.khrystal.library.R;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/15
 * update time:
 * email: 723526676@qq.com
 */
public class ScaleXViewMode implements ItemViewMode {

    private float mScaleRatio = 0.001f;

    public ScaleXViewMode(){}

    public ScaleXViewMode(float scaleRatio) {
        mScaleRatio = scaleRatio;
    }

    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfWidth = v.getWidth() * 0.5f;
        float parentHalfWidth = parent.getWidth() * 0.5f;
        float x = v.getX();
        float rot = parentHalfWidth - halfWidth - x;
        float scale = 1.0f - Math.abs(rot) * mScaleRatio;
        boolean isCenter = (boolean) v.getTag(R.string.tag_is_center);
        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
    }
}
