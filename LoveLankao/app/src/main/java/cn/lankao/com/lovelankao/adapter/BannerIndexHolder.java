package cn.lankao.com.lovelankao.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.holder.Holder;
import org.xutils.x;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.WebViewActivity;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.LanKaoNews;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
/**
 * Created by buzhiheng on 2017/4/25.
 */
public class BannerIndexHolder implements Holder<LanKaoNews> {
    private View view;
    private FrameLayout fLayout;
    private ImageView iv;
    private TextView tv;
    private TextView tvIndex;
    private int size;
    public BannerIndexHolder(int size){
        this.size = size;
    }
    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.activity_index_head,null);
        fLayout = (FrameLayout) view.findViewById(R.id.fl_indexfrm_head);
        iv = (ImageView) view.findViewById(R.id.iv_indexfrm_headview_photo);
        tv = (TextView) view.findViewById(R.id.tv_indexfrm_headview_title);
        tvIndex = (TextView) view.findViewById(R.id.tv_indexfrm_headview_index);
        return view;
    }
    @Override
    public void UpdateUI(final Context context, final int position, final LanKaoNews news) {
        x.image().bind(iv, news.getNewsImg(), BitmapUtil.getOptionCommon());
        tv.setText(news.getNewsTitle());
        tvIndex.setText((position + 1) + "/" + size);
        fLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(CommonCode.INTENT_ADVERT_TITLE, "");
                intent.putExtra(CommonCode.INTENT_SETTING_URL, news.getNewsFromUrl());
                intent.putExtra(CommonCode.INTENT_SHARED_DESC,news.getNewsTitle());
                if (news.getNewsPhoto() != null){
                    intent.putExtra(CommonCode.INTENT_SHARED_IMG, news.getNewsPhoto().getFileUrl());
                } else {
                    intent.putExtra(CommonCode.INTENT_SHARED_IMG, CommonCode.APP_ICON);
                }
                context.startActivity(intent);
            }
        });
    }
}