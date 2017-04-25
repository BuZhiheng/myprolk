package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.ipresenter.ILKPeoplePresenter;
import cn.lankao.com.lovelankao.iview.ILKPeopleView;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.viewcontroller.LKPeoplePresenter;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class LKPeopleActivity extends AppCompatActivity implements ILKPeopleView{
    private ILKPeoplePresenter presenter;
    @Bind(R.id.map_lk_people)
    MapView mapView;
    private BaiduMap map;
    private BitmapDescriptor bitmap;
    private MarkerOptions option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lkpeople);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("兰考人");
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
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_common_map_meishi);
        option = new MarkerOptions().zIndex(0).period(10).icon(bitmap);
        presenter = new LKPeoplePresenter(this);
        presenter.getPeople();
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
    @Override
    public void showToast(String toast) {
    }
    @Override
    public void setMapMarker(MyUser user) {
        if (user == null || user.getUserLng() == 0){
            return;
        }
        LatLng ll = new LatLng(user.getUserLat(), user.getUserLng());
        map.addOverlay(option.position(ll));
    }
}