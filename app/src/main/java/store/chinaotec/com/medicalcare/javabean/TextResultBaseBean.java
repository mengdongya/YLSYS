package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/8/2 0002 13:37
 * 保存检查检查结果解读页面的疾病名字  和id
 */

public class TextResultBaseBean {
    public String name;
    public int memberSickDealId;
    public String url;

    public TextResultBaseBean(String name, int memberSickDealId) {
        this.name = name;
        this.memberSickDealId = memberSickDealId;
    }

    public TextResultBaseBean(String name, int memberSickDealId,String url) {
        this.name = name;
        this.memberSickDealId = memberSickDealId;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberSickDealId() {
        return memberSickDealId;
    }

    public void setMemberSickDealId(int memberSickDealId) {
        this.memberSickDealId = memberSickDealId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TextResultBaseBean{" +
                "name='" + name + '\'' +
                ", memberSickDealId=" + memberSickDealId +
                '}';
    }
}
