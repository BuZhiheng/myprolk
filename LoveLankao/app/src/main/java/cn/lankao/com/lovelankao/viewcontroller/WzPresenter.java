package cn.lankao.com.lovelankao.viewcontroller;
import android.content.Intent;
import android.widget.EditText;

import java.util.List;

import cn.lankao.com.lovelankao.ipresenter.IWzPresenter;
import cn.lankao.com.lovelankao.iview.IWzView;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.model.JWzCitys;
import cn.lankao.com.lovelankao.model.JWzMsg;
import cn.lankao.com.lovelankao.model.JWzMsgList;
import cn.lankao.com.lovelankao.model.JuheApiResult;
import cn.lankao.com.lovelankao.utils.GsonUtil;
import cn.lankao.com.lovelankao.utils.OkHttpUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Subscriber;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class WzPresenter implements IWzPresenter {
    private String url = "http://v.juhe.cn/wz/query";
    private IWzView view;
    private JWzCitys city;
    public WzPresenter(IWzView view,Intent intent){
        this.view = view;
        if (intent.getSerializableExtra(CommonCode.INTENT_COMMON_OBJ) != null){
            city = (JWzCitys) intent.getSerializableExtra(CommonCode.INTENT_COMMON_OBJ);
        }
        if (city != null){
            if ("1".equals(city.classa)){
                if ("0".equals(city.classno)){
                    view.setClassShow("请输入车架号");
                } else {
                    view.setClassShow("请输入车架号后"+city.classno+"位");
                }
            }
            if ("1".equals(city.engine)){
                if ("0".equals(city.engineno)){
                    view.setEngineShow("请输入发动机号");
                } else {
                    view.setEngineShow("请输入发动机号后" + city.engineno + "位");
                }
            }
        }
    }
    @Override
    public void getWz(EditText etNo, EditText etClass, EditText etEngine) {
        if (city == null){
            return;
        }
        String sNo = etNo.getText().toString();
        String sClass = etClass.getText().toString();
        String sEngine = etEngine.getText().toString();
        if (TextUtil.isNull(sNo)){
            view.showToast("请输入车牌号");
            return;
        }
        if (!sNo.startsWith("豫")){
            view.showToast("请输入豫开头的车牌号");
            return;
        }
        if (sNo.length() < 7){
            view.showToast("请正确输入车牌号");
            return;
        }
        if ("1".equals(city.classa)){
            if (TextUtil.isNull(sClass)){
                view.showToast("请输入车架号");
                return;
            }
        }
        if ("1".equals(city.engine)){
            if (TextUtil.isNull(sEngine)){
                view.showToast("请输入发动机号");
                return;
            }
        }
        RequestBody body = new FormBody.Builder()
                .add("key", "d33b537656a3e1a29d38048ee9d138a6")
                .add("city", city.city_code)
                .add("hphm", sNo)
                .add("classno", sClass)
                .add("engineno", sEngine)
                .build();
        OkHttpUtil.post(url, body, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onNext(String s) {
                view.showToast(s);
                JuheApiResult result = GsonUtil.jsonToObject(s,JuheApiResult.class);
                if (result.success()){
                    JWzMsgList msgList = GsonUtil.jsonToObject(result.getResult().toString(),JWzMsgList.class);
                    List<JWzMsg> list = GsonUtil.jsonToList(msgList.lists,JWzMsg.class);
                    if (list == null || list.size() == 0){
                        view.showToast("本城市未查询到违章记录");
                    } else {
                        view.setWz(list);
                    }
                } else {
                    view.showToast(result.getReason());
                }
            }
        });
    }
}