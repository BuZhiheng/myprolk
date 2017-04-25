package cn.lankao.com.lovelankao.iview;
import java.util.List;
import cn.lankao.com.lovelankao.model.LanKaoNews;
import cn.lankao.com.lovelankao.model.MainService;
import cn.lankao.com.lovelankao.model.Setting;

/**
 * Created by buzhiheng on 2017/4/25.
 */
public interface IIndexView {
    void showToast(String toast);
    void setBannerAndNews(List<LanKaoNews> banners,List<LanKaoNews> news);
    void setMainService(List<MainService> list);

    void setRefreshStop();

    void showNewVersion(Setting setting);
}