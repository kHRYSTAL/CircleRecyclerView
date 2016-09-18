package me.khrystal.library.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/15
 * update time:
 * email: 723526676@qq.com
 */
public class RotateXScaleYViewMode implements ItemViewMode {

    private float mScaleRatio = 0.001f;
    private float mRontationRatio = 0.2f;
    private int last;
    private int first;

    public RotateXScaleYViewMode(){}

    public RotateXScaleYViewMode(float scaleRatio, float rontationRatio) {
        mScaleRatio = scaleRatio;
        mRontationRatio = rontationRatio;
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

        last = ((LinearLayoutManager) parent.getLayoutManager()).findLastVisibleItemPosition();
        first = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();

        float factor = (1 / (float) (last - first));
        if (rot > 0)
            tilt(v, rot * (rot % factor == 0 ? mRontationRatio : rot % factor) * factor);
        else
            tilt(v, -rot * (rot % factor == 0 ? mRontationRatio : rot % factor) * factor);

    }


    private void tilt(View v, float rot) {
        float degree = rot * (last - first);
        if (degree > 90) degree = 90;
        if (degree < -90) degree = -90;
        ViewCompat.setRotationX(v, degree);
    }
}
