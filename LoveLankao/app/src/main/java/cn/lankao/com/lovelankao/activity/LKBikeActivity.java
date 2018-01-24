package cn.lankao.com.lovelankao.activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.ipresenter.ILKBikePresenter;
import cn.lankao.com.lovelankao.ipresenter.ILKPeoplePresenter;
import cn.lankao.com.lovelankao.iview.ILKBikeView;
import cn.lankao.com.lovelankao.iview.ILKPeopleView;
import cn.lankao.com.lovelankao.model.AdvertNormal;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.model.PublicBike;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.LKBikePresenter;
import cn.lankao.com.lovelankao.viewcontroller.LKPeoplePresenter;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class LKBikeActivity extends AppCompatActivity implements ILKBikeView {
    private ILKBikePresenter presenter;
    @BindView(R.id.map_bike)
    MapView mapView;
    private BaiduMap map;
    private BitmapDescriptor bitmap;
    private MarkerOptions option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_bike);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("兰考公共自行车");
        ((TextView)findViewById(R.id.tv_titlebar_right)).setText("用车须知");
        map = mapView.getMap();
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        mapView.setLogoPosition(LogoPosition.logoPostionleftTop);
        //定位到兰考位置
        LatLng ll = new LatLng(34.828593,114.827743);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
                .zoom(15)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        map.setMapStatus(mMapStatusUpdate);
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_public_bike);
        option = new MarkerOptions().zIndex(0).period(10).icon(bitmap).animateType(MarkerOptions.MarkerAnimateType.none);
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundler = marker.getExtraInfo();
                if (bundler != null){
                    final PublicBike bike = (PublicBike) bundler.getSerializable(CommonCode.INTENT_COMMON_OBJ);
                    if(bike != null){
                        Intent intent;
                        String latlon = bike.bikeLat+","+bike.bikeLng;
                        try {
                            Uri mUri = Uri
                                    .parse("geo:"+latlon+"?q="+bike.bikeAddress+"站点");
                            intent = new Intent(Intent.ACTION_VIEW, mUri);
                            startActivity(intent);
                        } catch (Exception e) {
                        }
                    }
                }
                return false;
            }
        });

        presenter = new LKBikePresenter(this);
        presenter.getBike();
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_right:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(CommonCode.INTENT_ADVERT_TITLE, "用车须知");
                intent.putExtra(CommonCode.INTENT_SETTING_URL, PrefUtil.getString(CommonCode.SP_SET_BIKE_NEWS,""));
                startActivity(intent);
                break;
        }
    }
    @Override
    public void showToast(String toast) {
    }
    @Override
    public void setMapMarker(PublicBike bike) {
        if (bike == null || bike.bikeLat == 0){
            return;
        }
        LatLng ll = new LatLng(bike.bikeLat, bike.bikeLng);
        Bundle b = new Bundle();
        b.putSerializable(CommonCode.INTENT_COMMON_OBJ, bike);
        map.addOverlay(option.position(ll)).setExtraInfo(b);
    }
}