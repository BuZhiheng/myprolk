package cn.lankao.com.lovelankao.viewcontroller;
import java.util.List;
import cn.lankao.com.lovelankao.ipresenter.IWzCityPresenter;
import cn.lankao.com.lovelankao.iview.IWzCityView;
import cn.lankao.com.lovelankao.model.JWzCitys;
import cn.lankao.com.lovelankao.model.JWzProvince;
import cn.lankao.com.lovelankao.model.JuheApiResult;
import cn.lankao.com.lovelankao.utils.GsonUtil;
import cn.lankao.com.lovelankao.utils.OkHttpUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import rx.Subscriber;
/**
 * Created by buzhiheng on 2017/4/22.
 */
public class WzCityPresenter implements IWzCityPresenter{
    private String url = "http://v.juhe.cn/sweizhang/citys";
    private IWzCityView view;
    public WzCityPresenter(IWzCityView view){
        this.view = view;
    }
    @Override
    public void getCitys() {
        RequestBody body = new FormBody.Builder()
                .add("province","HN")
                .add("format","2")
                .add("key","80213ce00ded56d453cc89c497632e9a")
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
                JuheApiResult result = GsonUtil.jsonToObject(s,JuheApiResult.class);
                if (result.success()){
                    List<JWzProvince> listP = GsonUtil.jsonToList(result.getResult(),JWzProvince.class);
                    if (listP.size() > 0){
                        List<JWzCitys> listC = GsonUtil.jsonToList(listP.get(0).citys,JWzCitys.class);
                        view.setCity(listC);
                    }
                } else {
                    view.showToast(result.getReason());
                }
            }
        });
    }
}