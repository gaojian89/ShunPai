package cn.e23.shunpai.utils;
import android.util.DisplayMetrics;

import cn.e23.shunpai.application.App;

/**
 * Created by jian on 2016/5/5.
 */
public class DpPxUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @return
     */

	public static int dp2px(float dpValue) {
		DisplayMetrics metric = new DisplayMetrics();
        App.getCurrentActivity().get().getWindowManager().getDefaultDisplay().getMetrics(metric);
		final float scale =metric.density;
		return (int) (dpValue * scale + 0.5f);
	}

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        DisplayMetrics metric = new DisplayMetrics();
        App.getCurrentActivity().get().getWindowManager().getDefaultDisplay().getMetrics(metric);
        final float scale =metric.density;
        return (int) (pxValue / scale + 0.5f);
    }
}
