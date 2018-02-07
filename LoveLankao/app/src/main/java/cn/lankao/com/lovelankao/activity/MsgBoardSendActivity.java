package cn.lankao.com.lovelankao.activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.widget.MyDialog;

/**
 * Created by buzhiheng on 2017/6/25.
 */
public class MsgBoardSendActivity extends BaseActivity {
    @BindView(R.id.et_msg_board_send)
    EditText editText;
    @BindView(R.id.iv_msg_board_send_check)
    ImageView ivCheck;
    private boolean isCheck = false;
    private String nickName = "";
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_board_send);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("留言板");
        dialog = MyDialog.getNoticeDialog(this,"请稍后...");
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_msg_board_send:
                send();
                break;
            case R.id.ll_msg_board_send_check:
                check();
                break;
        }
    }
    private void check() {
        if (isCheck){
            isCheck = false;
            ivCheck.setImageResource(R.drawable.ic_check);
        } else {
            isCheck = true;
            ivCheck.setImageResource(R.drawable.ic_checked);
            nickName = "匿名留言";
        }
    }
    private void send() {
        String content = editText.getText().toString();
        if (TextUtil.isNull(content)){
            ToastUtil.show("请输入内容!");
            return;
        }
        if (content.length() > 50){
            ToastUtil.show("留言内容不能超过50个字!");
            return;
        }
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostId(CommonCode.COMMENT_MSG_BOARD_ID);
        if (MyUser.isLogin()){
            comment.setUserId(PrefUtil.getString(CommonCode.SP_USER_USERID, ""));
            if (isCheck){
                comment.setUsername(nickName);
            } else {
                comment.setUsername(PrefUtil.getString(CommonCode.SP_USER_NICKNAME,""));
            }
            comment.setUserPhotoUrl(PrefUtil.getString(CommonCode.SP_USER_PHOTO,""));
        } else {
            comment.setUsername("游客");
        }
        dialog.show();
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    ToastUtil.show("留言成功!");
                    dialog.dismiss();
                    Intent intent = new Intent();
                    setResult(CommonCode.INTENT_COMMON_ACTIVITY_CODE,intent);
                    finish();
                } else {
                    dialog.dismiss();
                    ToastUtil.show("留言失败");
                }
            }
        });
    }
}