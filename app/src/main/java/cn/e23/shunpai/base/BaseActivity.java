package cn.e23.shunpai.base;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import java.lang.ref.WeakReference;
import java.util.List;
import cn.e23.shunpai.R;
import cn.e23.shunpai.application.App;

/**
 * gaojian
 * */
public class BaseActivity extends FragmentActivity {
    private WeakReference<Activity> reference;
    private boolean isCanBack=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = ((App) getApplication()).addActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((App) getApplication()).removeActivity(reference);
    }
    protected void goBack() {
        this.finish();
        overridePendingTransition(R.anim.unhold, R.anim.unfade);
    }


    public boolean isCanBack() {
        return isCanBack;
    }
    public void setCanBack(boolean isCanBack) {
        this.isCanBack = isCanBack;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isCanBack()){
            goBack();
        }
    }


    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
