package me.khrystal.library.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/14
 * update time:
 * email: 723526676@qq.com
 */
public class CircularViewMode implements ItemViewMode {

    private static final int CIRCLE_OFFSET = 500;
    private static final float DEGTORAD = 1.0f / 180.0f * (float) Math.PI;
    private static final float SCALING_RATIO = 0.001f;
    private static final float TRANSLATION_RATIO = 0.09f;

    @Override
    public void applyToView(View v, RecyclerView parent) {
        float halfHeight = v.getHeight() * 0.5f;
        float parentHalfHeight = parent.getHeight() * 0.5f;
        float y = v.getY();
        float rot = parentHalfHeight - halfHeight - y;

        ViewCompat.setPivotX(v, 0.0f);
        ViewCompat.setPivotY(v, halfHeight);
        ViewCompat.setRotation(v, rot * 0.05f);
        ViewCompat.setTranslationX(v, (float)(-Math.cos(rot * TRANSLATION_RATIO * DEGTORAD) + 1) * CIRCLE_OFFSET);

        float scale = 1.0f - Math.abs(parentHalfHeight - halfHeight - y) * SCALING_RATIO;
        ViewCompat.setScaleX(v, scale);
        ViewCompat.setScaleY(v, scale);
    }
}
