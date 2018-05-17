package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

public class CategoryAndPre {
    private String code;
    private String name;
    private String imgUrl;
    private String showOrder;

    public CategoryAndPre(String code, String name, String imgUrl, String showOrder) {
        super();
        this.code = code;
        this.name = name;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }


}
