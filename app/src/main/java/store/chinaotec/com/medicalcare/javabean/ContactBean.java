package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/7/12 0012 09:45
 * 查找手机联系人的java对象
 */

public class ContactBean {
    public String name;
    public String phone;

    @Override
    public String toString() {
        return name + "," + phone + ",";
    }
}
