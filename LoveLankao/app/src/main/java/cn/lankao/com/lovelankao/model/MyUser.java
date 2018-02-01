package cn.lankao.com.lovelankao.model;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.lankao.com.lovelankao.utils.PrefUtil;
import cn.lankao.com.lovelankao.utils.TextUtil;
/**
 * Created by BuZhiheng on 2016/4/2.
 */
public class MyUser extends BmobObject{
    private String androidVersion;
    private String mobile;
    private Integer coupon;//
    private String nickName;
    private String userType;
    private String passWord;
    private Float userLat;
    private Float userLng;
    private BmobFile photo;
    private String commentMsg;
    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCommentMsg() {
        return commentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        this.commentMsg = commentMsg;
    }

    public Float getUserLat() {
        return userLat;
    }

    public void setUserLat(Float userLat) {
        this.userLat = userLat;
    }

    public Float getUserLng() {
        return userLng;
    }

    public void setUserLng(Float userLng) {
        this.userLng = userLng;
    }
    public static boolean isLogin(){
        if (TextUtil.isNull(PrefUtil.getString(CommonCode.SP_USER_USERID, ""))) {
            return false;
        } else {
            return true;
        }
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}