package cn.e23.shunpai.activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import cn.e23.shunpai.R;
import cn.e23.shunpai.adapter.CommentListAdapter;
import cn.e23.shunpai.base.MActivity;
import cn.e23.shunpai.constant.Constant;
import cn.e23.shunpai.http.JsonArrayCallBack;
import cn.e23.shunpai.model.Comment;
import cn.e23.shunpai.model.Video;
import cn.e23.shunpai.view.DividerItemDecoration;
import cn.e23.shunpai.view.LoadMoreView;
import okhttp3.Call;

public abstract class DetailActivity extends MActivity implements View.OnClickListener{
    public static final String VIDEO = "VIDEO";
    protected Video video;
    protected RelativeLayout videoLayout;
    protected ImageView imgBack;
    protected ImageView imgShare;
    protected TextView tvComment;
    protected boolean isVideoFullScreen = false;
    protected PullToRefreshRecyclerView mPtrrv;
    protected CommentListAdapter adapter;
    protected String commentid;
    protected int pageNum = 1;
    protected TextView headerTitle;
    protected TextView headerNum;
    protected TextView emptyTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private void init() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get(VIDEO) != null) {
            video = (Video) bundle.get(VIDEO);
            commentid = "content_" + video.getCatid()+ "-" + video.getId() + "-1";
        }
        videoLayout = (RelativeLayout) findViewById(R.id.activity_detail_video_parent);
        View videoView = getVideo();
        if(videoView != null) {
            videoLayout.addView(videoView);
        }
        imgBack = (ImageView) findViewById(R.id.activity_detail_back);
        imgBack.setOnClickListener(this);
        imgShare = (ImageView) findViewById(R.id.activity_detail_bottom_share);
        tvComment = (TextView) findViewById(R.id.activity_detail_bottom_comment);
        imgShare.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        mPtrrv = (PullToRefreshRecyclerView)findViewById(R.id.activity_detail_list);
        mPtrrv.setSwipeEnable(true);
        LoadMoreView loadMoreView = new LoadMoreView(this, mPtrrv.getRecyclerView());
        loadMoreView.setLoadmoreString(getString(R.string.loading));
        loadMoreView.setLoadMorePadding(100);
        adapter = new CommentListAdapter(this);
        mPtrrv.setLayoutManager(new LinearLayoutManager(this));
        mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        View headerView = View.inflate(this, R.layout.detail_list_header, null);
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        headerTitle = (TextView) headerView.findViewById(R.id.detail_list_header_title);
        headerNum = (TextView) headerView.findViewById(R.id.detail_list_header_num);
        emptyTitle = (TextView) emptyView.findViewById(R.id.detail_list_empty_title);
        if(video != null) {
            headerTitle.setText(video.getTitle());
            emptyTitle.setText(video.getTitle());
        }
        mPtrrv.addHeaderView(headerView);
        mPtrrv.setEmptyView(emptyView);
        mPtrrv.setLoadMoreFooter(loadMoreView);
        mPtrrv.setAdapter(adapter);
        mPtrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                refresh(true);
            }
        });
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                refresh(false);
            }
        });
        mPtrrv.onFinishLoading(true, false);
        refresh(false);

    }

    @Override
    protected View getCenterView() {
        return inflater.inflate(R.layout.activity_detail, null);
    }

    @Override
    protected void back() {
        if(isVideoFullScreen) {
            setVideoFullScreen(!isVideoFullScreen);
            return;
        }
        goBack();
    }

    @Override
    protected void next() {

    }

    @Override
    protected View getBottomView() {
        return inflater.inflate(R.layout.activiry_detail_bottom, null);
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }
    protected abstract View getVideo();
    protected abstract void setVideoFullScreen(boolean isFullScreen);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_detail_back:
                back();
                break;
            case R.id.activity_detail_bottom_share:

                break;
            case R.id.activity_detail_bottom_comment:

                break;
        }
    }
    public void refresh(final boolean isLoadMore) {
        OkHttpUtils
                .get()
                .url(Constant.URL_BANNER)
                .addParams("m","comment")
                .addParams("c","index")
                .addParams("a","init")
                .addParams("ios","1")
                .addParams("commentid", commentid)
                .addParams("page", Integer.toString(pageNum))
                .build()
                .execute(new JsonArrayCallBack<Comment>() {
                    @Override
                    public void onError(Call call, Exception e) {
                        if(adapter != null) {
                            int num = adapter.getItemCount();
                            if(num > 0) {
                                headerNum.setText("共有" + num + "条评论");
                            }else {
                                headerNum.setText("暂无评论");
                            }
                        }
                    }

                    @Override
                    public void onResponse(Object response) {
                        try {
                            if(response != null) {
                                List<Comment> list = (List<Comment>) response;
                                if(isLoadMore) {
                                    adapter.addItemLast(list);
                                } else {
                                    adapter.setComments(list);
                                }
                                pageNum++;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(adapter != null) {
                            int num = adapter.getItemCount();
                            if(num > 0) {
                                headerNum.setText("共有" + num + "条评论");
                            }else {
                                headerNum.setText("暂无评论");
                            }
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if(mPtrrv == null)
                            return;
                        if(isLoadMore) {
                            mPtrrv.onFinishLoading(true, false);
                        }else {
                            mPtrrv.setOnRefreshComplete();
                            mPtrrv.onFinishLoading(true, false);
                        }
                    }
                });
    }
}
