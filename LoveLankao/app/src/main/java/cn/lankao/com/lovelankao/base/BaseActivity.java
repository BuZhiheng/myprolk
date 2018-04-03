package cn.lankao.com.lovelankao.base;
import android.os.Bundle;

import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.utils.StatusBarUtil;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;
/**
 * Created by HOREN on 2018/2/7.
 */
public class BaseActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_green), 0);
    }
}