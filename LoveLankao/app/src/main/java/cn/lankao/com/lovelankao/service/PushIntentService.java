package cn.lankao.com.lovelankao.service;
import android.content.Context;
import android.util.Log;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import cn.lankao.com.lovelankao.utils.ToastUtil;
/**
 * Created by buzhiheng on 2017/6/18.
 */
public class PushIntentService extends GTIntentService {
    public PushIntentService() {
    }
    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
    }
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
}