package cn.e23.shunpai.activity;

import android.view.View;

import cn.e23.shunpai.R;

/**
 * Created by jian on 2016/5/16.
 */
public class VideoDetailActivity extends DetailActivity{










    @Override
    protected void setVideoFullScreen(boolean isFullScreen) {

    }

    @Override
    protected View getVideo() {
        return inflater.inflate(R.layout.activity_video_detail, null);
    }
}
