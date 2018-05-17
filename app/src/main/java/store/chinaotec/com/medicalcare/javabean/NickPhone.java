package store.chinaotec.com.medicalcare.javabean;


/**
 * Created by hxk on 2017/7/18 0018 10:59
 * 登录账户的用户名 注册电话   用户种类
 */

public class NickPhone {
    public int type = 0;  //用户种类
    public String nickName;   //用户名
    public String phone;   //注册电话

    public NickPhone(int type, String nickName, String phone) {
        this.type = type;
        this.nickName = nickName;
        this.phone = phone;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "NickPhone{" +
                "type=" + type +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
