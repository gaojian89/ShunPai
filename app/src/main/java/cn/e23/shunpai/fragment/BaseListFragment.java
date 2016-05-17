package cn.e23.shunpai.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huewu.pla.lib.XListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import cn.e23.shunpai.R;
import cn.e23.shunpai.activity.DetailActivity;
import cn.e23.shunpai.activity.VRDetailActivity;
import cn.e23.shunpai.activity.VideoDetailActivity;
import cn.e23.shunpai.adapter.StaggeredAdapter;
import cn.e23.shunpai.base.BaseFragment;
import cn.e23.shunpai.http.JsonArrayCallBack;
import cn.e23.shunpai.model.Video;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseListFragment#} factory method to
 * create an instance of this fragment.
 */
public abstract class BaseListFragment extends BaseFragment {


    protected XListView listView;
    protected View view;
    protected StaggeredAdapter adapter;
    public BaseListFragment() {
        // Required empty public constructor
    }
    protected int pageNum = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base, container, false);
        init();
        return view;
    }
    protected void init() {
        listView = (XListView) view.findViewById(R.id.fragment_base_list);
        listView.setPullLoadEnable(true);
        adapter = new StaggeredAdapter(mActivity);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getmInfos() != null && adapter.getmInfos().size() > 0) {
                    Intent intent = new Intent();


                    Video video = adapter.getmInfos().get(position - 1);
                    if(video != null) {
                        if(video.getCatid().equals(VRFragment.CATID)) {
                            intent.setClass(mActivity, VRDetailActivity.class);
                        }else {
                            intent.setClass(mActivity, VideoDetailActivity.class);
                        }
                        Bundle bundle  = new Bundle();
                        bundle.putSerializable(DetailActivity.VIDEO, video);
                        intent.putExtras(bundle);
                        mActivity.startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    }

                }
            }
        });
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                refresh(false);
            }

            @Override
            public void onLoadMore() {
                refresh(true);
            }
        });
    }
    public void refresh(final boolean isLoadMore) {
        OkHttpUtils
                .get()
                .url(getUrl())
                .addParams("m","content")
                .addParams("c","index")
                .addParams("a","json")
                .addParams("catid", getCatid())
                .addParams("page", Integer.toString(pageNum))
                .build()
                .execute(new JsonArrayCallBack<Video>() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        try {
                            if(response != null) {
                                List<Video> list = (List<Video>) response;
                                if(isLoadMore) {
                                    adapter.addItemLast(list);
                                } else {
                                    adapter.refreshItems(list);
                                }
                                pageNum++;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if(listView == null)
                            return;
                        if(isLoadMore) {
                            listView.stopLoadMore();
                        }else {
                            listView.stopRefresh();
                        }
                    }
                });
    }
    protected abstract String getUrl();
    protected abstract String getCatid();

}
