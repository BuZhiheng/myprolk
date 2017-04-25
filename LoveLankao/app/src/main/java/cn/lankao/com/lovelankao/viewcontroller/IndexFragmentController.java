package cn.lankao.com.lovelankao.viewcontroller;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.ipresenter.IIndexPresenter;
import cn.lankao.com.lovelankao.iview.IIndexView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.LanKaoNews;
import cn.lankao.com.lovelankao.model.MainService;
import cn.lankao.com.lovelankao.model.Setting;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.WindowUtils;
/**
 * Created by BuZhiheng on 2016/5/11.
 */
public class IndexFragmentController implements IIndexPresenter{
    private IIndexView view;
    public IndexFragmentController(IIndexView view){
        this.view = view;
        initSetting();
    }
    private void initSetting() {
        BmobQuery<Setting> query = new BmobQuery<>();
        query.addWhereEqualTo("setType", 1);
        query.findObjects(new FindListener<Setting>() {
            @Override
            public void done(List<Setting> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    Setting setting = list.get(0);
                    PrefUtil.putString(CommonCode.SP_SET_PARTNERURL, setting.getSetPartnerUrl());
                    PrefUtil.putString(CommonCode.SP_SET_ABOUTUSURL, setting.getSetAboutusUrl());
                    PrefUtil.putString(CommonCode.SP_SET_JCLKURL, setting.getSetJCLKUrl());
                    if (WindowUtils.getAppVersionCode() < setting.getAndroidVersionCode()){
                        view.showNewVersion(setting);
                    }
                }
            }
        });
    }
    @Override
    public void getData() {
        //加载服务菜单
        BmobQuery<MainService> query = new BmobQuery<>();
        query.order("index");
        query.findObjects(new FindListener<MainService>() {
            @Override
            public void done(List<MainService> list, BmobException e) {
                if (list != null && list.size() > 0){
                    view.setMainService(list);
                } else {
                    view.setRefreshStop();
                }
            }
        });
        //加载兰考新闻
        BmobQuery<LanKaoNews> queryNews = new BmobQuery<>();
        queryNews.setLimit(CommonCode.RV_ITEMS_COUT20);
        queryNews.order("-createdAt");
        queryNews.findObjects(new FindListener<LanKaoNews>() {
            @Override
            public void done(List<LanKaoNews> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    List<LanKaoNews> dataBanner = new ArrayList<>();
                    List<LanKaoNews> dataNews = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        LanKaoNews news = list.get(i);
                        if ("1".equals(news.getNewsType())) {
                            dataBanner.add(news);
                        } else {
                            dataNews.add(news);
                        }
                    }
                    view.setBannerAndNews(dataBanner,dataNews);
                } else {
                    view.setRefreshStop();
                }
            }
        });
    }
}