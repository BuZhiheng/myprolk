package cn.lankao.com.lovelankao.viewcontroller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.JsonElement;

import java.util.List;

import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.TopActivity;
import cn.lankao.com.lovelankao.adapter.TopAdapter;
import cn.lankao.com.lovelankao.model.JuheApiResult;
import cn.lankao.com.lovelankao.model.Top;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.GsonUtil;
import cn.lankao.com.lovelankao.utils.OkHttpUtil;
import cn.lankao.com.lovelankao.widget.OnRvScrollListener;
import cn.lankao.com.lovelankao.widget.ProDialog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by BuZhiheng on 2016/4/18.
 */
public class TopActivityController implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private final String url = "http://www.tngou.net/api/top/list?rows=";
    private TopActivity context;
    private RecyclerView rv;
    private SwipeRefreshLayout refresh;
    private TopAdapter adapter;
    private ProgressDialog dialog;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoadMore = true;
    public TopActivityController(TopActivity context){
        this.context = context;
        initView();
        initData();
    }

    private void initData() {
        String finalUrl;
        if (isRefresh){
            finalUrl = url+ CommonCode.RV_ITEMS_COUT+"&page="+1;
        }else{
            finalUrl = url+CommonCode.RV_ITEMS_COUT+"&page="+page;
        }
        OkHttpUtil.get(finalUrl, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onNext(String s) {
                JuheApiResult res = GsonUtil.jsonToObject(s, JuheApiResult.class);
                if (res != null) {
                    try {
                        JsonElement tngou = res.getTngou();
                        List<Top> data = GsonUtil.jsonToList(tngou, Top.class);
                        if (isRefresh){
                            adapter.setData(data);
                        }else{
                            adapter.addData(data);
                        }
                        canLoadMore = true;
                        adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                        dialog.dismiss();
                    } catch (Exception e) {
                        refresh.setRefreshing(false);
                    }
                }
            }
        });
    }

    private void initView() {
        dialog = ProDialog.getProDialog(context);
        dialog.show();
        context.setContentView(R.layout.activity_top);
        context.findViewById(R.id.iv_top_back).setOnClickListener(this);
        adapter = new TopAdapter(context);
        refresh = (SwipeRefreshLayout)context.findViewById(R.id.srl_top_activity);
        refresh.setOnRefreshListener(this);
        refresh.setRefreshing(true);
        rv = (RecyclerView) context.findViewById(R.id.rv_top_activity);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRvScrollListener() {
            @Override
            public void toBottom() {
                if (canLoadMore) {
                    isRefresh = false;
                    canLoadMore = false;
                    page++;
                    initData();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_top_back:
                context.finish();
                break;
        }
    }
}
