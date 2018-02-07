package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;

import cn.lankao.com.lovelankao.base.BaseActivity;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.viewcontroller.ChatRoomController;
/**
 * Created by BuZhiheng on 2016/4/3.
 */
public class ChatRoomActivity extends BaseActivity {
    private ChatRoomController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        controller = new ChatRoomController(this);
    }
    @Override
    protected void onDestroy() {
        controller.onDestory();
        super.onDestroy();
    }
}