package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.CommentActivity;
import cn.lankao.com.lovelankao.activity.LoginActivity;
import cn.lankao.com.lovelankao.activity.SquareActivity;
import cn.lankao.com.lovelankao.activity.SquarePersonalActivity;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.utils.WindowUtils;
import cn.lankao.com.lovelankao.widget.FlowLayout;
import cn.lankao.com.lovelankao.widget.MyDialog;
/**
 * Created by BuZhiheng on 2016/4/3.
 */
public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.MyViewHolder> {
    private Context context;
    private List<Square> data;
    public SquareAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }
    public void addData(Square data) {
        this.data.add(data);
    }
    public void setData(List<Square> data) {
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.activity_square_item, parent, false));
        return holder;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Square square = data.get(position);
        if (square.getObjectId() == null){
            return;
        }
        holder.tvNickname.setText(square.getNickName());
        holder.tvTime.setText(square.getCreatedAt());
        holder.tvLikeTimes.setText(square.getLikeTimes() == null ? "0" : square.getLikeTimes() + "");
        holder.tvClickTimes.setText(square.getClickTimes() == null ? "0" : square.getClickTimes() + "");
        holder.tvUserType.setText(TextUtil.getVipString(square.getSquareUserType()));
        String content = square.getSquareContent();
        if (content != null && content.length() > 100){
            content = content.substring(0,80)+"...(全文)";
        }
        if(TextUtils.isEmpty(square.getSquareTitle())){
            holder.tvContent.setText(content);
        } else {
            holder.tvContent.setText(square.getSquareTitle()+content);
        }
        if (!TextUtil.isNull(square.getUserPhoto())){
            BitmapUtil.loadImageCircle(context,holder.ivPhoto, square.getUserPhoto());
        } else {
            BitmapUtil.loadImageCircle(context,holder.ivPhoto, CommonCode.APP_ICON);
        }
        holder.flPhoto.removeAllViews();
        if (square.getSquarePhoto1() != null){
            setFlowImg(holder.flPhoto,square.getSquarePhoto1().getFileUrl());
        }
        if (square.getSquarePhoto2() != null){
            setFlowImg(holder.flPhoto,square.getSquarePhoto2().getFileUrl());
        }
        if (square.getSquarePhoto3() != null){
            setFlowImg(holder.flPhoto,square.getSquarePhoto3().getFileUrl());
        }
        final String nickname = PrefUtil.getString(CommonCode.SP_USER_NICKNAME,"");
        holder.ivLikeTimes.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_square_liketimes));
        if (!TextUtil.isNull(nickname) && !TextUtil.isNull(square.getLikeUsers()) && square.getLikeUsers().contains(nickname)){
            holder.ivLikeTimes.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_square_liketimesc));
        } else {
            holder.ivLikeTimes.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_square_liketimes));
        }
        if (square.getCommentTimes() == null){
            holder.tvCommentTimes.setText("评论 0");
        } else {
            holder.tvCommentTimes.setText("评论 " + square.getCommentTimes());
        }
        holder.llLikeTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()){
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                if (square.getLikeUsers() == null || !square.getLikeUsers().contains(nickname)) {
                    String likeUsers = square.getLikeUsers() == null ? nickname : nickname + "," + square.getLikeUsers();
                    square.setLikeUsers(likeUsers);
                    holder.ivLikeTimes.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_square_liketimesc));
                    final int like = square.getLikeTimes() == null ? 1 : square.getLikeTimes() + 1;
                    holder.tvLikeTimes.setText(like + "");
                    square.increment("likeTimes");
                    square.update(square.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });
                    square.setLikeTimes(like);
                } else {
                    ToastUtil.show("您已经点过赞了");
                }
            }
        });
        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                square.increment("clickTimes");
                square.update(square.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                    }
                });
                Intent intent = new Intent(context, SquareActivity.class);
                intent.putExtra(CommonCode.INTENT_COMMON_STRING, square.getObjectId());
                context.startActivity(intent);
            }
        });
        holder.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()){
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                    return;
                }
                Comment comment = new Comment();
                comment.setCommentFrom(CommonCode.INTENT_COMMENT_FROM_SQUARE);
                comment.setPostId(square.getObjectId());
                comment.setPostUserId(square.getUserId());
                comment.setPostContent(square.getSquareContent());
                Intent intent = new Intent(context,CommentActivity.class);
                intent.putExtra(CommonCode.INTENT_COMMON_OBJ,comment);
                context.startActivity(intent);
            }
        });
        holder.fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtil.isNull(square.getUserId())){
                    Intent intent = new Intent(context, SquarePersonalActivity.class);
                    intent.putExtra(CommonCode.SP_USER_USERID,square.getUserId());
                    intent.putExtra(CommonCode.SP_USER_NICKNAME,square.getNickName());
                    context.startActivity(intent);
                }
            }
        });
        if (!TextUtil.isNull(square.getUserId()) && square.getUserId().equals(PrefUtil.getString(CommonCode.SP_USER_USERID,""))){
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.getAlertDialog(context, "提示", "确认删除?", true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            square.delete(square.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        data.remove(square);
                                        notifyDataSetChanged();
                                        ToastUtil.show("删除成功");
                                    }
                                }
                            });
                        }
                    });
                }
            });
        } else {
            holder.tvDelete.setVisibility(View.GONE);
        }
    }
    private void setFlowImg(FlowLayout flowLayout,String url){
        View flowView = LayoutInflater.from(context).inflate(R.layout.activity_square_item_flowimg,null,false);
        ImageView ivFlow = flowView.findViewById(R.id.iv_square_item_photo);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivFlow.getLayoutParams();
        params.weight = WindowUtils.getWindowsWidth()/3;
        ivFlow.setLayoutParams(params);
        BitmapUtil.loadImageNormal(context,ivFlow, url);
        flowLayout.addView(flowView);
    }
    private boolean isLogin(){
        String nickname = PrefUtil.getString(CommonCode.SP_USER_NICKNAME, "");
        if (TextUtil.isNull(nickname)){
            ToastUtil.show("请先登录");
            return false;
        }
        return true;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout fl;
        ImageView ivPhoto;
        TextView tvNickname;
        TextView tvTime;
        TextView tvContent;
        TextView tvUserType;
        ImageView ivLikeTimes;
        TextView tvCommentTimes;
        TextView tvLikeTimes;
        TextView tvClickTimes;
        FlowLayout flPhoto;
        LinearLayout llComment;
        LinearLayout llLikeTimes;
        LinearLayout llContent;

        TextView tvDelete;
        public MyViewHolder(View view) {
            super(view);
            fl = (FrameLayout) view.findViewById(R.id.fl_square_item_personal);
            ivPhoto = (ImageView) view.findViewById(R.id.iv_square_item_photo);
            ivLikeTimes = (ImageView) view.findViewById(R.id.iv_square_item_liketimes);
            tvNickname = (TextView) view.findViewById(R.id.tv_square_item_nickname);
            tvTime = (TextView) view.findViewById(R.id.tv_square_item_time);
            tvContent = (TextView) view.findViewById(R.id.tv_square_item_content);
            tvUserType = (TextView) view.findViewById(R.id.tv_square_item_usertype);
            tvLikeTimes = (TextView) view.findViewById(R.id.tv_square_item_liketimes);
            tvClickTimes = (TextView) view.findViewById(R.id.tv_square_item_clicktimes);
            tvCommentTimes = (TextView) view.findViewById(R.id.tv_square_item_commenttimes);
            flPhoto = (FlowLayout) view.findViewById(R.id.fl_square_item_photo);
            llLikeTimes = (LinearLayout) view.findViewById(R.id.ll_square_item_liketimes);
            llComment = (LinearLayout) view.findViewById(R.id.ll_square_item_comment);
            llContent = (LinearLayout) view.findViewById(R.id.ll_square_item_content);
            tvDelete = (TextView) view.findViewById(R.id.tv_square_item_delete);
        }
    }
}