// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LKPeopleActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.LKPeopleActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427512, "field 'mapView'");
    target.mapView = finder.castView(view, 2131427512, "field 'mapView'");
  }

  @Override public void unbind(T target) {
    target.mapView = null;
  }
}
