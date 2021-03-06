package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import cn.lankao.com.lovelankao.utils.StatusBarUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.MainActivityController;
import cn.lankao.com.lovelankao.R;
public class MainActivity extends AppCompatActivity {
    private boolean canExit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_green), 0);
        new MainActivityController(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canExit) {
                finish();
                return true;
            }
            canExit = true;
            ToastUtil.show("再次点击退出");
            new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                        canExit = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}