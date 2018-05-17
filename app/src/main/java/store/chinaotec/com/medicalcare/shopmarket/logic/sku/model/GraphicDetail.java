package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

public class GraphicDetail {
    /**
     * 图文详情的图片
     */
    public String url;
    /**
     * 图文详情中的图片的高度
     */
    public int height;

    public GraphicDetail() {
        // TODO Auto-generated constructor stub
    }

    public GraphicDetail(String url, int height) {
        super();
        this.url = url;
        this.height = height;
    }

    @Override
    public String toString() {
        return "GraphicDetail [url=" + url + ", height=" + height + "]";
    }

}
