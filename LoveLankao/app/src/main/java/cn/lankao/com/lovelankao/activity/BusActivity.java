package cn.lankao.com.lovelankao.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import cn.lankao.com.lovelankao.ipresenter.IBusPresenter;
import cn.lankao.com.lovelankao.iview.IBusView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.Driver;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.viewcontroller.BusPresenter;
/**
 * Created by buzhiheng on 2017/5/2.
 */
public class BusActivity extends AppCompatActivity implements IBusView {
    private IBusPresenter presenter;
    @Bind(R.id.map_bus)
    MapView mapView;
    @Bind(R.id.tv_titlebar_right)
    TextView tvLine;
    @Bind(R.id.btn_bus_refresh)
    Button btnRefresh;
    private String line = "";
    private BaiduMap map;
    private BitmapDescriptor bitmap;
    private MarkerOptions option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        ButterKnife.bind(this);
        presenter = new BusPresenter(this);
        initView();
    }
    private void initView() {
        ((TextView)findViewById(R.id.tv_titlebar_title)).setText("兰考公交");
        ((TextView)findViewById(R.id.tv_titlebar_right)).setText("选择线路");
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
        option = new MarkerOptions().zIndex(0).period(10).icon(bitmap).animateType(MarkerOptions.MarkerAnimateType.none);
        presenter.getBus(line);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_bus_refresh:
                presenter.getBus(line);
                break;
            case R.id.tv_titlebar_right:
                Intent intent = new Intent(this,BusLineActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }
    @Override
    public void showToast(String toast) {
        ToastUtil.show(toast);
    }
    @Override
    public void setMapMarker(Driver driver) {
        if (driver == null || driver.carLat == 0){
            return;
        }
        LatLng ll = new LatLng(driver.carLat, driver.carLng);
        map.addOverlay(option.position(ll));
    }
    @Override
    public void clearMap() {
        map.clear();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                if (data == null){
                    return;
                }
                line = data.getStringExtra(CommonCode.INTENT_COMMON_STRING);
                presenter.getBus(line);
                tvLine.setText(line);
                btnRefresh.setText("刷新:"+line);
                break;
        }
    }
}