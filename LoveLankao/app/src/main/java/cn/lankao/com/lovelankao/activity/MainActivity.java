package cn.lankao.com.lovelankao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.igexin.sdk.PushManager;

import cn.lankao.com.lovelankao.service.PushIntentService;
import cn.lankao.com.lovelankao.service.PushService;
import cn.lankao.com.lovelankao.viewcontroller.MainActivityController;
import cn.lankao.com.lovelankao.R;

public class MainActivity extends AppCompatActivity {
    private boolean canExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushManager.getInstance().initialize(this.getApplicationContext(), PushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), PushIntentService.class);
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
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
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