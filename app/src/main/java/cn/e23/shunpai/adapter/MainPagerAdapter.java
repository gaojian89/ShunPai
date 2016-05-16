package cn.e23.shunpai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.e23.shunpai.fragment.BaseListFragment;

/**
 * Created by jian on 2016/4/28.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private ArrayList<BaseListFragment> fragments;
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        fragments = new ArrayList<BaseListFragment>();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    public ArrayList<BaseListFragment> getFragments() {
        return fragments;
    }


    public void notify(ArrayList<BaseListFragment> fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}
