package cn.e23.shunpai.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import cn.e23.shunpai.R;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by jian on 2016/5/16.
 */
public class VideoDetailActivity extends DetailActivity{

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        hideTitleLayout();
        playFunction();
    }

    private void playFunction() {
        videoView = (VideoView) findViewById(R.id.activity_video_detail_video);
        if(video != null && !TextUtils.isEmpty(video.getVideo_url())) {
            videoView.setVideoPath(video.getVideo_url());
            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }
    }







    @Override
    protected void setVideoFullScreen(boolean isFullScreen) {

    }

    @Override
    protected View getVideo() {
        return inflater.inflate(R.layout.activity_video_detail, null);
    }

    @Override
    protected boolean isInitVitamio() {
        return true;
    }
}
