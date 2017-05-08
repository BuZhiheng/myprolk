package cn.lankao.com.lovelankao.viewcontroller;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.ipresenter.IMainFrmPresenter;
import cn.lankao.com.lovelankao.iview.IMainFrmView;
import cn.lankao.com.lovelankao.model.AdvertNormal;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.SystemUtils;
/**
 * Created by BuZhiheng on 2016/3/31.
 */
public class MainFragmentController implements IMainFrmPresenter {
    private IMainFrmView view;
    public MainFragmentController(IMainFrmView view) {
        this.view = view;
    }
    @Override
    public void getData() {
        if (!SystemUtils.networkState()){
            view.setRefreshStop();
            return;
        }
        BmobQuery<AdvertNormal> query = new BmobQuery<>();
        query.order("-advClicked");
        query.setLimit(50);
        query.addWhereEqualTo("advIndex", CommonCode.ADVERT_INDEX);
        query.findObjects(new FindListener<AdvertNormal>() {
            @Override
            public void done(List<AdvertNormal> list, BmobException e) {
                if (list != null) {
                    view.setData(list);
                }
            }
        });
    }
}