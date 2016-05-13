package cn.e23.shunpai.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.e23.shunpai.R;

/**
 * gaojian
 * */
public abstract class MActivity extends BaseActivity{

    protected LayoutInflater inflater;
    protected View centerView, bottomView;
    protected FrameLayout centerLayout;
    protected RelativeLayout titleLayout;
    protected RelativeLayout bottomLayout;
    private RelativeLayout.LayoutParams layoutParams;
    protected ImageView btnBack,btnNext,imgTitle;
    protected TextView tvBack,tvNext,tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_m);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        initBaseView();
    }
    private void initBaseView() {
        inflater = LayoutInflater.from(this);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        initMainView();
    }
    private void initMainView() {
        centerLayout = (FrameLayout) findViewById(R.id.layout_center);
        titleLayout = (RelativeLayout) findViewById(R.id.layout_title);
        bottomLayout = (RelativeLayout) findViewById(R.id.layout_bottom);
        btnBack = (ImageView) findViewById(R.id.layout_title_btn_back);
        btnNext = (ImageView) findViewById(R.id.layout_title_btn_next);
        imgTitle = (ImageView) findViewById(R.id.layout_title_img);
        tvBack = (TextView) findViewById(R.id.layout_title_tv_back);
        tvNext = (TextView) findViewById(R.id.layout_title_tv_next);
        tvTitle = (TextView) findViewById(R.id.layout_title_tv_title);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        centerView = getCenterView();
        bottomView = getBottomView();
        if(centerView != null) {
            centerLayout.setVisibility(View.VISIBLE);
            centerLayout.addView(centerView,layoutParams);
        } else {
            centerLayout.setVisibility(View.GONE);
        }
        if(bottomView != null) {
            bottomLayout.setVisibility(View.VISIBLE);
            bottomLayout.addView(bottomView,layoutParams);

        } else {
            bottomLayout.setVisibility(View.GONE);
        }
    }
    public void hideTitleLayout() {
        titleLayout.setVisibility(View.GONE);
    }
    public void showTitleLayout() {
        titleLayout.setVisibility(View.VISIBLE);
    }

    public void showBottomLayout() {
        bottomLayout.setVisibility(View.VISIBLE);
    }

    public void hideBottomLayout() {
        bottomLayout.setVisibility(View.GONE);
    }
    public void showBtnBack() {
        tvBack.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
    }
    public void showTvBack(String str) {
        tvBack.setVisibility(View.VISIBLE);
        tvBack.setText(str);
        btnBack.setVisibility(View.GONE);
    }
    public void hideBackView() {
        tvBack.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
    }
    public void showBtnNext() {
        tvNext.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
    public void showTvNext(String str) {
        tvNext.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.GONE);
        tvNext.setText(str);
    }
    public void hideNextView() {
        tvNext.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
    }
    public void showTitleText(String str) {
        tvTitle.setText(str);
        tvTitle.setVisibility(View.VISIBLE);
        imgTitle.setVisibility(View.GONE);
    }
    public  void showTitleImg() {
        tvTitle.setVisibility(View.GONE);
        imgTitle.setVisibility(View.VISIBLE);
    }

    public void hideTitleView(){
        tvTitle.setVisibility(View.GONE);
        imgTitle.setVisibility(View.GONE);
    }

    /**
     * 点击back Button时执行
     */
    protected abstract void back();

    /**
     * 点击next Button时执行
     */
    protected abstract void next();

    /**
     * 获取中间显示视图，是本类的重点方法
     *
     * @return
     */
    protected abstract View getCenterView();

    /**
     * 获取底部显示视图，是本类的重点方法
     *
     * @return
     */
    protected abstract View getBottomView();
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    protected abstract boolean isFullScreen();

}
