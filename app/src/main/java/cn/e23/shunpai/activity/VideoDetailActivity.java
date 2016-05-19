package cn.e23.shunpai.activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import cn.e23.shunpai.R;
import cn.e23.shunpai.utils.ScreenSize;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.CenterLayout;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by jian on 2016/5/16.
 */
public class VideoDetailActivity extends DetailActivity implements MediaController.OnFullScreenListener{
    private VideoView videoView;
    private ProgressBar progressBar;
    private MediaController control;
    private FrameLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        hideTitleLayout();
        initViews();
        playFunction();
    }

    private void initViews() {
        layout = (FrameLayout) findViewById(R.id.activity_video_detail_layout);
        videoView = (VideoView) findViewById(R.id.activity_video_detail_video);
//        progressBar = (ProgressBar) findViewById(R.id.activity_video_detail_progress);
        control = new MediaController(this, true, layout);
        control.setOnFullScreenListener(this);
        videoView.setMediaController(control);
        control.setVisibility(View.GONE);
    }

    private void playFunction() {

        setVideoFullScreen(isVideoFullScreen);
        if(video != null && !TextUtils.isEmpty(video.getVideo_url())) {
            videoView.setVideoPath(video.getVideo_url());
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.seekTo(0);
                }
            });
        }
    }

    @Override
    protected void setVideoFullScreen(boolean isFullScreen) {
        this.isVideoFullScreen = isFullScreen;
        if(isFullScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
            CenterLayout.LayoutParams params = new CenterLayout.LayoutParams(ScreenSize.getScreenWidth(this), ScreenSize.getScreenHeight(this),0, 0);
            videoView.setLayoutParams(params);
            bottomLayout.setVisibility(View.GONE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
            CenterLayout.LayoutParams params = new CenterLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenSize.getScreenWidth(this)/4 * 3,0,0);
            videoView.setLayoutParams(params);
            bottomLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected View getVideo() {
        return inflater.inflate(R.layout.activity_video_detail, null);
    }

    @Override
    protected boolean isInitVitamio() {
        return true;
    }

    @Override
    public void onFullScreen() {
        setVideoFullScreen(!isVideoFullScreen);
    }
}
