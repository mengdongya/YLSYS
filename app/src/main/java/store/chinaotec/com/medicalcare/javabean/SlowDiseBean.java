package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by hxk on 2017/10/16 0016 11:02
 * 慢性病模块联网获取到的用户的慢性病信息java对象
 */

public class SlowDiseBean {
    public String diseId;
    public String diseName;

    public SlowDiseBean(String diseId, String diseName) {
        this.diseId = diseId;
        this.diseName = diseName;
    }

    public String getDiseId() {
        return diseId;
    }

    public void setDiseId(String diseId) {
        this.diseId = diseId;
    }

    public String getDiseName() {
        return diseName;
    }

    public void setDiseName(String diseName) {
        this.diseName = diseName;
    }

    @Override
    public String toString() {
        return "SlowDiseBean{" +
                "diseId='" + diseId + '\'' +
                ", diseName='" + diseName + '\'' +
                '}';
    }
}
