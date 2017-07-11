// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MsgBoardSendActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.MsgBoardSendActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493077, "field 'editText'");
    target.editText = finder.castView(view, 2131493077, "field 'editText'");
    view = finder.findRequiredView(source, 2131493079, "field 'ivCheck'");
    target.ivCheck = finder.castView(view, 2131493079, "field 'ivCheck'");
  }

  @Override public void unbind(T target) {
    target.editText = null;
    target.ivCheck = null;
  }
}
