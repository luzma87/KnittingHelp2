package com.lzm.knittinghelp2.helpers;

import android.content.Context;

public class Utils {
    public static int dp2px(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);

    }
}
