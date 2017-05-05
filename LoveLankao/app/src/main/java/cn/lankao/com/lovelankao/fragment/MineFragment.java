package cn.lankao.com.lovelankao.fragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.xutils.x;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.AdvertDetailActivity;
import cn.lankao.com.lovelankao.activity.LoginActivity;
import cn.lankao.com.lovelankao.activity.SettingActivity;
import cn.lankao.com.lovelankao.activity.SquareMsgActivity;
import cn.lankao.com.lovelankao.activity.WZCityActivity;
import cn.lankao.com.lovelankao.activity.WebViewActivity;
import cn.lankao.com.lovelankao.iview.IMineFrmView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.MineFragmentController;
/**
 * Created by BuZhiheng on 2016/3/30.
 */
public class MineFragment extends Fragment implements IMineFrmView, View.OnClickListener{
    private MineFragmentController presenter = new MineFragmentController(this);
    private View view;
    @Bind(R.id.tv_minefrm_nickname)
    TextView tvNickName;
    @Bind(R.id.tv_minefrm_phone)
    TextView tvPhone;
    @Bind(R.id.tv_minefrm_jifen)
    TextView tvJifen;
    @Bind(R.id.iv_minefrm_photo)
    ImageView photo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }
    private void initView() {
        view.findViewById(R.id.ll_minefrm_user_msg).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_needpartner).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_tuijian).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_mypartner).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_aboutus).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_setting).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_msg).setOnClickListener(this);
        view.findViewById(R.id.fl_minefrm_wz).setOnClickListener(this);
        presenter.initUser();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    @Override
    public void exit() {
        getActivity().finish();
    }
    @Override
    public void setPhoto(String fileUrl) {
        x.image().bind(photo, fileUrl, BitmapUtil.getOptionCommonRadius());
    }
    @Override
    public void setUserMsg(MyUser user) {
        tvNickName.setText(user.getNickName());
        tvPhone.setText(user.getMobile());
        tvJifen.setText(user.getCoupon() + "");
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_minefrm_user_msg:
                if (TextUtil.isNull(PrefUtil.getString(CommonCode.SP_USER_USERID, ""))) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fl_minefrm_msg:
                if (TextUtil.isNull(PrefUtil.getString(CommonCode.SP_USER_USERID, ""))) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentMsg = new Intent(getActivity(), SquareMsgActivity.class);
                    startActivity(intentMsg);
                }
                break;
            case R.id.fl_minefrm_needpartner:
                toWebView(PrefUtil.getString(CommonCode.SP_SET_PARTNERURL, ""), "我要合作");
                break;
            case R.id.fl_minefrm_tuijian:
                toAdvert(CommonCode.ADVERT_TUIJIAN, "今日推荐");
                break;
            case R.id.fl_minefrm_mypartner:
                toAdvert(CommonCode.ADVERT_HEZUO, "合作商家");
                break;
            case R.id.fl_minefrm_aboutus:
                toWebView(PrefUtil.getString(CommonCode.SP_SET_ABOUTUSURL, ""), "关于我们");
                break;
            case R.id.fl_minefrm_setting:
                if (TextUtil.isNull(PrefUtil.getString(CommonCode.SP_USER_USERID, ""))) {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.fl_minefrm_wz:
                Intent intentWz = new Intent(getActivity(), WZCityActivity.class);
                startActivity(intentWz);
                break;
            default:
                break;
        }
    }
    private void toAdvert(int code, String title) {
        Intent intent = new Intent(getActivity(), AdvertDetailActivity.class);
        intent.putExtra(CommonCode.INTENT_ADVERT_TITLE, title);
        intent.putExtra(CommonCode.INTENT_ADVERT_TYPE, code);
        startActivity(intent);
    }
    private void toWebView(String url, String title) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(CommonCode.INTENT_ADVERT_TITLE, title);
        intent.putExtra(CommonCode.INTENT_SETTING_URL, url);
        startActivity(intent);
    }

    public void initData() {
        presenter.initUser();
    }
}