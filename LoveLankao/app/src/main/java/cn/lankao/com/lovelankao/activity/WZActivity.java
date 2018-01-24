package cn.lankao.com.lovelankao.activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.WzMsgAdapter;
import cn.lankao.com.lovelankao.ipresenter.IWzPresenter;
import cn.lankao.com.lovelankao.iview.IWzView;
import cn.lankao.com.lovelankao.model.JWzMsg;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.WzPresenter;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class WZActivity extends AppCompatActivity implements IWzView {
    private IWzPresenter presenter;
    @BindView(R.id.fl_wz_class)
    FrameLayout flClass;
    @BindView(R.id.fl_wz_engine)
    FrameLayout flEngine;
    @BindView(R.id.et_wz_no)
    EditText etNo;
    @BindView(R.id.et_wz_class)
    EditText etClass;
    @BindView(R.id.et_wz_engine)
    EditText etEngine;
    @BindView(R.id.rv_wz_msg)
    RecyclerView rvWz;
    private WzMsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wz_msg);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView) findViewById(R.id.tv_titlebar_title)).setText("违章查询");
        presenter = new WzPresenter(this,getIntent());
        rvWz.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WzMsgAdapter(this);
        rvWz.setAdapter(adapter);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_wz_sure:
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                presenter.getWz(etNo,etClass,etEngine);
                break;
        }
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    @Override
    public void setClassShow(String msg) {
        flClass.setVisibility(View.VISIBLE);
        etClass.setHint(msg);
    }
    @Override
    public void setEngineShow(String msg) {
        flEngine.setVisibility(View.VISIBLE);
        etEngine.setHint(msg);
    }
    @Override
    public void setWz(List<JWzMsg> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}