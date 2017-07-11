// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SquareMsgActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.SquareMsgActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493081, "field 'rvMsg'");
    target.rvMsg = finder.castView(view, 2131493081, "field 'rvMsg'");
  }

  @Override public void unbind(T target) {
    target.rvMsg = null;
  }
}
