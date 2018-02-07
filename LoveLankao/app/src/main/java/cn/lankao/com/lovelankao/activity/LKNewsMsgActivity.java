package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.viewcontroller.LKNewsMsgActivityController;
/**
 * Created by BuZhiheng on 2016/4/20.
 */
public class LKNewsMsgActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LKNewsMsgActivityController(this);
    }
}
