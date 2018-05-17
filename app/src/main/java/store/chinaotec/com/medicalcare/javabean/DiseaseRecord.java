package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by seven on 2018/3/5 0005.
 */

public class DiseaseRecord {
    private int id;
    private String name;
    private String image;
    private int memberSickDealId;
    private long startTime;
    private String url;

    public DiseaseRecord() {
    }

    public DiseaseRecord(int id, String name, String image, int memberSickDealId, long startTime,String url){
        this.id = id;
        this.name = name;
        this.image = image;
        this.memberSickDealId = memberSickDealId;
        this.startTime = startTime;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMemberSickDealId() {
        return memberSickDealId;
    }

    public void setMemberSickDealId(int memberSickDealId) {
        this.memberSickDealId = memberSickDealId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DiseaseRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", memberSickDealId=" + memberSickDealId +
                ", startTime=" + startTime +
                '}';
    }
}
