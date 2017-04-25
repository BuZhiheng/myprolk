package cn.lankao.com.lovelankao.iview;

import java.util.List;

import cn.lankao.com.lovelankao.model.Comment;

/**
 * Created by buzhiheng on 2017/4/22.
 */
public interface ISquareMsgView {
    void showToast(String toast);

    void setComment(List<Comment> list);

}