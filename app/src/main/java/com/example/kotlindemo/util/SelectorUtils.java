package com.example.kotlindemo.util;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * @author yao
 */
public class SelectorUtils {

    /**
     * 获取View的shape
     * @param solidColor 颜色
     * @param radius 圆角
     * @return
     */
    public static GradientDrawable getShape(int solidColor, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(solidColor);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    /**
     * 获取默认View的selector
     * @param solidDefaultColor 默认颜色
     * @param solidPressedColor 按压颜色
     * @param radius 圆角
     * @return
     */
    public static StateListDrawable getSelector(int solidDefaultColor, int solidPressedColor, float radius) {
        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[]{android.R.attr.state_pressed},
                getShape(solidPressedColor, radius));
        listDrawable.addState(new int[]{},
                getShape(solidDefaultColor, radius));
        return listDrawable;
    }
}
