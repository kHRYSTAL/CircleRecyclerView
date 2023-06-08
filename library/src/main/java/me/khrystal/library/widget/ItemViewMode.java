package me.khrystal.library.widget;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

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
