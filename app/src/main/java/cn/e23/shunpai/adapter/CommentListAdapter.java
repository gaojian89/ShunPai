package cn.e23.shunpai.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.List;
import cn.e23.shunpai.R;
import cn.e23.shunpai.model.Comment;
import cn.e23.shunpai.utils.TimeUtils;

/**
 * Created by jian on 2016/5/12.
 */
public class CommentListAdapter extends PtrrvBaseAdapter<CommentListAdapter.ViewHolder>{
    private LinkedList<Comment> comments;
    public CommentListAdapter(Context context) {
        super(context);
        this.comments = new LinkedList<Comment>();
    }
    public void setComments(List<Comment> datas) {
        comments.clear();
        comments.addAll(datas);
        this.notifyDataSetChanged();
    }
    public void addItemLast(List<Comment> datas) {
        comments.addAll(datas);
        this.notifyDataSetChanged();
    }

    public LinkedList<Comment> getComments() {
        return comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comment_list_item, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(comments != null && comments.size() > 0) {
            Comment comment = comments.get(position);
            if(comment != null) {
                holder.tvName.setText(comment.getUsername());
                holder.tvContent.setText(comment.getContent());
                holder.tvTime.setText(TimeUtils.getTimeDisplay(Long.parseLong(comment.getCreat_at())));
            }
        }
    }
    @Override
    public int getItemCount()
    {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvContent;
        TextView tvTime;
        ImageView imgHeader;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.comment_list_item_name);
            tvContent = (TextView) itemView.findViewById(R.id.comment_list_item_content);
            tvTime = (TextView) itemView.findViewById(R.id.comment_list_item_time);
            imgHeader = (ImageView) itemView.findViewById(R.id.comment_list_item_header);
        }
    }
}
