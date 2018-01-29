package cn.lankao.com.lovelankao.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.AdvertDetailActivity;
import cn.lankao.com.lovelankao.activity.AllBusinessActivity;
import cn.lankao.com.lovelankao.activity.LBSActivity;
import cn.lankao.com.lovelankao.adapter.MyAdapter;
import cn.lankao.com.lovelankao.ipresenter.IMainFrmPresenter;
import cn.lankao.com.lovelankao.iview.IMainFrmView;
import cn.lankao.com.lovelankao.model.AdvertNormal;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.MainFragmentController;
/**
 * Created by BuZhiheng on 2016/3/30.
 */
public class MainFragment extends Fragment implements IMainFrmView, View.OnClickListener{
    private IMainFrmPresenter presenter = new MainFragmentController(this);
    private View view;
    @BindView(R.id.srl_main_frm)
    SwipeRefreshLayout refresh;
    @BindView(R.id.appbar_main_frm)
    AppBarLayout appBarLayout;
    @BindView(R.id.rv_mainfrm_shop)
    RecyclerView rvShop;
    private MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }
    private void initView() {
        rvShop.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(getActivity());
        rvShop.setAdapter(adapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
        view.findViewById(R.id.ll_mainfrm_header_chihewanle).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_women).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_offer).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_zulin).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_friend).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_hunqing).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_fangchan).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_service).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_jingcailankao).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_other).setOnClickListener(this);
        view.findViewById(R.id.ll_mainfrm_header_mingqi).setOnClickListener(this);

        view.findViewById(R.id.tv_mainfrm_tehui).setOnClickListener(this);
        view.findViewById(R.id.tv_mainfrm_tuiian).setOnClickListener(this);
        view.findViewById(R.id.tv_mainfrm_toall).setOnClickListener(this);
        view.findViewById(R.id.fl_mainfrm_more).setOnClickListener(this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refresh.setEnabled(true);
                } else {
                    refresh.setEnabled(false);
                }
            }
        });
        presenter.getData();
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    @Override
    public void setData(List<AdvertNormal> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        refresh.setRefreshing(false);
    }

    @Override
    public void setRefreshStop() {
        refresh.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_mainfrm_toall:
                Intent intentLbs = new Intent(getActivity(), LBSActivity.class);
                startActivity(intentLbs);
                break;
            case R.id.fl_mainfrm_more:
                Intent intent = new Intent(getActivity(), AllBusinessActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_mainfrm_header_mingqi:
                toAdvert(CommonCode.ADVERT_MINGQI, "名企名商");
                break;
            case R.id.tv_mainfrm_tehui:
                toAdvert(CommonCode.ADVERT_TEHUI, "特惠不打烊");
                break;
            case R.id.tv_mainfrm_tuiian:
                toAdvert(CommonCode.ADVERT_TUIJIAN, "精品推荐");
                break;
            case R.id.ll_mainfrm_header_chihewanle:
                toAdvert(CommonCode.ADVERT_CHIHEWANLE, getString(R.string.text_mainfrm_chihewanle));
                break;
            case R.id.ll_mainfrm_header_women:
                toAdvert(CommonCode.ADVERT_WOMEN, getString(R.string.text_mainfrm_women));
                break;
            case R.id.ll_mainfrm_header_offer:
                toAdvert(CommonCode.ADVERT_OFFER, getString(R.string.text_mainfrm_offer));
                break;
            case R.id.ll_mainfrm_header_zulin:
                toAdvert(CommonCode.ADVERT_ZULIN, getString(R.string.text_mainfrm_zulin));
                break;
            case R.id.ll_mainfrm_header_friend:
                toAdvert(CommonCode.ADVERT_FRIEND, getString(R.string.text_mainfrm_friend));
                break;
            case R.id.ll_mainfrm_header_hunqing:
                toAdvert(CommonCode.ADVERT_HUNQING, getString(R.string.text_mainfrm_hunqing));
                break;
            case R.id.ll_mainfrm_header_fangchan:
                toAdvert(CommonCode.ADVERT_FANGCHAN, getString(R.string.text_mainfrm_fangchan));
                break;
            case R.id.ll_mainfrm_header_service:
                toAdvert(CommonCode.ADVERT_SERVICE, getString(R.string.text_mainfrm_service));
                break;
            case R.id.ll_mainfrm_header_jingcailankao:
                toAdvert(CommonCode.ADVERT_JINGCAILANKAO, getString(R.string.text_mainfrm_jingcai));
                break;
            case R.id.ll_mainfrm_header_other:
                toAdvert(CommonCode.ADVERT_OTHER, getString(R.string.text_mainfrm_other));
                break;
            default:
                break;
        }
    }
    private void toAdvert(int code,String title){
        Intent intent = new Intent(getActivity(), AdvertDetailActivity.class);
        intent.putExtra(CommonCode.INTENT_ADVERT_TITLE, title);
        intent.putExtra(CommonCode.INTENT_ADVERT_TYPE, code);
        startActivity(intent);
    }
}