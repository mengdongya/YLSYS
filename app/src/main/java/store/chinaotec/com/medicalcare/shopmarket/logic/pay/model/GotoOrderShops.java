package store.chinaotec.com.medicalcare.shopmarket.logic.pay.model;

import java.util.ArrayList;

public class GotoOrderShops {

    private GotoOrderStoreSummaryDto gotoOrderStoreSummaryDto;
    private ArrayList<PaySku> orderLineItems;
    private String storeName;
    private String storeCode;
    private String skuTotal;
    private String memo;
    private String freight;
    private int quantityTotal;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getSkuTotal() {
        return skuTotal;
    }

    public void setSkuTotal(String skuTotal) {
        this.skuTotal = skuTotal;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public int getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(int quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    public ArrayList<PaySku> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(ArrayList<PaySku> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public GotoOrderStoreSummaryDto getGotoOrderStoreSummaryDto() {
        return gotoOrderStoreSummaryDto;
    }

    public void setGotoOrderStoreSummaryDto(GotoOrderStoreSummaryDto gotoOrderStoreSummaryDto) {
        this.gotoOrderStoreSummaryDto = gotoOrderStoreSummaryDto;
    }

    public class GotoOrderStoreSummaryDto {
        private String storeName;
        private String storeCode;
        private String skuTotal;
        private String memo;
        private String freight;
        private int quantityTotal;
        private String usableCouponCount;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getSkuTotal() {
            return skuTotal;
        }

        public void setSkuTotal(String skuTotal) {
            this.skuTotal = skuTotal;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public int getQuantityTotal() {
            return quantityTotal;
        }

        public void setQuantityTotal(int quantityTotal) {
            this.quantityTotal = quantityTotal;
        }

        public String getUsableCouponCount() {
            return usableCouponCount;
        }

        public void setUsableCouponCount(String usableCouponCount) {
            this.usableCouponCount = usableCouponCount;
        }
    }
}
