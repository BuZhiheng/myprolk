package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.MySquareMsgAdapter;
import cn.lankao.com.lovelankao.ipresenter.ISquareMsgPresenter;
import cn.lankao.com.lovelankao.iview.ISquareMsgView;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.SquareMsgPresenter;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class SquareMsgActivity extends AppCompatActivity implements ISquareMsgView{
    private ISquareMsgPresenter presenter;
    @BindView(R.id.rv_square_msg)
    RecyclerView rvMsg;
    private MySquareMsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msg);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("我的消息");
        presenter = new SquareMsgPresenter(this);
        rvMsg.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MySquareMsgAdapter(this);
        rvMsg.setAdapter(adapter);
        presenter.getMyMsg();
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
    @Override
    public void setComment(List<Comment> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}