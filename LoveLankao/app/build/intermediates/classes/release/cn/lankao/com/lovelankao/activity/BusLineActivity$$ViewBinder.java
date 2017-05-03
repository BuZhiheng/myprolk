// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BusLineActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.BusLineActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427466, "field 'rvLine'");
    target.rvLine = finder.castView(view, 2131427466, "field 'rvLine'");
  }

  @Override public void unbind(T target) {
    target.rvLine = null;
  }
}
