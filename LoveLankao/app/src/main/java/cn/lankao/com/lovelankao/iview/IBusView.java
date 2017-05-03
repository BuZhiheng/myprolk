package cn.lankao.com.lovelankao.iview;
import cn.lankao.com.lovelankao.model.Driver;
/**
 * Created by buzhiheng on 2017/5/2.
 */
public interface IBusView {
    void showToast(String toast);
    void setMapMarker(Driver driver);
    void clearMap();
}