package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.viewcontroller.AdvertDetailController;
/**
 * Created by BuZhiheng on 2016/4/1.
 */
public class AdvertDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertdetail);
        new AdvertDetailController(this);
    }
}
