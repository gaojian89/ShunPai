package cn.e23.shunpai.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by jian on 2016/5/3.
 */
public class BaseFragment extends Fragment{
    protected BaseActivity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }
}
