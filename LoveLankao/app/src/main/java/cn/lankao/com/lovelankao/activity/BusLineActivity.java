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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.BusLineAdapter;
import cn.lankao.com.lovelankao.model.BusLine;
/**
 * Created by buzhiheng on 2017/5/2.
 */
public class BusLineActivity extends AppCompatActivity {
    @BindView(R.id.rv_bus_line)
    RecyclerView rvLine;
    private BusLineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_line);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("选择线路");
        rvLine.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BusLineAdapter(this);
        rvLine.setAdapter(adapter);
        BmobQuery<BusLine> query = new BmobQuery<>();
        query.order("line");
        query.findObjects(new FindListener<BusLine>() {
            @Override
            public void done(List<BusLine> list, BmobException e) {
                if (list != null){
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
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