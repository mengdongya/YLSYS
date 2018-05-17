package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/8/1 0001 17:18
 * 突发伤病 检查结果解析对象解析
 */

public class BaseContentBean {
    public String contentName;
    public String contentInfo;

    public BaseContentBean(String contentName, String contentInfo) {
        this.contentName = contentName;
        this.contentInfo = contentInfo;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    @Override
    public String toString() {
        return "BaseContentBean{" +
                "contentName='" + contentName + '\'' +
                ", contentInfo='" + contentInfo + '\'' +
                '}';
    }
}
