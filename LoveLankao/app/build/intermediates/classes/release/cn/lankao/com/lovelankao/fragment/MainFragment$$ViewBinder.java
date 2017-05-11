// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainFragment$$ViewBinder<T extends cn.lankao.com.lovelankao.fragment.MainFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427687, "field 'refresh'");
    target.refresh = finder.castView(view, 2131427687, "field 'refresh'");
    view = finder.findRequiredView(source, 2131427688, "field 'rvShop'");
    target.rvShop = finder.castView(view, 2131427688, "field 'rvShop'");
    view = finder.findRequiredView(source, 2131427689, "field 'header'");
    target.header = finder.castView(view, 2131427689, "field 'header'");
  }

  @Override public void unbind(T target) {
    target.refresh = null;
    target.rvShop = null;
    target.header = null;
  }
}
