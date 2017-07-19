package com.lzm.knittinghelp2.helpers;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

public class Utils {
    public static int dp2px(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);

    }

    public static void setBackgroundAndBorder(View view, int backgroundColor, int borderColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setCornerRadius(5);
        gradientDrawable.setStroke(1, borderColor);
        view.setBackground(gradientDrawable);
    }
}
