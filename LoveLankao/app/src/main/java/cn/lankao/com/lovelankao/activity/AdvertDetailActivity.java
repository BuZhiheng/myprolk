package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.viewcontroller.AdvertDetailController;
/**
 * Created by BuZhiheng on 2016/4/1.
 */
public class AdvertDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertdetail);
        new AdvertDetailController(this);
    }
}
