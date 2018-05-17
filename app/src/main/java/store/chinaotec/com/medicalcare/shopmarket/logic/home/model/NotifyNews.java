package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;

/**
 * 此类描述的是:首页--通知消息
 *
 * @author: wjc
 * @version:18
 * @date:2016年5月19日 下午1:22:59
 */
public class NotifyNews {
    private String name;
    private String linkUrl;
    private String showOrder;

    public NotifyNews(String name, String linkUrl, String showOrder) {
        super();
        this.name = name;
        this.linkUrl = linkUrl;
        this.showOrder = showOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
