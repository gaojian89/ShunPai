package cn.e23.shunpai.activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import cn.e23.shunpai.R;
import cn.e23.shunpai.adapter.MainPagerAdapter;
import cn.e23.shunpai.application.App;
import cn.e23.shunpai.base.BaseListFragment;
import cn.e23.shunpai.base.MActivity;
import cn.e23.shunpai.fragment.HotFragment;
import cn.e23.shunpai.fragment.LiveFragment;
import cn.e23.shunpai.fragment.SceneFragment;
import cn.e23.shunpai.fragment.VRFragment;
import cn.e23.shunpai.utils.ScreenSize;

public class MainActivity extends MActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private TextView tvVR,tvHot,tvLive,tvScene;
    private View indicatorView;
    private ViewPager viewPager;
    private MainPagerAdapter pagerAdapter;
    private ArrayList<BaseListFragment> fragments;
    private VRFragment vrFragment;
    private HotFragment hotFragment;
    private LiveFragment liveFragment;
    private SceneFragment sceneFragment;
    private long exitTime = 0;
    private final long APP_EXIT_TIMER = 2000;
    private static final int INDEX_VR = 0;
    private static final int INDEX_HOT = 1;
    private static final int INDEX_LIVE = 2;
    private static final int INDEX_SCENE = 3;
    private int currentIndex = 0;
    private int screenWidth = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleText(getString(R.string.app_name));
        initData();
        initViews();
    }

    @Override
    public void hideTitleLayout() {
        super.hideTitleLayout();
    }

    @Override
    protected void back() {

    }

    @Override
    protected void next() {

    }

    @Override
    protected View getCenterView() {
        return inflater.inflate(R.layout.activity_main_center, null);
    }

    @Override
    protected View getBottomView() {
        return null;
    }

    private void initData() {
        screenWidth = ScreenSize.getScreenWidth(this);
    }

    private void initViews() {
        tvVR = (TextView) findViewById(R.id.main_act_tab_vr);
        tvHot = (TextView) findViewById(R.id.main_act_tab_hot);
        tvLive = (TextView) findViewById(R.id.main_act_tab_live);
        tvScene = (TextView) findViewById(R.id.main_act_tab_scene);
        indicatorView = (View) findViewById(R.id.main_act_tab_line);
        viewPager = (ViewPager) findViewById(R.id.main_act_pager);
        viewPager.setOffscreenPageLimit(4);
        pagerAdapter = new MainPagerAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        fragments = new ArrayList<BaseListFragment>();
        vrFragment = new VRFragment();
        hotFragment = new HotFragment();
        liveFragment = new LiveFragment();
        sceneFragment = new SceneFragment();
        fragments.clear();
        fragments.add(vrFragment);
        fragments.add(hotFragment);
        fragments.add(liveFragment);
        fragments.add(sceneFragment);
        pagerAdapter.notify(fragments);
        initTabLineWidth();
        changeTabUi(INDEX_VR);
        fragments.get(INDEX_VR).refresh(false);
        tvVR.setOnClickListener(this);
        tvLive.setOnClickListener(this);
        tvHot.setOnClickListener(this);
        tvScene.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }
    /**
     * 设置滑动条的宽度为屏幕的1/4(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) indicatorView.getLayoutParams();
        lp.width = screenWidth / 4;
        indicatorView.setLayoutParams(lp);
    }

    private void changeTabUi(int index) {
        switch (index) {
            case INDEX_VR:
                setTextColor(tvVR, true);
                setTextColor(tvHot, false);
                setTextColor(tvLive, false);
                setTextColor(tvScene, false);
                break;
            case INDEX_HOT:
                setTextColor(tvVR, false);
                setTextColor(tvHot, true);
                setTextColor(tvLive, false);
                setTextColor(tvScene, false);
                break;
            case INDEX_LIVE:
                setTextColor(tvVR, false);
                setTextColor(tvHot, false);
                setTextColor(tvLive, true);
                setTextColor(tvScene, false);
                break;
            case INDEX_SCENE:
                setTextColor(tvVR, false);
                setTextColor(tvHot, false);
                setTextColor(tvLive, false);
                setTextColor(tvScene, true);
                break;
        }
    }
    private void setTextColor(TextView tv, boolean isChecked) {
        if(isChecked) {
            tv.setTextColor(getResources().getColor(R.color.colorBlue));
        } else {
            tv.setTextColor(getResources().getColor(R.color.colorGray));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_act_tab_vr:
                if(currentIndex != INDEX_VR) {
                    viewPager.setCurrentItem(INDEX_VR);
                }
                break;
            case R.id.main_act_tab_hot:
                if(currentIndex != INDEX_HOT) {
                    viewPager.setCurrentItem(INDEX_HOT);
                }
                break;
            case R.id.main_act_tab_live:
                if(currentIndex != INDEX_LIVE) {
                    viewPager.setCurrentItem(INDEX_LIVE);
                }
                break;

            case R.id.main_act_tab_scene:
                if(currentIndex != INDEX_SCENE) {
                    viewPager.setCurrentItem(INDEX_SCENE);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) indicatorView.getLayoutParams();

        /**
         * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
         *
         */
        if(currentIndex == position) {
            lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
        } else if (currentIndex > position) {
            lp.leftMargin = (int) (-(1 - positionOffset) * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
        }
        indicatorView.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {
        changeTabUi(position);
        fragments.get(position).refresh(false);
        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > APP_EXIT_TIMER) {
                Toast.makeText(getApplicationContext(), getString(R.string.app_exit_notify_text), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                App.getInstance().setAppRunning(false);
                finish();
                App.getInstance().exit();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }
}
