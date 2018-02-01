package cn.lankao.com.lovelankao.viewcontroller;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.lankao.com.lovelankao.R;
import cn.lankao.com.lovelankao.activity.LoginActivity;
import cn.lankao.com.lovelankao.activity.SquareSendActivity;
import cn.lankao.com.lovelankao.adapter.SquareSendPictureAdapter;
import cn.lankao.com.lovelankao.model.Square;
import cn.lankao.com.lovelankao.model.CommonCode;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.ToastUtil;
import cn.lankao.com.lovelankao.widget.ProDialog;
import me.iwf.photopicker.PhotoPicker;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import static android.app.Activity.RESULT_OK;
/**
 * Created by BuZhiheng on 2016/4/4.
 */
public class SquareSendActivityController implements View.OnClickListener, SquareSendActivity.SquareSendHolder {
    private SquareSendActivity context;
    private EditText etContent;
    private RecyclerView recyclerView;
    private String[] pathArr;
    private ProgressDialog dialog;
    private ArrayList<String> finalPhotos = new ArrayList<>();
    private SquareSendPictureAdapter adapter;
    public SquareSendActivityController(SquareSendActivity context) {
        this.context = context;
        initView();
    }
    private void initView() {
        dialog = ProDialog.getProDialog(context);
        etContent = context.findViewById(R.id.et_square_send_content);
        recyclerView = context.findViewById(R.id.rv_square_send_picture);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new SquareSendPictureAdapter(context);
        recyclerView.setAdapter(adapter);
        context.findViewById(R.id.tv_square_send).setOnClickListener(this);
        context.findViewById(R.id.tv_square_cancel).setOnClickListener(this);
        context.findViewById(R.id.iv_square_choose_photo).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_square_choose_photo:
                PhotoPicker.builder()
                        .setPhotoCount(6)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .setSelected(finalPhotos)
                        .start(context, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.tv_square_send:
                upLoading();
                break;
            case R.id.tv_square_cancel:
                context.finish();
                break;
            default:
                break;
        }
    }
    private void upLoading(){
        if ("".equals(PrefUtil.getString(CommonCode.SP_USER_USERID, ""))){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return;
        } else if (TextUtils.isEmpty(etContent.getText().toString())){
            ToastUtil.show("请输入内容");
            return;
        }
        dialog.show();
        //设置图片路径,压缩图片

        final Square square = new Square();
        square.setNickName(PrefUtil.getString(CommonCode.SP_USER_NICKNAME, "掌上兰考"));
        square.setUserPhoto(PrefUtil.getString(CommonCode.SP_USER_PHOTO, ""));
        square.setSquareContent(etContent.getText().toString());
        square.setSquareUserType(PrefUtil.getString(CommonCode.SP_USER_USERTYPE, ""));
        square.setUserId(PrefUtil.getString(CommonCode.SP_USER_USERID,""));
        if (pathArr == null || pathArr.length == 0){//没有选择图片
            square.save(new SaveListener() {
                @Override
                public void done(Object o, BmobException e) {
                    if (e == null){
                        dialog.dismiss();
                        ToastUtil.show("发表成功");
                        EventBus.getDefault().post(square);
                        context.finish();
                    } else {
                        ToastUtil.show(e.getMessage());
                    }
                }
            });
        } else {
            BmobFile.uploadBatch(pathArr, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> strs) {
                    if (strs.size() == pathArr.length) {
                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                square.setSquarePhoto1(list.get(i));
                            } else if (i == 1) {
                                square.setSquarePhoto2(list.get(i));
                            } else if (i == 2) {
                                square.setSquarePhoto3(list.get(i));
                            } else if (i == 3) {
                                square.setSquarePhoto4(list.get(i));
                            } else if (i == 4) {
                                square.setSquarePhoto5(list.get(i));
                            } else if (i == 5) {
                                square.setSquarePhoto6(list.get(i));
                            }
                        }
                        square.save(new SaveListener() {
                            @Override
                            public void done(Object o, BmobException e) {
                                if (e == null){
                                    dialog.dismiss();
                                    ToastUtil.show("发表成功");
                                    EventBus.getDefault().post(square);
                                    context.finish();
                                } else {
                                    ToastUtil.show("发表失败");
                                }
                            }
                        });
                    }
                }
                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                }
                @Override
                public void onError(int i, String s) {
                    ToastUtil.show(s);
                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            return;
        }
        if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                finalPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                adapter.setData(finalPhotos);
                adapter.notifyDataSetChanged();
                setUploadPath(finalPhotos);
            }
        }
    }
    private void setUploadPath(ArrayList<String> photos){
        if (photos.size() > 0){
            pathArr = new String[photos.size()];
            for (int i=0;i<photos.size();i++){
                int finalI = i;
                Luban.with(context).load(photos.get(i)).setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        pathArr[finalI] = file.getAbsolutePath();
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
            }
        }
    }
}