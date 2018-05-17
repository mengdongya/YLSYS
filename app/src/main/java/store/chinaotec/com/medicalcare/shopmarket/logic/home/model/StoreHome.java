package store.chinaotec.com.medicalcare.shopmarket.logic.home.model;


import java.util.ArrayList;

import store.chinaotec.com.medicalcare.shopmarket.logic.shops.entity.Shops;

/**
 * 门店的首页
 */
public class StoreHome {
    /**
     * 店铺信息
     */
    public Shops storeInfoDto;
    /**
     * banner列表
     */
    public ArrayList<ViewPagerImage> banner = new ArrayList<ViewPagerImage>();
    /**
     * 专题列表
     */
    public ArrayList<FunctionalBlock> block = new ArrayList<FunctionalBlock>();
    /**
     * 门店商品列表
     */
    public ArrayList<StoreSku> sku = new ArrayList<StoreSku>();
    /**
     * 营业时间
     */
    public String shopHours;

    public StoreConnection StoreConnection;

    @Override
    public String toString() {
        return "StoreHome [banner=" + banner + ", block=" + block + ", sku=" + sku + "]";
    }

    public class StoreConnection {
        public String name;
        public String phone;
    }

}
