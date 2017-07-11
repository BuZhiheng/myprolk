package cn.lankao.com.lovelankao.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.MsgBoardAdapter;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.widget.OnRvScrollListener;
/**
 * Created by buzhiheng on 2017/6/24.
 */
public class MessageBoardActivity extends AppCompatActivity {
    @Bind(R.id.rv_msg_board)
    RecyclerView recyclerView;
    @Bind(R.id.srl_msg_board)
    SwipeRefreshLayout refresh;
    private MsgBoardAdapter adapter;
    private int cout = CommonCode.RV_ITEMS_COUT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_board);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("兰考留言板");
        ((TextView)findViewById(R.id.tv_titlebar_right)).setText("我要留言");
        adapter = new MsgBoardAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnRvScrollListener() {
            @Override
            public void toBottom() {
                super.toBottom();
                cout += cout;
                getData(cout);
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cout = CommonCode.RV_ITEMS_COUT;
                getData(cout);
            }
        });
        getData(cout);
    }
    private void getData(int cout) {
        BmobQuery<Comment> query = new BmobQuery();
        query.setLimit(cout);
        query.order("-createdAt");
        query.addWhereEqualTo("postId",CommonCode.COMMENT_MSG_BOARD_ID);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
                refresh.setRefreshing(false);
            }
        });
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_right:
                Intent intent = new Intent(this,MsgBoardSendActivity.class);
                startActivityForResult(intent, CommonCode.INTENT_COMMON_ACTIVITY_CODE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonCode.INTENT_COMMON_ACTIVITY_CODE){
            if (data != null){
                cout = CommonCode.RV_ITEMS_COUT;
                getData(cout);
            }
        }
    }
}