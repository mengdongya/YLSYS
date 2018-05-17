package store.chinaotec.com.medicalcare.javabean;

/**
 * Created by seven on 2017/12/4 0004.
 */
public class MemberSickDealListThreeBean {
    private String image;
    private String name;
    private int memberSickDealId;

    public MemberSickDealListThreeBean(String image,String name,int memberSickDealId){
        this.image = image;
        this.name = name;
        this.memberSickDealId = memberSickDealId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
