package store.chinaotec.com.medicalcare.shopmarket.logic.sku.model;

/**
 * @author 选择 颜色尺寸后的商品
 */
public class SelectedSku {
    public String unCode;
    public String productCode;
    public String color;
    public String availableQty;
    public String skuCode;
    public String size;
    public String usableSku;

    public String getUnCode() {
        return unCode;
    }

    public void setUnCode(String unCode) {
        this.unCode = unCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(String availableQty) {
        this.availableQty = availableQty;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "SelectedSku [unCode=" + unCode + ", productCode=" + productCode
                + ", color=" + color + ", availableQty=" + availableQty
                + ", skuCode=" + skuCode + ", size=" + size + "]";
    }


}
