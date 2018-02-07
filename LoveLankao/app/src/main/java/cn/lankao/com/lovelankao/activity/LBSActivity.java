package cn.lankao.com.lovelankao.activity;

import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.viewcontroller.LBSActivityController;

/**
 * Created by BuZhihieng on 2016/3/31.
 */
public class LBSActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lbs);
        new LBSActivityController(this);
    }
}
