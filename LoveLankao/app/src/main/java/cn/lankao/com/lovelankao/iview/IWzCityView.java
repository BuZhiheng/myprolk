package cn.lankao.com.lovelankao.iview;
import java.util.List;
import cn.lankao.com.lovelankao.model.JWzCitys;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public interface IWzCityView {
    void showToast(String toast);
    void setCity(List<JWzCitys> list);
}