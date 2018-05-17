package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;

/**
 * Created by hxk on 2017/7/12 0012 15:06
 * 注册账户第一步填写资料的信息
 */

public class RegistInfo implements Serializable {
    //注册账户电话号码
    public String phone;
    //获取到的验证码
    public String smsCode;
    //获取验证码同时从后台获取的key值
    public String bindKey;
    //注册录入的用户名
    public String nickName;
    //注册时设置的密码
    public String nickPasword;
    //本机mac地址
    public String macAddres;

    public RegistInfo(String phone, String smsCode, String bindKey, String nickName, String nickPasword, String macAddres) {
        this.phone = phone;
        this.smsCode = smsCode;
        this.bindKey = bindKey;
        this.nickName = nickName;
        this.nickPasword = nickPasword;
        this.macAddres = macAddres;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getBindKey() {
        return bindKey;
    }

    public void setBindKey(String bindKey) {
        this.bindKey = bindKey;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickPasword() {
        return nickPasword;
    }

    public void setNickPasword(String nickPasword) {
        this.nickPasword = nickPasword;
    }

    public String getMacAddres() {
        return macAddres;
    }

    public void setMacAddres(String macAddres) {
        this.macAddres = macAddres;
    }

    @Override
    public String toString() {
        return "RegistInfo{" +
                "phone='" + phone + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", bindKey='" + bindKey + '\'' +
                ", nickName='" + nickName + '\'' +
                ", nickPasword='" + nickPasword + '\'' +
                ", macAddres='" + macAddres + '\'' +
                '}';
    }
}
