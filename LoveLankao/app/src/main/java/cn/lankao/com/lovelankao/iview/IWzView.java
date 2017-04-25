package cn.lankao.com.lovelankao.iview;
import java.util.List;

import cn.lankao.com.lovelankao.model.JWzMsg;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public interface IWzView {
    void showToast(String toast);
    void setClassShow(String msg);
    void setEngineShow(String msg);
    void setWz(List<JWzMsg> list);
}