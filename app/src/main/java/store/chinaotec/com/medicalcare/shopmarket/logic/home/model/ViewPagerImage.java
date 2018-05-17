package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

/**
 * 轮播图--item
 */
public class ViewPagerImage {
    public String picturePath;
    public String url;
    public String title;
    public String remark;
    public int autoTime;
    public String isAuto;
    public String skipRuleCode;
    public String skipRuleValue;

    @Override
    public String toString() {
        String str = "picturePath:" + picturePath + ",url:" + url;
        return str;
    }
}
