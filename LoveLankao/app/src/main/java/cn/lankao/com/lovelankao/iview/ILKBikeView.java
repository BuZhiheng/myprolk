package cn.lankao.com.lovelankao.iview;
import cn.lankao.com.lovelankao.model.PublicBike;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public interface ILKBikeView {
    void showToast(String toast);
    void setMapMarker(PublicBike bike);
}