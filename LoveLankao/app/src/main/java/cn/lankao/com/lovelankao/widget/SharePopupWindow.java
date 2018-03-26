package cn.lankao.com.lovelankao.widget;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.utils.WindowUtils;

/**
 * Created by BuZhiheng on 2016/5/16.
 */
public class SharePopupWindow extends Dialog {
    private View.OnClickListener listener;
    public SharePopupWindow(@NonNull Context context,View.OnClickListener listener) {
        this(context,R.style.NewActivityDialog);
        this.listener = listener;
    }
    public SharePopupWindow(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    protected SharePopupWindow(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.requestFeature(window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_popwin_share);
        findViewById(R.id.tv_popwinshare_cancel).setOnClickListener(listener);
        findViewById(R.id.ll_popwinshare_qq).setOnClickListener(listener);
        findViewById(R.id.ll_popwinshare_qzone).setOnClickListener(listener);
        findViewById(R.id.ll_popwinshare_wx).setOnClickListener(listener);
        findViewById(R.id.ll_popwinshare_wxsquare).setOnClickListener(listener);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowUtils.getWindowsWidth();
        window.setAttributes(lp);
        window.getAttributes().gravity = Gravity.BOTTOM;
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }
}