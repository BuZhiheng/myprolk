package cn.lankao.com.lovelankao.iview;
import cn.lankao.com.lovelankao.model.MyUser;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public interface ILKPeopleView {
    void showToast(String toast);
    void setMapMarker(MyUser user);
}