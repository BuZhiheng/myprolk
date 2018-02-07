package cn.lankao.com.lovelankao.utils;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import java.util.List;
import cn.lankao.com.lovelankao.base.LApplication;
/**
 * Created by buzhiheng on 2016/10/28.
 */
public class SystemUtils {
    public static String MAP_BAIDU = "com.baidu.BaiduMap";
    public static String MAP_ALI = "com.autonavi.minimap";
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static boolean networkState(){
        ConnectivityManager cm = (ConnectivityManager) (LApplication.getCtx().getSystemService(Context.CONNECTIVITY_SERVICE));
        if (cm.getActiveNetworkInfo() != null){
            return cm.getActiveNetworkInfo().isAvailable();
            //连接成功返回true,否则返回false
        }
        return false;
    }
    //服务器返回的是百度反编译的经纬度,所以要转百度为国测局
    public static double[] bd09_To_Gcj02(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double tempLon = z * Math.cos(theta);
        double tempLat = z * Math.sin(theta);
        double[] gps = {tempLat,tempLon};
        return gps;
    }
    /**
     * 检查手机上是否安装了指定的软件
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilibleApk(String packageName){
        //获取packagemanager
        final PackageManager packageManager = LApplication.getCtx().getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                if (packageName != null && packageName.equals(packName)){
                    return true;
                }
            }
        }
        return false;
    }
}