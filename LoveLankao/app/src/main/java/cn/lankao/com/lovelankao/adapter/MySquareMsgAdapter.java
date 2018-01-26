package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.SquareActivity;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
/**
 * Created by BuZhiheng on 2016/4/3.
 */
public class MySquareMsgAdapter extends RecyclerView.Adapter<MySquareMsgAdapter.MyViewHolder> {
    private Context context;
    public static List<Comment> data = new ArrayList<>();
    public MySquareMsgAdapter(Context context) {
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
                .inflate(R.layout.activity_my_msg_item, parent, false));
        return holder;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Comment comment = data.get(position);
        if (TextUtil.isNull(comment.getUserPhotoUrl())){
            BitmapUtil.loadImageCircle(context,holder.iv, CommonCode.APP_ICON);
        } else {
            BitmapUtil.loadImageCircle(context,holder.iv, comment.getUserPhotoUrl());
        }
        holder.tvNickname.setText(comment.getUsername());
        if (!TextUtil.isNull(comment.getLastUserContent())){
            holder.tvReComment.setText(comment.getLastUserContent());
            holder.tvReComment.setVisibility(View.VISIBLE);
        } else {
            holder.tvReComment.setVisibility(View.GONE);
        }
        holder.tvComment.setText(comment.getContent());
        holder.tvTime.setText(comment.getCreatedAt());
        holder.tvUserType.setText(TextUtil.getVipString(comment.getUserType()));
        if (TextUtil.isNull(comment.getPostContent())){
            holder.tvPost.setVisibility(View.GONE);
        } else {
            if (comment.getPostContent().length() > 50){
                holder.tvPost.setText("(原文)"+comment.getPostContent().substring(0,39));
            } else {
                holder.tvPost.setText("(原文)" + comment.getPostContent());
            }
        }
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SquareActivity.class);
                intent.putExtra(CommonCode.INTENT_COMMON_STRING,comment.getPostId());
                context.startActivity(intent);
            }
        });
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        ImageView iv;
        TextView tvNickname;
        TextView tvUserType;
        TextView tvTime;
        TextView tvReComment;
        TextView tvComment;
        TextView tvPost;
        public MyViewHolder(View view) {
            super(view);
            ll = (LinearLayout) view.findViewById(R.id.ll_square_msg_content);
            iv = (ImageView) view.findViewById(R.id.iv_square_comment_photo);
            tvNickname = (TextView) view.findViewById(R.id.tv_square_comment_nickname);
            tvUserType = (TextView) view.findViewById(R.id.tv_square_comment_usertype);
            tvTime = (TextView) view.findViewById(R.id.tv_square_comment_time);
            tvReComment = (TextView) view.findViewById(R.id.tv_square_comment_recomment);
            tvComment = (TextView) view.findViewById(R.id.tv_square_comment_content);
            tvPost = (TextView) view.findViewById(R.id.tv_square_comment_post);
        }
    }
}