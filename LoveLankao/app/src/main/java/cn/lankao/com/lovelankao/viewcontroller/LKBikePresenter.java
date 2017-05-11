package cn.lankao.com.lovelankao.viewcontroller;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.ipresenter.ILKBikePresenter;
import cn.lankao.com.lovelankao.iview.ILKBikeView;
import cn.lankao.com.lovelankao.model.PublicBike;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class LKBikePresenter implements ILKBikePresenter {
    private ILKBikeView view;
    public LKBikePresenter(ILKBikeView view){
        this.view = view;
    }
    @Override
    public void getBike() {
        BmobQuery<PublicBike> query = new BmobQuery();
        query.order("-updatedAt");
        query.addWhereGreaterThan("bikeLat", 0);
        query.setLimit(150);
        query.findObjects(new FindListener<PublicBike>() {
            @Override
            public void done(List<PublicBike> list, BmobException e) {
                if (list != null && list.size() > 0){
                    for (int i=0;i<list.size();i++){
                        PublicBike bike = list.get(i);
                        CoordinateConverter converter  = new CoordinateConverter();
                        converter.from(CoordinateConverter.CoordType.COMMON);
                        //sourceLatLng待转换坐标
                        converter.coord(new LatLng(bike.bikeLat,bike.bikeLng));
                        LatLng desLatLng = converter.convert();
                        bike.bikeLat = (float) desLatLng.latitude;
                        bike.bikeLng = (float) desLatLng.longitude;
                        view.setMapMarker(bike);
                    }
                }
            }
        });
    }
}