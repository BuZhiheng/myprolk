package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.SquareAdapter;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.widget.OnRvScrollListener;
/**
 * Created by buzhiheng on 2017/6/16.
 */
public class SquarePersonalActivity extends BaseActivity {
    @BindView(R.id.rv_square_personal)
    RecyclerView recyclerView;
    @BindView(R.id.srl_square_personal)
    SwipeRefreshLayout refresh;
    private SquareAdapter adapter;
    private int cout = CommonCode.RV_ITEMS_COUT;
    private String userId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_personal);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        userId = getIntent().getStringExtra(CommonCode.SP_USER_USERID);
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText(getIntent().getStringExtra(CommonCode.SP_USER_NICKNAME));
        adapter = new SquareAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnRvScrollListener() {
            @Override
            public void toBottom() {
                cout += CommonCode.RV_ITEMS_COUT;
                getData();
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cout = CommonCode.RV_ITEMS_COUT;
                getData();
            }
        });
        getData();
    }
    private void getData(){
        BmobQuery<Square> query = new BmobQuery<>();
        query.setLimit(cout);
        query.addWhereEqualTo("userId",userId);
        query.order("-createdAt");//按事件排序
        query.findObjects(new FindListener<Square>() {
            @Override
            public void done(List<Square> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
                refresh.setRefreshing(false);
            }
        });
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
}