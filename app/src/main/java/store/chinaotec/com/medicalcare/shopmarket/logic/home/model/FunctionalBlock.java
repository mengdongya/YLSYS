package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

/**
 * 此类描述的是:首页专题
 *
 * @author: wyk
 * @version:1.0
 * @date:2015年10月15日 下午2:44:46
 */
public class FunctionalBlock {
    /**
     * cede
     */
    private String code;
    /**
     * 专题名称
     */
    private String name;
    /**
     * 图片
     */
    private String icon;
    /**
     * 链接地址
     */
    private String linkUrl;

    public FunctionalBlock() {
        // TODO Auto-generated constructor stub
    }

    public FunctionalBlock(String code, String name, String icon, String linkUrl) {
        super();
        this.code = code;
        this.name = name;
        this.icon = icon;
        this.linkUrl = linkUrl;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @Override
    public String toString() {
        return "FunctionalBlock [code=" + code + ", name=" + name + ", icon="
                + icon + ", linkUrl=" + linkUrl + "]";
    }

}
