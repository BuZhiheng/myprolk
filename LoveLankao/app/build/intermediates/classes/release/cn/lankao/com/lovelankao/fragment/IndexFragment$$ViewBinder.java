// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class IndexFragment$$ViewBinder<T extends cn.lankao.com.lovelankao.fragment.IndexFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427666, "field 'refreshLayout'");
    target.refreshLayout = finder.castView(view, 2131427666, "field 'refreshLayout'");
    view = finder.findRequiredView(source, 2131427669, "field 'banner'");
    target.banner = finder.castView(view, 2131427669, "field 'banner'");
    view = finder.findRequiredView(source, 2131427670, "field 'rvService'");
    target.rvService = finder.castView(view, 2131427670, "field 'rvService'");
    view = finder.findRequiredView(source, 2131427667, "field 'rvNews'");
    target.rvNews = finder.castView(view, 2131427667, "field 'rvNews'");
    view = finder.findRequiredView(source, 2131427668, "field 'header'");
    target.header = finder.castView(view, 2131427668, "field 'header'");
  }

  @Override public void unbind(T target) {
    target.refreshLayout = null;
    target.banner = null;
    target.rvService = null;
    target.rvNews = null;
    target.header = null;
  }
}
