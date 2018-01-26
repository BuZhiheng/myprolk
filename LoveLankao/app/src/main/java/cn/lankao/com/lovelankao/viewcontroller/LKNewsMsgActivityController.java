package cn.lankao.com.lovelankao.viewcontroller;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.LKNewsMsgActivity;
import cn.lankao.com.lovelankao.model.LanKaoNews;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.BitmapUtil;

/**
 * Created by BuZhiheng on 2016/4/20.
 */
public class LKNewsMsgActivityController implements View.OnClickListener {
    private LKNewsMsgActivity context;
    private TextView tvTitle,tvFromTime,tvContent;
    private ImageView ivPhoto;
    private LanKaoNews news;
    public LKNewsMsgActivityController(LKNewsMsgActivity context) {
        this.context = context;
        initVeiw();
        initData();
    }
    private void initVeiw() {
        context.setContentView(R.layout.activity_lknews_msg);
        tvTitle = (TextView) context.findViewById(R.id.tv_lknewsmsg_title);
        tvFromTime = (TextView) context.findViewById(R.id.tv_lknewsmsg_fromtime);
        tvContent = (TextView) context.findViewById(R.id.tv_lknewsmsg_content);
        ivPhoto = (ImageView) context.findViewById(R.id.iv_lknewsmsg_photo);
        context.findViewById(R.id.iv_lknewsmsg_back).setOnClickListener(this);
    }
    private void initData() {
        Intent intent = context.getIntent();
        if (intent != null){
            news = (LanKaoNews) intent.getSerializableExtra(CommonCode.INTENT_ADVERT_TYPE);
            if (news != null){
                tvTitle.setText(news.getNewsTitle());
                tvFromTime.setText(news.getNewsFrom()+"   "+news.getNewsTime());
                tvContent.setText(news.getNewsContent());
                if (news.getNewsImg() != null){
                    BitmapUtil.loadImageNormal(context,ivPhoto,news.getNewsImg());
                } else {
                    ivPhoto.setVisibility(View.GONE);
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_lknewsmsg_back:
                context.finish();
                break;
        }
    }
}
