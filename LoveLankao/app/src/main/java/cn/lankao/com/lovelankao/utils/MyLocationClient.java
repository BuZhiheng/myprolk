package cn.lankao.com.lovelankao.utils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import cn.lankao.com.lovelankao.base.LApplication;
/**
 * Created by BuZhiheng on 2016/4/6.
 */
public class MyLocationClient {
    private static LocationClient locationClient;
    public static void locSingle(final MyLocationListener locationListener){
        locationClient = new LocationClient(LApplication.getCtx());
        initLocation();
        locationClient.start();
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (locationClient != null){
                    locationClient.stop();
                    locationClient = null;
                }
                if (locationListener != null){
                    locationListener.onLocSuccess(bdLocation);
                }
//                ToastUtil.show(bdLocation.getAddrStr());
            }
        });
    }
    public static void loc(final MyLocationListener locationListener){
        if (locationClient == null){
            locationClient = new LocationClient(LApplication.getCtx());
        }
        initLocation();
        locationClient.start();
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (locationListener != null){
                    locationListener.onLocSuccess(bdLocation);
                }
            }
        });
    }
    public static void stopLoc(){
        if (locationClient != null){
            if (locationClient.isStarted()){
                locationClient.stop();
                locationClient = null;
            }
        }
    }
    private static void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 3000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationClient.setLocOption(option);
    }
    public interface MyLocationListener{
        void onLocSuccess(BDLocation bdLocation);
    }
}