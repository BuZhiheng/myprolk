package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;

/**
 * Created by BuZhiheng on 2016/4/3.
 */
public class MsgBoardAdapter extends RecyclerView.Adapter<MsgBoardAdapter.MyViewHolder> {
    private Context context;
    public static List<Comment> data = new ArrayList<>();
    public MsgBoardAdapter(Context context) {
        this.context = context;
    }
    public void addData(Comment data) {
        this.data.add(data);
    }
    public void setData(List<Comment> data) {
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.activity_msg_board_item, parent, false));
        return holder;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment comment = data.get(position);
        holder.tvNickname.setText(comment.getUsername());
        holder.tvContent.setText(comment.getContent());
        holder.tvTime.setText(comment.getCreatedAt());
        if(TextUtil.isNull(comment.getUserPhotoUrl())){
            x.image().bind(holder.imageView, CommonCode.APP_ICON, BitmapUtil.getOptionByRadius(20));
        } else {
            x.image().bind(holder.imageView, comment.getUserPhotoUrl(), BitmapUtil.getOptionByRadius(20));
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvNickname;
        TextView tvContent;
        TextView tvTime;
        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_msg_board_item_photo);
            tvNickname = (TextView) view.findViewById(R.id.tv_msg_board_item_nickname);
            tvContent = (TextView) view.findViewById(R.id.tv_msg_board_item_content);
            tvTime = (TextView) view.findViewById(R.id.tv_msg_board_item_time);
        }
    }
}