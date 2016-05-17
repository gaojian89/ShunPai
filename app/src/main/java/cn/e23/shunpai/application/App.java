package cn.e23.shunpai.application;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.vov.vitamio.Vitamio;

/**
 * gaojian
 * */
public class App extends Application {
    private static List<WeakReference<Activity>> mActivities = new LinkedList<WeakReference<Activity>>();
    private HashMap<String, Object> cache = new HashMap<String, Object>();
    private static App instance = null;
    private boolean isAppRunning;
    public static String version="";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        OkHttpUtils.getInstance().setConnectTimeout(100000, TimeUnit.MILLISECONDS);
        Vitamio.isInitialized(getApplicationContext());
        initImageLoader();
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void initImageLoader() {
        /**
         * 初始话图片加载模块
         */

            File cacheDir = StorageUtils.getCacheDirectory(this);
            int cacheSize = 5 * 1024 * 1024;
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    this)
                    .threadPoolSize(3)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .memoryCache(new LruMemoryCache(cacheSize))
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .diskCacheSize(50 * 1024 * 1024)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs()
                    .build();
            ImageLoader.getInstance().init(config);

    }

    public static WeakReference<Activity> addActivity(Activity activity) {
        synchronized (mActivities) {
            WeakReference<Activity> reference = new WeakReference<Activity>(activity);
            mActivities.add(reference);
            return reference;
        }
    }

    public int getCurrentRunningActivitySize() {

        return mActivities.size();
    }

    public String getTopActivity() {

        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return (runningTaskInfos.get(0).topActivity).getClassName().toString();
        else
            return null;
    }
    public static WeakReference<Activity> getCurrentActivity() {
        return mActivities.get(mActivities.size() - 1);
    }

    public void removeActivity(WeakReference<Activity> reference) {
        synchronized (mActivities) {
            mActivities.remove(reference);
        }
    }

    public void exit() {
        synchronized (mActivities) {
            for (WeakReference<Activity> activityRef : mActivities) {
                Activity activity = activityRef.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            mActivities.clear();
        }
    }

    public static App getInstance() {
        if (instance == null) {
            throw new NullPointerException("app not create should be terminated!");
        }
        return instance;
    }

    public boolean isAppRunning() {
        return isAppRunning;
    }

    public void setAppRunning(boolean isAppRunning) {
        this.isAppRunning = isAppRunning;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        exit();
    }

}
