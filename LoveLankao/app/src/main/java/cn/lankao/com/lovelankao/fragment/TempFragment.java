package cn.lankao.com.lovelankao.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.TempAdapter;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.Jock;
import cn.lankao.com.lovelankao.model.JuheApiResult;
import cn.lankao.com.lovelankao.utils.GsonUtil;
import cn.lankao.com.lovelankao.utils.OkHttpUtil;
import cn.lankao.com.lovelankao.widget.OnRvScrollListener;
import rx.Subscriber;
/**
 * Created by BuZhiheng on 2016/4/7.
 */
public class TempFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private final String url = "http://v.juhe.cn/joke/randJoke.php?&key=da46a2a9e5d5a3bfefb5694bfa0e04c1";
    private RecyclerView rv;
    private SwipeRefreshLayout refresh;
    private TempAdapter adapter;
    private boolean isRefresh = true;
    private boolean canLoadMore = true;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_jock, container, false);
        initView();
        initData();
        return view;
    }
    private void initView() {
        ((TextView)view.findViewById(R.id.tv_jock_title)).setText("首页");
        view.findViewById(R.id.iv_jock_back).setVisibility(View.GONE);
        adapter = new TempAdapter(getActivity());
        refresh = (SwipeRefreshLayout)view.findViewById(R.id.srl_jock_activity);
        refresh.setOnRefreshListener(this);
        rv = (RecyclerView) view.findViewById(R.id.rv_activity_jock);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRvScrollListener() {
            @Override
            public void toBottom() {
                if (canLoadMore) {
                    isRefresh = false;
                    canLoadMore = false;
                    initData();
                }
            }
        });
    }
    private void initData() {
        OkHttpUtil.get(url, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(String s) {
                JuheApiResult res = GsonUtil.jsonToObject(s, JuheApiResult.class);
                if (res.getError_code() == 0) {
                    try {
                        List<Jock> data = GsonUtil.jsonToList(res.getResult(), Jock.class);
                        if (isRefresh) {
                            adapter.setData(data);
                        } else {
                            adapter.addData(data);
                        }
                        canLoadMore = true;
                        adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                    } catch (Exception e) {
                        refresh.setRefreshing(false);
                    }
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        isRefresh = true;
        initData();
    }
}