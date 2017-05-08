package cn.lankao.com.lovelankao.viewcontroller;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.lankao.com.lovelankao.ipresenter.ISquareMsgPresenter;
import cn.lankao.com.lovelankao.iview.ISquareMsgView;
import cn.lankao.com.lovelankao.model.Comment;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.SystemUtils;
import cn.lankao.com.lovelankao.utils.TextUtil;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class SquareMsgPresenter implements ISquareMsgPresenter {
    private ISquareMsgView view;
    public SquareMsgPresenter(ISquareMsgView view){
        this.view = view;
    }
    @Override
    public void getMyMsg() {
        if (!SystemUtils.networkState()){
            view.showToast(CommonCode.MSG_NETWORK_ERR);
            return;
        }
        String userId = PrefUtil.getString(CommonCode.SP_USER_USERID,"");
        if (TextUtil.isNull(userId)){
            return;
        }
        BmobQuery<Comment> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("postUserId",userId);
        BmobQuery<Comment> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("lastUserId",userId);
        List<BmobQuery<Comment>> list = new ArrayList<>();
        list.add(query1);
        list.add(query2);
        BmobQuery<Comment> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.or(list);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (list.size() > 0){
                    view.setComment(list);
                }
            }
        });
    }
}