// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SquarePersonalActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.SquarePersonalActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493159, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131493159, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131493158, "field 'refresh'");
    target.refresh = finder.castView(view, 2131493158, "field 'refresh'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
    target.refresh = null;
  }
}
