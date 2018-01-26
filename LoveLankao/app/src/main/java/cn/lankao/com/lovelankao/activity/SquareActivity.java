package cn.lankao.com.lovelankao.activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.SquareActivityController;
/**
 * Created by BuZhiheng on 2016/4/4.
 */
public class SquareActivity extends AppCompatActivity {
    private SquareActivityController controller;
    @BindView(R.id.iv_square_item_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_square_item_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_square_item_time)
    TextView tvTime;
    @BindView(R.id.tv_square_item_title)
    TextView tvTitle;
    @BindView(R.id.tv_square_item_content)
    TextView tvContent;
    @BindView(R.id.tv_square_item_usertype)
    TextView tvUserType;
    @BindView(R.id.iv_square_item_photo1)
    public ImageView ivPhoto1;
    @BindView(R.id.iv_square_item_photo2)
    public ImageView ivPhoto2;
    @BindView(R.id.iv_square_item_photo3)
    public ImageView ivPhoto3;
    @BindView(R.id.iv_square_item_photo4)
    public ImageView ivPhoto4;
    @BindView(R.id.iv_square_item_photo5)
    public ImageView ivPhoto5;
    @BindView(R.id.iv_square_item_photo6)
    public ImageView ivPhoto6;
    @BindView(R.id.iv_square_item_liketimes)
    ImageView ivLikeTimes;
    @BindView(R.id.tv_square_item_commenttimes)
    TextView tvCommentTimes;
    @BindView(R.id.tv_square_item_liketimes)
    TextView tvLikeTimes;
    @BindView(R.id.tv_square_item_clicktimes)
    TextView tvClickTimes;
    @BindView(R.id.ll_square_item_liketimes)
    LinearLayout llLikes;
    @BindView(R.id.ll_square_item_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_square_item_photo1)
    public LinearLayout llPhoto1;
    @BindView(R.id.ll_square_item_photo2)
    public LinearLayout llPhoto2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_msg);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        controller = new SquareActivityController(this,getIntent());
    }
    public void setData(final Square square,String userImg,Drawable drawable){
        tvNickname.setText(square.getNickName());
        tvTime.setText(square.getCreatedAt());
        tvContent.setText(square.getSquareContent());
        tvLikeTimes.setText(square.getLikeTimes() == null ? "0" : square.getLikeTimes() + "");
        tvClickTimes.setText(square.getClickTimes() == null ? "0" : square.getClickTimes() + "");
        if (square.getSquareTitle() == null){
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(square.getSquareTitle());
            tvTitle.setVisibility(View.VISIBLE);
        }
        tvUserType.setText(TextUtil.getVipString(square.getSquareUserType()));
        BitmapUtil.loadImageCircle(this,ivPhoto, userImg);
        ivLikeTimes.setImageDrawable(drawable);
        llLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onLikeClick();
            }
        });
    }
    public void setLickIvTimes(Drawable drawable,String times){
        ivLikeTimes.setImageDrawable(drawable);
        tvLikeTimes.setText(times);
    }
    public void setIvPhoto(ImageView iv,String imgUrl){
        BitmapUtil.loadImageNormal(this,iv, imgUrl);
        iv.setVisibility(View.VISIBLE);
    }
    public void showToast(String toast){
        ToastUtil.show(toast);
    }
    public void toLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_square_back:
                finish();
                break;
            case R.id.tv_square_comment_writecomment:
            case R.id.tv_square_msg_comment:
            case R.id.ll_square_item_commenttimes:
                controller.checkComment();
                break;
        }
    }
    public void setVisibility(View v){
        v.setVisibility(View.VISIBLE);
    }
    public void toComment(Comment comment){
        Intent intent = new Intent(this,CommentActivity.class);
        intent.putExtra(CommonCode.INTENT_COMMON_OBJ,comment);
        startActivityForResult(intent, CommonCode.INTENT_COMMON_ACTIVITY_CODE);
    }
    public void clearCommentLL(){
        llComment.removeAllViews();
    }
    public void setCommentTimes(String s){
        tvCommentTimes.setText(s);
    }
    public void setComment(final Comment comment){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_square_comment,null);
        CommentHolder holder = new CommentHolder(view);
        if (TextUtil.isNull(comment.getUserPhotoUrl())){
            BitmapUtil.loadImageCircle(this,holder.ivPhoto, CommonCode.APP_ICON);
        } else {
            BitmapUtil.loadImageCircle(this,holder.ivPhoto, comment.getUserPhotoUrl());
        }
        holder.tvNickname.setText(comment.getUsername());
        holder.tvTime.setText(comment.getCreatedAt());
        if (!TextUtil.isNull(comment.getLastUserContent())){
            holder.tvReComment.setText(comment.getLastUserContent());
            holder.tvReComment.setVisibility(View.VISIBLE);
        }
        holder.tvToReComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.checkComment("回复(" + comment.getUsername() + "):" + comment.getContent(),comment.getUserId());
            }
        });
        holder.tvUserType.setText(TextUtil.getVipString(comment.getUserType()));
        holder.tvComment.setText(comment.getContent());
        llComment.addView(view);
    }
    class CommentHolder {
        ImageView ivPhoto;
        TextView tvToReComment;
        TextView tvNickname;
        TextView tvTime;
        TextView tvReComment;
        TextView tvComment;
        TextView tvUserType;
        public CommentHolder(View view){
            ivPhoto = (ImageView) view.findViewById(R.id.iv_square_comment_photo);
            tvToReComment = (TextView) view.findViewById(R.id.tv_square_comment_recomment);
            tvNickname = (TextView) view.findViewById(R.id.tv_square_comment_nickname);
            tvTime = (TextView) view.findViewById(R.id.tv_square_comment_time);
            tvReComment = (TextView) view.findViewById(R.id.tv_square_comment_recontent);
            tvComment = (TextView) view.findViewById(R.id.tv_square_comment_content);
            tvUserType = (TextView) view.findViewById(R.id.tv_square_comment_usertype);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onResult(resultCode,data);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}