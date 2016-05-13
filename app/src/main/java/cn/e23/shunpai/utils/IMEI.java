package cn.e23.shunpai.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import cn.e23.shunpai.application.App;

/**
 * Created by jian on 2016/4/27.
 */
public class IMEI {
    /**
     * 获取手机的IMEI号码
     *
     * @return
     */

    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei;

    }
}
