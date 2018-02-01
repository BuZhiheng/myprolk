package cn.lankao.com.lovelankao.viewcontroller;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.util.ArrayList;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.SettingActivity;
import cn.lankao.com.lovelankao.model.MyUser;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.utils.BitmapUtil;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.widget.ProDialog;
import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import static android.app.Activity.RESULT_OK;
/**
 * Created by BuZhiheng on 2016/4/7.
 */
public class SettingActivityController implements View.OnClickListener ,SettingActivity.SettingHolder{
    private SettingActivity context;
    private ImageView photo;
    private ProgressDialog dialog;
    public SettingActivityController(SettingActivity context){
        this.context = context;
        initView();
    }
    private void initView() {
        dialog = ProDialog.getProDialog(context);
        context.findViewById(R.id.btn_setting_zhuxiao).setOnClickListener(this);
        context.findViewById(R.id.btn_setting_exit).setOnClickListener(this);
        context.findViewById(R.id.iv_setting_back).setOnClickListener(this);
        photo = (ImageView) context.findViewById(R.id.iv_setting_photo);
        photo.setOnClickListener(this);
        BitmapUtil.loadImageCircle(context,photo, PrefUtil.getString(CommonCode.SP_USER_PHOTO, CommonCode.APP_ICON));
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.iv_setting_photo:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(context, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.btn_setting_zhuxiao:
                MyUser user = new MyUser();
                user.setNickName("未登录");
                PrefUtil.clear();
                context.finish();
                EventBus.getDefault().post(new Square());
                EventBus.getDefault().post(user);
                break;
            case R.id.btn_setting_exit:
                MyUser userExit = new MyUser();
                //username设置包名,退出程序
                userExit.setNickName(context.getPackageName());
                context.finish();
                EventBus.getDefault().post(userExit);
                break;
            case R.id.iv_setting_back:
                context.finish();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            return;
        }
        if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> datas = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (datas == null || datas.size() == 0){
                    return;
                }
                String path = datas.get(0);
                final String userId = PrefUtil.getString(CommonCode.SP_USER_USERID,"");
                dialog.show();
                Luban.with(context).load(path).setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File f) {
                        final BmobFile file = new BmobFile(f);
                        file.upload(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                final MyUser myUser = new MyUser();
                                myUser.setPhoto(file);
                                myUser.update(userId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        dialog.dismiss();
                                        ToastUtil.show("上传成功");
                                        PrefUtil.putString(CommonCode.SP_USER_PHOTO, file.getFileUrl());
                                        BitmapUtil.loadImageCircle(context,photo, file.getFileUrl());
                                        myUser.setNickName(CommonCode.SP_USER_PHOTO);//user frm 界面更新头像
                                        EventBus.getDefault().post(myUser);
                                    }
                                });
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
            }
        }
    }
}