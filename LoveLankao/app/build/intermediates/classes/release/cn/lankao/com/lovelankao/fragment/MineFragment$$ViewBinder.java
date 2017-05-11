// Generated code from Butter Knife. Do not modify!
package cn.lankao.com.lovelankao.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MineFragment$$ViewBinder<T extends cn.lankao.com.lovelankao.fragment.MineFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427713, "field 'tvNickName'");
    target.tvNickName = finder.castView(view, 2131427713, "field 'tvNickName'");
    view = finder.findRequiredView(source, 2131427714, "field 'tvPhone'");
    target.tvPhone = finder.castView(view, 2131427714, "field 'tvPhone'");
    view = finder.findRequiredView(source, 2131427715, "field 'tvJifen'");
    target.tvJifen = finder.castView(view, 2131427715, "field 'tvJifen'");
    view = finder.findRequiredView(source, 2131427712, "field 'photo'");
    target.photo = finder.castView(view, 2131427712, "field 'photo'");
  }

  @Override public void unbind(T target) {
    target.tvNickName = null;
    target.tvPhone = null;
    target.tvJifen = null;
    target.photo = null;
  }
}
