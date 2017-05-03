// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class TalkFragment$$ViewBinder<T extends cn.lankao.com.lovelankao.fragment.TalkFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427725, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131427725, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131427724, "field 'refresh'");
    target.refresh = finder.castView(view, 2131427724, "field 'refresh'");
    view = finder.findRequiredView(source, 2131427726, "field 'tvMsg'");
    target.tvMsg = finder.castView(view, 2131427726, "field 'tvMsg'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
    target.refresh = null;
    target.tvMsg = null;
  }
}
