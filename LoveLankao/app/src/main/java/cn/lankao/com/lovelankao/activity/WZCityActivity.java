package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.adapter.WzCityAdapter;
import cn.lankao.com.lovelankao.ipresenter.IWzCityPresenter;
import cn.lankao.com.lovelankao.iview.IWzCityView;
import cn.lankao.com.lovelankao.model.JWzCitys;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.WzCityPresenter;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class WZCityActivity extends AppCompatActivity implements IWzCityView {
    private IWzCityPresenter presenter;
    @Bind(R.id.rv_wz_city)
    RecyclerView rvCity;
    private WzCityAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wz_city);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("城市列表");
        presenter = new WzCityPresenter(this);
        adapter = new WzCityAdapter(this);
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.setAdapter(adapter);
        presenter.getCitys();
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    @Override
    public void setCity(List<JWzCitys> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}