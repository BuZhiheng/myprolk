package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.viewcontroller.AllBusinessActivityController;
/**
 * Created by BuZhiheng on 2016/3/30.
 */
public class AllBusinessActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AllBusinessActivityController(this);
    }
}
