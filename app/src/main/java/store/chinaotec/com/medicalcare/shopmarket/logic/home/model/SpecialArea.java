package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

/***
 * 此类描述的是:首页 --特别专区
 *
 * @author: wjc
 * @version:18
 * @date:2016年5月19日 下午1:27:45
 */
public class SpecialArea {
    private String code;
    private String name;
    private String imageUrl;
    private String linkUrl;
    private String showOrder;

    public SpecialArea(String code, String name, String imageUrl, String linkUrl, String showOrder) {
        super();
        this.code = code;
        this.name = name;
        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
        this.showOrder = showOrder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }

}
