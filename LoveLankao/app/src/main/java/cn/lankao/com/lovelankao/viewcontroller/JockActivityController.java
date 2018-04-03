package cn.lankao.com.lovelankao.viewcontroller;
import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import java.util.List;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.JockActivity;
import cn.lankao.com.lovelankao.adapter.JockAdapter;
import cn.lankao.com.lovelankao.model.Jock;
import cn.lankao.com.lovelankao.model.JuheApiResult;
import cn.lankao.com.lovelankao.model.Shared;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.GsonUtil;
import cn.lankao.com.lovelankao.utils.OkHttpUtil;
import cn.lankao.com.lovelankao.utils.ShareManager;
import cn.lankao.com.lovelankao.widget.OnRvScrollListener;
import cn.lankao.com.lovelankao.widget.ProDialog;
import cn.lankao.com.lovelankao.widget.SharePopupWindow;
import rx.Subscriber;
/**
 * Created by BuZhiheng on 2016/4/18.
 */
public class JockActivityController implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private final String url = "http://v.juhe.cn/joke/randJoke.php?&key=da46a2a9e5d5a3bfefb5694bfa0e04c1";
    private JockActivity context;
    private RecyclerView rv;
    private SwipeRefreshLayout refresh;
    private JockAdapter adapter;
    private ProgressDialog dialog;
    private ShareManager manager;
    private SharePopupWindow popWin;
    private String shareString;
    private boolean isRefresh = true;
    private boolean canLoadMore = true;
    public JockActivityController(JockActivity context){
        this.context = context;
        initView();
        initData();
    }
    private void initData() {
        OkHttpUtil.get(url, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onNext(String s) {
                JuheApiResult res = GsonUtil.jsonToObject(s, JuheApiResult.class);
                if (res.getError_code() == 0) {
                    try {
                        List<Jock> data = GsonUtil.jsonToList(res.getResult(), Jock.class);
                        if (isRefresh) {
                            adapter.setData(data);
                        } else {
                            adapter.addData(data);
                        }
                        canLoadMore = true;
                        adapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                        dialog.dismiss();
                    } catch (Exception e) {
                        refresh.setRefreshing(false);
                    }
                }
            }
        });
    }
    private void initView() {
        manager = ShareManager.getInstance(context);
        popWin = new SharePopupWindow(context,this);
        dialog = ProDialog.getProDialog(context);
        dialog.show();
        adapter = new JockAdapter(context,this);
        context.setContentView(R.layout.activity_jock);
        context.findViewById(R.id.iv_jock_back).setOnClickListener(this);
        refresh = (SwipeRefreshLayout)context.findViewById(R.id.srl_jock_activity);
        refresh.setOnRefreshListener(this);
        rv = (RecyclerView) context.findViewById(R.id.rv_activity_jock);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRvScrollListener() {
            @Override
            public void toBottom() {
                if (canLoadMore) {
                    isRefresh = false;
                    canLoadMore = false;
                    initData();
                }
            }
        });
    }
    private void shareQQ(int type) {
        Shared share = new Shared();
        share.setDesc(shareString);
        share.setTitle("掌上兰考");
        share.setImgUrl(CommonCode.APP_ICON);
        share.setUrl(CommonCode.APP_URL);
        share.setWxType(type);
        manager.shareQQ(share);
    }
    private void shareWxText(int type){
        Shared share = new Shared();
        share.setTitle("掌上兰考");
        share.setDesc(shareString);
        share.setWxType(type);
        manager.shareWxText(share);
    }
    @Override
    public void onRefresh() {
        isRefresh = true;
        initData();
    }
    public void onItemClick(String jock){
        shareString = jock;
        //设置layout在PopupWindow中显示的位置
        popWin.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_jock_back:
                context.finish();
                break;
            case R.id.ll_popwinshare_qq:
                shareQQ(ShareManager.SHARE_TYPE_CHAT);
                popWin.dismiss();
                break;
            case R.id.ll_popwinshare_qzone:
                shareQQ(ShareManager.SHARE_TYPE_SQUARE);
                popWin.dismiss();
                break;
            case R.id.ll_popwinshare_wx:
                shareWxText(ShareManager.SHARE_TYPE_CHAT);
                popWin.dismiss();
                break;
            case R.id.ll_popwinshare_wxsquare:
                shareWxText(ShareManager.SHARE_TYPE_SQUARE);
                popWin.dismiss();
                break;
            case R.id.tv_popwinshare_cancel:
                popWin.dismiss();
                break;
                default:break;
        }
    }
}