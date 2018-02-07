package cn.lankao.com.lovelankao.viewcontroller;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.lankao.com.lovelankao.base.LApplication;
import cn.lankao.com.lovelankao.iview.IMineFrmView;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.SystemUtils;
import cn.lankao.com.lovelankao.utils.TextUtil;
/**
 * Created by BuZhiheng on 2016/4/6.
 */
public class MineFragmentController {
    private IMineFrmView view;
    public MineFragmentController(IMineFrmView view) {
        this.view = view;
        EventBus.getDefault().register(this);
    }
    public void initUser() {
        if (!SystemUtils.networkState()){
            MyUser user = new MyUser();
            user.setNickName(PrefUtil.getString(CommonCode.SP_USER_NICKNAME, ""));
            user.setMobile(PrefUtil.getString(CommonCode.SP_USER_USERMOBILE, ""));
            user.setCoupon(PrefUtil.getInt(CommonCode.SP_USER_POINT, 0));
            if (TextUtil.isNull(PrefUtil.getString(CommonCode.SP_USER_PHOTO,""))){
                view.setPhoto(CommonCode.APP_ICON);
            } else {
                view.setPhoto(PrefUtil.getString(CommonCode.SP_USER_PHOTO,""));
            }
            view.setUserMsg(user);
            return;
        }
        String userid = PrefUtil.getString(CommonCode.SP_USER_USERID,"");
        if (!TextUtil.isNull(userid)){
            BmobQuery<MyUser> query = new BmobQuery<>();
            query.getObject(userid, new QueryListener<MyUser>() {
                @Override
                public void done(MyUser user, BmobException e) {
                    if (user == null){
                        return;
                    }
                    PrefUtil.putString(CommonCode.SP_USER_USERID, user.getObjectId());
                    PrefUtil.putString(CommonCode.SP_USER_USERMOBILE, user.getMobile());
                    PrefUtil.putString(CommonCode.SP_USER_NICKNAME, user.getNickName());
                    PrefUtil.putString(CommonCode.SP_USER_USERTYPE, user.getUserType());
                    PrefUtil.putString(CommonCode.SP_USER_USER_POST_MSG, user.getCommentMsg());
                    if (user.getPhoto() != null){
                        PrefUtil.putString(CommonCode.SP_USER_PHOTO, user.getPhoto().getFileUrl());
                    }
                    Integer point = user.getCoupon();
                    if (point == null){
                        PrefUtil.putInt(CommonCode.SP_USER_POINT, 0);
                    } else {
                        int p = point;
                        PrefUtil.putInt(CommonCode.SP_USER_POINT, p);
                    }
                    EventBus.getDefault().post(user);
                }
            });
        } else {
            view.setPhoto(CommonCode.APP_ICON);
        }
    }
    @Subscribe
    public void onEventMainThread(MyUser user) {
        if (user == null){
            return;
        }
        if (LApplication.getCtx().getPackageName().equals(user.getNickName())){
            //nickname等于app包名,退出程序
            view.exit();
        } else if(CommonCode.SP_USER_PHOTO.equals(user.getNickName())){
            //nickname等于CommonCode.SP_USER_PHOTO,更新头像
            if (user.getPhoto() != null){
                view.setPhoto(user.getPhoto().getFileUrl());
            } else {
                view.setPhoto(CommonCode.APP_ICON);
            }
        } else {
            //登陆成功,更新user frm 界面
            if (user.getCoupon() == null){
                user.setCoupon(0);
            }
            view.setUserMsg(user);
            if (user.getPhoto() != null){
                view.setPhoto(user.getPhoto().getFileUrl());
            } else {
                view.setPhoto(CommonCode.APP_ICON);
            }
        }
    }
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}