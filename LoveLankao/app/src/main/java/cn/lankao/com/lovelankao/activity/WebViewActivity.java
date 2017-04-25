package cn.lankao.com.lovelankao.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.model.Shared;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.ShareManager;
import cn.lankao.com.lovelankao.widget.ProDialog;
import cn.lankao.com.lovelankao.widget.SharePopupWindow;
/**
 * Created by BuZhiheng on 2016/4/7.
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layout;
    private TextView title;
    private WebView webView;
    private ProgressDialog dialog;
    private SharePopupWindow popWin;
    private ShareManager manager;
    private String webUrl;
    private String shareImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initData();
    }
    private void initData() {
        Intent intent = getIntent();
        if (intent != null){
            title.setText(intent.getStringExtra(CommonCode.INTENT_ADVERT_TITLE));
            webUrl = intent.getStringExtra(CommonCode.INTENT_SETTING_URL);
            shareImg = intent.getStringExtra(CommonCode.INTENT_SHARED_IMG);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                }
            });//播放视频
            webView.loadUrl(webUrl);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    dialog.dismiss();
                    super.onPageFinished(view, url);
                }
            });
        }
    }
    private void initView() {
        popWin = new SharePopupWindow(this,this);
        manager = ShareManager.getInstance(this);
        dialog = ProDialog.getProDialog(this);
        dialog.show();
        title = (TextView) findViewById(R.id.tv_webview_title);
        webView = (WebView) findViewById(R.id.web_webview_content);
        layout = (LinearLayout) findViewById(R.id.ll_webview_content);
        findViewById(R.id.iv_webview_back).setOnClickListener(this);
        findViewById(R.id.iv_webview_share).setOnClickListener(this);
    }
    private void shareQQ(int type) {
        Shared share = new Shared();
        if (!webView.canGoBack()){
            share.setImgUrl(CommonCode.APP_ICON);
            share.setUrl(webUrl);
        } else {
            share.setImgUrl(CommonCode.APP_ICON);
            share.setUrl(webView.getUrl());
        }
        share.setWxType(type);
        share.setTitle("掌上兰考");
        share.setDesc(webView.getTitle() == null ? "来自掌上兰考的分享" : webView.getTitle());
        manager.shareQQ(share);
    }
    private void shareWx(int type){
        Shared share = new Shared();
        if (!webView.canGoBack()){
            share.setUrl(webUrl);
            share.setImgUrl(CommonCode.APP_ICON);
        } else {
            share.setImgUrl(CommonCode.APP_ICON);
            share.setUrl(webView.getUrl());
        }
        share.setTitle("掌上兰考");
        share.setDesc(webView.getTitle() == null ? "来自掌上兰考的分享" : webView.getTitle());
        share.setWxType(type);
        manager.shareWx(share);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
            layout.removeAllViews();
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_webview_back:
                finish();
                break;
            case R.id.iv_webview_share:
                //设置layout在PopupWindow中显示的位置
//                share = new Shared();
//                share.setTitle(info.share_title);
//                share.setDesc(info.share_describe);
//                share.setUrl(info.share_url);
//                share.setImgUrl(CommonCode.APP_ICON);
                popWin.showAtLocation(findViewById(R.id.ll_webview_content), Gravity.BOTTOM , 0, 0);
                break;
            case R.id.ll_popwinshare_qq:
                shareQQ(manager.SHARE_TYPE_CHAT);
                popWin.dismiss();
                break;
            case R.id.ll_popwinshare_qzone:
                shareQQ(manager.SHARE_TYPE_SQUARE);
                popWin.dismiss();
                break;
            case R.id.ll_popwinshare_wx:
                shareWx(manager.SHARE_TYPE_CHAT);
                popWin.dismiss();
                break;
            case R.id.ll_popwinshare_wxsquare:
                shareWx(manager.SHARE_TYPE_SQUARE);
                popWin.dismiss();
                break;
            case R.id.tv_popwinshare_cancel:
                popWin.dismiss();
                break;
            default:break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
