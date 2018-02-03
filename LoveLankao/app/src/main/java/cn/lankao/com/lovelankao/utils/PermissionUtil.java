package cn.lankao.com.lovelankao.utils;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by buzhiheng on 2016/10/20.
 */
public class PermissionUtil {
    public static final int REQUEST_CODE_PERMISSION = 1010;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkNoPermission(AppCompatActivity context, String permission){
        //没有权限,返回true
        int per = context.checkSelfPermission(permission);
        if (per != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkDismissPermissionWindow(AppCompatActivity context, String permission){
        //没有权限,用户点击不在提示,返回true
        if (!context.shouldShowRequestPermissionRationale(permission)) {
            return true;
        } else {
            context.requestPermissions(new String[]{permission}, REQUEST_CODE_PERMISSION);
        }
        return false;
    }
}