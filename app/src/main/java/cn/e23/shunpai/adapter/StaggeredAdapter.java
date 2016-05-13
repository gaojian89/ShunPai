package cn.e23.shunpai.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

import cn.e23.shunpai.R;
import cn.e23.shunpai.model.Video;
import cn.e23.shunpai.utils.DpPxUtils;
import cn.e23.shunpai.utils.ScreenSize;
import cn.e23.shunpai.utils.inject.Injector;

/**
 * Created by jian on 2016/5/4.
 */
public class StaggeredAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<Video> mInfos;

    public StaggeredAdapter(Context context) {
        this.mContext = context;
        this.mInfos = new LinkedList<Video>();
    }

    @Override
    public Object getItem(int position) {
        return mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.video_info_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.video_info_item_img);
            holder.textView = (TextView) convertView.findViewById(R.id.video_info_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            Video video = mInfos.get(position);

            int width = (ScreenSize.getScreenWidth((Activity) mContext) - DpPxUtils.dp2px(16))/ 2;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            if(!TextUtils.isEmpty(video.getWidth()) && !TextUtils.isEmpty(video.getHeight())) {
                double imageWidth = Double.parseDouble(video.getWidth());
                double imageHeight =  Double.parseDouble(video.getHeight());
                height = (int)(width/(imageWidth/imageHeight));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            holder.imageView.setLayoutParams(params);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.textView.setText(video.getTitle());
            holder.textView.setLayoutParams(params2);
            //显示图片的配置
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.pic_default_list_common)
//                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            ImageLoader.getInstance().displayImage(video.getThumb(), holder.imageView, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    public void addItemLast(List<Video> datas) {
        mInfos.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void addItemTop(List<Video> datas) {
        for (Video info : datas) {
            mInfos.addFirst(info);
        }
        this.notifyDataSetChanged();
    }

    public void refreshItems(List<Video> datas) {
        mInfos.clear();
        mInfos.addAll(datas);
        this.notifyDataSetChanged();
    }

    public LinkedList<Video> getmInfos() {
        return mInfos;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
