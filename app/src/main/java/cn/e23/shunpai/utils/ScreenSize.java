package cn.e23.shunpai.utils;
import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by jian on 2016/4/28.
 */
public class ScreenSize {

    public static final DisplayMetrics getScreenMetrics(Activity activity) {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        return dpMetrics;
    }
    public static final int getScreenWidth(Activity activity) {
        DisplayMetrics dpMetrics = getScreenMetrics(activity);
        return dpMetrics.widthPixels;
    }
    public static final int getScreenHeight(Activity activity) {
        DisplayMetrics dpMetrics = getScreenMetrics(activity);
        return dpMetrics.heightPixels;
    }

}
