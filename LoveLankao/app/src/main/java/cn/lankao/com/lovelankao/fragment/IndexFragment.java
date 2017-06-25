package cn.lankao.com.lovelankao.fragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.BusActivity;
import cn.lankao.com.lovelankao.activity.ChatRoomActivity;
import cn.lankao.com.lovelankao.activity.CookActivity;
import cn.lankao.com.lovelankao.activity.JockActivity;
import cn.lankao.com.lovelankao.activity.LKBikeActivity;
import cn.lankao.com.lovelankao.activity.LKNewsActivity;
import cn.lankao.com.lovelankao.activity.LKPeopleActivity;
import cn.lankao.com.lovelankao.activity.MessageBoardActivity;
import cn.lankao.com.lovelankao.activity.RoadActivity;
import cn.lankao.com.lovelankao.adapter.BannerIndexHolder;
import cn.lankao.com.lovelankao.adapter.IndexServiceAdapter;
import cn.lankao.com.lovelankao.adapter.LKNewsAdapter;
import cn.lankao.com.lovelankao.ipresenter.IIndexPresenter;
import cn.lankao.com.lovelankao.iview.IIndexView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.LanKaoNews;
import cn.lankao.com.lovelankao.model.MainService;
import cn.lankao.com.lovelankao.model.Setting;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.IndexFragmentController;
import cn.lankao.com.lovelankao.widget.MyDialog;
/**
 * Created by BuZhiheng on 2016/5/11.
 */
public class IndexFragment extends Fragment implements IIndexView, View.OnClickListener{
    private IIndexPresenter presenter;
    private View view;
    @Bind(R.id.srl_index_frm)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.banner_indexfrm)
    ConvenientBanner banner;
    @Bind(R.id.rv_indexfrm_service)
    RecyclerView rvService;
    @Bind(R.id.rv_indexfrm_lknews)
    RecyclerView rvNews;
    @Bind(R.id.rv_indexfrm_lknews_header)
    RecyclerViewHeader header;
    private LKNewsAdapter adapterNews;
    private IndexServiceAdapter adapterService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        presenter = new IndexFragmentController(this);
        initView();
        return view;
    }
    private void initView() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        header.attachTo(rvNews);
        adapterNews = new LKNewsAdapter(getActivity());
        rvNews.setAdapter(adapterNews);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvService.setLayoutManager(manager);
        adapterService = new IndexServiceAdapter(getActivity());
        rvService.setAdapter(adapterService);
        view.findViewById(R.id.ll_indexfrm_more_chat).setOnClickListener(this);
        view.findViewById(R.id.ll_indexfrm_more_menu).setOnClickListener(this);
        view.findViewById(R.id.ll_indexfrm_more_bike).setOnClickListener(this);
        view.findViewById(R.id.ll_indexfrm_more_jock).setOnClickListener(this);
        view.findViewById(R.id.ll_indexfrm_more_road).setOnClickListener(this);
        view.findViewById(R.id.fl_indexfrm_more_news).setOnClickListener(this);
        view.findViewById(R.id.ll_indexfrm_more_lkpeople).setOnClickListener(this);
        view.findViewById(R.id.tv_indexfrm_msg_board).setOnClickListener(this);
        presenter.getData();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_indexfrm_more_news:
                Intent intentNews = new Intent(getActivity(), LKNewsActivity.class);
                startActivity(intentNews);
                break;
            case R.id.ll_indexfrm_more_chat:
                Intent intentChat = new Intent(getActivity(), ChatRoomActivity.class);
                startActivity(intentChat);
                break;
            case R.id.ll_indexfrm_more_menu:
                Intent intentMenu = new Intent(getActivity(), CookActivity.class);
                intentMenu.putExtra(CommonCode.INTENT_COOK_OR_FOOD, CommonCode.INTENT_COOK);
                startActivity(intentMenu);
                break;
            case R.id.ll_indexfrm_more_bike:
                Intent intentBike = new Intent(getActivity(), LKBikeActivity.class);
                startActivity(intentBike);
                break;
            case R.id.ll_indexfrm_more_jock:
                Intent intentJock = new Intent(getActivity(), JockActivity.class);
                startActivity(intentJock);
                break;
            case R.id.ll_indexfrm_more_lkpeople:
                Intent intentPeople = new Intent(getActivity(), LKPeopleActivity.class);
                startActivity(intentPeople);
                break;
            case R.id.ll_indexfrm_more_road:
                Intent intentRoad = new Intent(getActivity(), RoadActivity.class);
                startActivity(intentRoad);
                break;
            case R.id.tv_indexfrm_msg_board:
                Intent intentBoard = new Intent(getActivity(), MessageBoardActivity.class);
                startActivity(intentBoard);
                break;
            default:
                break;
        }
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    @Override
    public void setBannerAndNews(final List<LanKaoNews> banners,final List<LanKaoNews> news) {
        banner.setPages(
                new CBViewHolderCreator<BannerIndexHolder>() {
                    @Override
                    public BannerIndexHolder createHolder() {
                        return new BannerIndexHolder(banners.size());
                    }
                }, banners)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        banner.startTurning(5000);
        adapterNews.setData(news);
        adapterNews.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void setMainService(List<MainService> list) {
        adapterService.setData(list);
        adapterService.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void setRefreshStop() {
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void showNewVersion(Setting setting) {
        MyDialog.getAlertDialog(getActivity(), "发现新版本", setting.getAndroidUpdateLog(), false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(CommonCode.APP_URL);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }
}