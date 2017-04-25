package cn.lankao.com.lovelankao.iview;

import java.util.List;

import cn.lankao.com.lovelankao.model.Square;

/**
 * Created by buzhiheng on 2017/4/25.
 */
public interface ITalkView {
    void setData(List<Square> list);

    void setReadedMsg();

    void setRefreshStop();
}