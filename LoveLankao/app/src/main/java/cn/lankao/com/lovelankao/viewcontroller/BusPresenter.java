package cn.lankao.com.lovelankao.viewcontroller;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.ipresenter.IBusPresenter;
import cn.lankao.com.lovelankao.iview.IBusView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.Driver;
import cn.lankao.com.lovelankao.utils.TextUtil;
/**
 * Created by buzhiheng on 2017/5/2.
 */
public class BusPresenter implements IBusPresenter {
    private IBusView view;
    public BusPresenter(IBusView view){
        this.view = view;
    }
    @Override
    public void getBus(String line) {
        BmobQuery<Driver> query = new BmobQuery();
        query.addWhereGreaterThan("carLat", 0);
        query.addWhereNotEqualTo("line", CommonCode.CAR_SERVICE_TYPE_TEXI);
        if (!TextUtil.isNull(line)){
            query.addWhereEqualTo("line",line);
        }
        query.addWhereEqualTo("carType",CommonCode.CAR_SERVICE_TYPE_WORK);
        query.setLimit(150);
        query.findObjects(new FindListener<Driver>() {
            @Override
            public void done(List<Driver> list, BmobException e) {
                view.clearMap();
                if (list != null && list.size() > 0) {
//                    view.showToast("获取成功");
                    for (int i = 0; i < list.size(); i++) {
                        view.setMapMarker(list.get(i));
                    }
                } else {
                    view.showToast("未查询到公交车");
                }
            }
        });
    }
}