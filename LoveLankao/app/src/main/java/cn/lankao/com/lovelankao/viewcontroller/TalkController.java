package cn.lankao.com.lovelankao.viewcontroller;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.lankao.com.lovelankao.ipresenter.ITalkPresnter;
import cn.lankao.com.lovelankao.iview.ITalkView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.SystemUtils;
import cn.lankao.com.lovelankao.utils.TextUtil;
/**
 * Created by BuZhiheng on 2016/5/20.
 */
public class TalkController implements ITalkPresnter {
    private ITalkView view;
    public TalkController(ITalkView view){
        this.view = view;
    }
    @Override
    public void getData(int cout) {
        BmobQuery<Square> query = new BmobQuery<>();
        query.setLimit(cout);
        query.order("-createdAt");//按事件排序
        query.findObjects(new FindListener<Square>() {
            @Override
            public void done(List<Square> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    view.setData(list);
                } else {
                    view.setRefreshStop();
                }
            }
        });
    }
    @Override
    public void onMsgClick() {
        String userId = PrefUtil.getString(CommonCode.SP_USER_USERID, "");
        if (TextUtil.isNull(userId)){
            return;
        }
        MyUser user = new MyUser();
        user.setCommentMsg(CommonCode.USER_MSG_POST_INIT);
        user.update(userId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    view.setReadedMsg();
                }
            }
        });
    }
}