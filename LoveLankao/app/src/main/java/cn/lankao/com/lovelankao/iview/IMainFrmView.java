package cn.lankao.com.lovelankao.iview;

import java.util.List;

import cn.lankao.com.lovelankao.model.AdvertNormal;

/**
 * Created by buzhiheng on 2017/5/5.
 */
public interface IMainFrmView {
    void showToast(String toast);

    void setData(List<AdvertNormal> list);

}