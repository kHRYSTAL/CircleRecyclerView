package me.khrystal.circlerecyclerviewdemo;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/15
 * update time:
 * email: 723526676@qq.com
 */
public class ModeType {

    @ModeTypeChecker
    public static final int TYPE_CIRCLE = 1;
    @ModeTypeChecker
    public static final int TYPE_SCALEX = 2;
    @ModeTypeChecker
    public static final int TYPE_SCALEY = 3;
    @ModeTypeChecker
    public static final int TYPE_ROTATEXSCALEY = 4;
    @ModeTypeChecker
    public static final int TYPE_ROTETEYSCALEX = 5;
    @ModeTypeChecker
    public static final int TYPE_CIRCLE_NO_LOOP = 6;
    @ModeTypeChecker
    public static final int TYPE_HORIZONTAL_CIRCLE = 7;
    @ModeTypeChecker
    public static final int TYPE_CIRCLE_RTL = 8;
    @ModeTypeChecker
    public static final int TYPE_HORIZONTAL_CIRCLE_BTT = 9;


    @IntDef({TYPE_CIRCLE, TYPE_SCALEX, TYPE_SCALEY, TYPE_ROTETEYSCALEX,
            TYPE_ROTATEXSCALEY, TYPE_CIRCLE_NO_LOOP, TYPE_HORIZONTAL_CIRCLE,
            TYPE_CIRCLE_RTL, TYPE_HORIZONTAL_CIRCLE_BTT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ModeTypeChecker {
    }
}
