package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.viewcontroller.ShopLocController;
/**
 * Created by dell on 2016/4/6.
 */
public class ShopLocationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_loc);
        new ShopLocController(this);
    }
}