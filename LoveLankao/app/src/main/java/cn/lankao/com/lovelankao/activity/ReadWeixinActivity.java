package cn.lankao.com.lovelankao.activity;

import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.viewcontroller.ReadActivityController;

/**
 * Created by BuZhiheng on 2016/4/18.
 */
public class ReadWeixinActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ReadActivityController(this);
    }
}
