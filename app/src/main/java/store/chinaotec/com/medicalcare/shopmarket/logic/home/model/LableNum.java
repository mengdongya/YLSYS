package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

/**
 * 此类描述的是: app首页的中间banner
 *
 * @author: wjc
 * @version:1.0
 * @date:2016年1月13日 下午5:11:11
 */
public class LableNum {
    private String code;
    private String name;
    private String imgUrl;

    public LableNum(String code, String name, String imgUrl) {
        super();
        this.code = code;
        this.name = name;
        this.imgUrl = imgUrl;
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

}
