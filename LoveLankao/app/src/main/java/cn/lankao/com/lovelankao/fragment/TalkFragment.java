package cn.lankao.com.lovelankao.fragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.LoginActivity;
import cn.lankao.com.lovelankao.activity.SquareMsgActivity;
import cn.lankao.com.lovelankao.activity.SquareSendActivity;
import cn.lankao.com.lovelankao.adapter.SquareAdapter;
import cn.lankao.com.lovelankao.ipresenter.ITalkPresnter;
import cn.lankao.com.lovelankao.iview.ITalkView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.viewcontroller.TalkController;
import cn.lankao.com.lovelankao.widget.OnRvScrollListener;
/**
 * Created by BuZhiheng on 2016/3/30.
 */
public class TalkFragment extends Fragment implements ITalkView, View.OnClickListener {
    private ITalkPresnter presnter = new TalkController(this);
    private View view;
    @Bind(R.id.rv_square_frm)
    RecyclerView recyclerView;
    @Bind(R.id.srl_square_frm)
    SwipeRefreshLayout refresh;
    @Bind(R.id.tv_squarefrm_msg)
    TextView tvMsg;
    private SquareAdapter adapter;
    private int cout = CommonCode.RV_ITEMS_COUT;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_talknew,container,false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }
    private void initView() {
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cout = CommonCode.RV_ITEMS_COUT;
                presnter.getData(cout);
            }
        });
        adapter = new SquareAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnRvScrollListener() {
            @Override
            public void toBottom() {
                cout += CommonCode.RV_ITEMS_COUT;
                presnter.getData(cout);
            }
        });
        tvMsg.setOnClickListener(this);
        view.findViewById(R.id.ll_talkfrm_send).setOnClickListener(this);
        view.findViewById(R.id.tv_talkfrm_my).setOnClickListener(this);
        presnter.getData(cout);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(Square s){
        cout = CommonCode.RV_ITEMS_COUT;
        presnter.getData(cout);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userMsg(MyUser user){
        if (CommonCode.USER_MSG_POST_COMMENT.equals(user.getCommentMsg())){
            tvMsg.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_talkfrm_send:
                Intent intentSend = new Intent(getActivity(), SquareSendActivity.class);
                startActivity(intentSend);
                break;
            case R.id.tv_squarefrm_msg:
                presnter.onMsgClick();
                break;
            case R.id.tv_talkfrm_my:
                if (MyUser.isLogin()){
                    setReadedMsg();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
    @Override
    public void setData(List<Square> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        refresh.setRefreshing(false);
    }
    @Override
    public void setReadedMsg() {
        Intent intent = new Intent(getActivity(), SquareMsgActivity.class);
        startActivity(intent);
        tvMsg.setVisibility(View.GONE);
    }
    @Override
    public void setRefreshStop() {
        refresh.setRefreshing(false);
    }
}