package cn.lankao.com.lovelankao.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
/**
 * Created by buzhiheng on 2017/5/2.
 */
public class RoadActivity extends AppCompatActivity {
    @Bind(R.id.map_road)
    MapView mapView;
    private BaiduMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ((TextView) findViewById(R.id.tv_titlebar_title)).setText("兰考路况/人口分布");
        map = mapView.getMap();
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        mapView.setLogoPosition(LogoPosition.logoPostionleftTop);
        //定位到兰考位置
        LatLng ll = new LatLng(34.838341,114.829863);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(ll)
                .zoom(17)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        map.setMapStatus(mMapStatusUpdate);
        map.setBaiduHeatMapEnabled(true);
        map.setTrafficEnabled(true);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
}