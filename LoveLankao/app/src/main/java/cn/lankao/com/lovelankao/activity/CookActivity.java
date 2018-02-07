package cn.lankao.com.lovelankao.activity;

import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.viewcontroller.CookActivityController;

/**
 * Created by BuZhiheng on 2016/4/19.
 */
public class CookActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CookActivityController(this);
    }
}
