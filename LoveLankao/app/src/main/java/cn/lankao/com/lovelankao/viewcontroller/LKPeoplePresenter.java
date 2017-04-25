package cn.lankao.com.lovelankao.viewcontroller;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.ipresenter.ILKPeoplePresenter;
import cn.lankao.com.lovelankao.iview.ILKPeopleView;
import cn.lankao.com.lovelankao.model.MyUser;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class LKPeoplePresenter implements ILKPeoplePresenter{
    private ILKPeopleView view;
    public LKPeoplePresenter(ILKPeopleView view){
        this.view = view;
    }
    @Override
    public void getPeople() {
        BmobQuery<MyUser> query = new BmobQuery();
        query.order("-updatedAt");
        query.addWhereGreaterThan("userLat", 0);
        query.setLimit(150);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (list.size() > 0){
                    for (int i=0;i<list.size();i++){
                        view.setMapMarker(list.get(i));
                    }
                }
            }
        });
    }
}