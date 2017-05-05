// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BusActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.BusActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427464, "field 'mapView'");
    target.mapView = finder.castView(view, 2131427464, "field 'mapView'");
    view = finder.findRequiredView(source, 2131427771, "field 'tvLine'");
    target.tvLine = finder.castView(view, 2131427771, "field 'tvLine'");
    view = finder.findRequiredView(source, 2131427465, "field 'btnRefresh'");
    target.btnRefresh = finder.castView(view, 2131427465, "field 'btnRefresh'");
  }

  @Override public void unbind(T target) {
    target.mapView = null;
    target.tvLine = null;
    target.btnRefresh = null;
  }
}
