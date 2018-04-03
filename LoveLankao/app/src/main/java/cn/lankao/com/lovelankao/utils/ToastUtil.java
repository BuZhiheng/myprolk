package cn.lankao.com.lovelankao.utils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.base.LApplication;
/**
 * Created by BuZhiheng on 2016/4/1.
 */
public class ToastUtil {
    private static Toast toast;
    public static void show(String msg){
        if (toast == null){
            toast = new Toast(LApplication.getCtx());
        }
        View view = LayoutInflater.from(LApplication.getCtx()).inflate(R.layout.toast_tv, null);
        TextView tv = view.findViewById(R.id.toast);
        tv.setText(msg);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}