package me.khrystal.library.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/14
 * update time:
 * email: 723526676@qq.com
 */
public interface ItemViewMode {
    void applyToView(View v, RecyclerView parent);
}
