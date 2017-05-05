package cn.lankao.com.lovelankao.iview;

import cn.lankao.com.lovelankao.model.MyUser;

/**
 * Created by buzhiheng on 2017/5/5.
 */
public interface IMineFrmView {
    void showToast(String toast);

    void exit();

    void setPhoto(String fileUrl);

    void setUserMsg(MyUser user);
}