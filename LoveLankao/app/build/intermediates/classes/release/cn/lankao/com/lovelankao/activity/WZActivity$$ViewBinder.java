// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WZActivity$$ViewBinder<T extends cn.lankao.com.lovelankao.activity.WZActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427633, "field 'flClass'");
    target.flClass = finder.castView(view, 2131427633, "field 'flClass'");
    view = finder.findRequiredView(source, 2131427635, "field 'flEngine'");
    target.flEngine = finder.castView(view, 2131427635, "field 'flEngine'");
    view = finder.findRequiredView(source, 2131427632, "field 'etNo'");
    target.etNo = finder.castView(view, 2131427632, "field 'etNo'");
    view = finder.findRequiredView(source, 2131427634, "field 'etClass'");
    target.etClass = finder.castView(view, 2131427634, "field 'etClass'");
    view = finder.findRequiredView(source, 2131427636, "field 'etEngine'");
    target.etEngine = finder.castView(view, 2131427636, "field 'etEngine'");
    view = finder.findRequiredView(source, 2131427638, "field 'rvWz'");
    target.rvWz = finder.castView(view, 2131427638, "field 'rvWz'");
  }

  @Override public void unbind(T target) {
    target.flClass = null;
    target.flEngine = null;
    target.etNo = null;
    target.etClass = null;
    target.etEngine = null;
    target.rvWz = null;
  }
}
